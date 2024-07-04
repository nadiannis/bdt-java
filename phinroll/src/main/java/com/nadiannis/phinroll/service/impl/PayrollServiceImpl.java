package com.nadiannis.phinroll.service.impl;

import com.nadiannis.phinroll.dto.*;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;
import com.nadiannis.phinroll.dto.employee.EmployeeMinimalDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDetailDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDto;
import com.nadiannis.phinroll.dto.payroll.PayrollFormDto;
import com.nadiannis.phinroll.exception.ResourceAlreadyExistsException;
import com.nadiannis.phinroll.exception.ResourceNotFoundException;
import com.nadiannis.phinroll.model.Employee;
import com.nadiannis.phinroll.model.Payroll;
import com.nadiannis.phinroll.model.SalaryMatrixEntry;
import com.nadiannis.phinroll.repository.EmployeeRepository;
import com.nadiannis.phinroll.repository.PayrollRepository;
import com.nadiannis.phinroll.repository.SalaryMatrixEntryRepository;
import com.nadiannis.phinroll.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.nadiannis.phinroll.utils.Salaries.*;

@Service
public class PayrollServiceImpl implements PayrollService {

    private PayrollRepository payrollRepository;
    private EmployeeRepository employeeRepository;
    private SalaryMatrixEntryRepository salaryMatrixEntryRepository;

    @Autowired
    public PayrollServiceImpl(PayrollRepository payrollRepository,
                              EmployeeRepository employeeRepository,
                              SalaryMatrixEntryRepository salaryMatrixEntryRepository
    ) {
        this.payrollRepository = payrollRepository;
        this.employeeRepository = employeeRepository;
        this.salaryMatrixEntryRepository = salaryMatrixEntryRepository;
    }

    @Override
    public DataList<List<PayrollDto>> findAll(int page, int limit) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Payroll> pageOfPayrolls = payrollRepository.findAll(pageable);
        List<PayrollDto> payrolls = pageOfPayrolls.getContent()
                .stream()
                .map(this::mapToDto)
                .toList();

        Metadata metadata = new Metadata(
                pageOfPayrolls.getNumber(),
                pageOfPayrolls.getSize(),
                pageOfPayrolls.getTotalElements(),
                pageOfPayrolls.getTotalPages(),
                pageOfPayrolls.isLast());
        return new DataList<List<PayrollDto>>(payrolls, metadata);
    }

    @Override
    public PayrollDto save(PayrollFormDto payrollFormDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(payrollFormDto.getEmployeeId());
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("employee", "id", payrollFormDto.getEmployeeId());
        }

        boolean doesPayrollExists = payrollRepository.existsByEmployeeAndOverlappingPeriod(payrollFormDto.getEmployeeId(), payrollFormDto.getStartDate(), payrollFormDto.getEndDate());
        if (doesPayrollExists) {
            throw new ResourceAlreadyExistsException("payroll for the given period date", "employee_id", payrollFormDto.getEmployeeId());
        }

        Employee employee = employeeOptional.get();
        HashMap<String, BigDecimal> salaries = getSalaries(employee, payrollFormDto.getAttendanceDays(), payrollFormDto.getAbsenceDays());

        Payroll payroll = new Payroll();
        payroll.setAttendanceDays(payrollFormDto.getAttendanceDays());
        payroll.setAbsenceDays(payrollFormDto.getAbsenceDays());
        payroll.setBasicSalary(salaries.get("basicSalary"));
        payroll.setTotalPayCut(salaries.get("totalPayCut"));
        payroll.setTotalAllowance(salaries.get("totalAllowance"));
        payroll.setHeadOfFamily(salaries.get("headOfFamily"));
        payroll.setTotalAdditionalSalary(salaries.get("totalAdditionalSalary"));
        payroll.setStartDate(payrollFormDto.getStartDate());
        payroll.setEndDate(payrollFormDto.getEndDate());
        payroll.setEmployee(employee);

        Payroll newPayroll = payrollRepository.save(payroll);
        return mapToDto(newPayroll);
    }

    @Override
    public DataList<List<PayrollDetailDto>> findByEmployeeId(String id, int page, int limit) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("employee", "id", id);
        }

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Payroll> pageOfPayrolls = payrollRepository.findByEmployeeId(id, pageable);
        List<PayrollDetailDto> payrolls = pageOfPayrolls.getContent()
                .stream()
                .map(this::mapToDetailDto)
                .toList();

        Metadata metadata = new Metadata(
                pageOfPayrolls.getNumber(),
                pageOfPayrolls.getSize(),
                pageOfPayrolls.getTotalElements(),
                pageOfPayrolls.getTotalPages(),
                pageOfPayrolls.isLast());
        return new DataList<List<PayrollDetailDto>>(payrolls, metadata);
    }

    @Override
    public PayrollDetailDto findById(String id) {
        Optional<Payroll> result = payrollRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("payroll", "id", id);
        }
        return mapToDetailDto(result.get());
    }

    @Override
    public PayrollDto delete(String id) {
        Optional<Payroll> result = payrollRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("payroll", "id", id);
        }

        Payroll payroll = result.get();
        payrollRepository.delete(payroll);
        return mapToDto(payroll);
    }

    private HashMap<String, BigDecimal> getSalaries(Employee employee, Integer attendanceDays, Integer absenceDays) {
        Optional<SalaryMatrixEntry> salaryMatrixEntryOptional = salaryMatrixEntryRepository.findByGrade(employee.getGrade());
        if (salaryMatrixEntryOptional.isEmpty()) {
            throw new ResourceNotFoundException("salary matrix entry", "grade", employee.getGrade().toString());
        }
        return calculateSalaries(attendanceDays, absenceDays, salaryMatrixEntryOptional.get());
    }

    private static HashMap<String, BigDecimal> calculateSalaries(Integer attendanceDays, Integer absenceDays, SalaryMatrixEntry salaryMatrixEntry) {
        BigDecimal basicSalary = salaryMatrixEntry.getBasicSalary();
        BigDecimal totalPayCut = calculateTotalPayCut(absenceDays, salaryMatrixEntry.getPayCut());
        BigDecimal totalAllowance = calculateTotalAllowance(attendanceDays, salaryMatrixEntry.getAllowance());
        BigDecimal headOfFamily = salaryMatrixEntry.getHeadOfFamily();
        BigDecimal totalAdditionalSalary = totalAllowance.add(headOfFamily);
        BigDecimal totalSalary = calculateTotalSalary(salaryMatrixEntry.getBasicSalary(), totalAdditionalSalary, totalPayCut);

        HashMap<String, BigDecimal> salaries = new HashMap<>();
        salaries.put("basicSalary", basicSalary);
        salaries.put("totalPayCut", totalPayCut);
        salaries.put("totalAllowance", totalAllowance);
        salaries.put("headOfFamily", headOfFamily);
        salaries.put("totalAdditionalSalary", totalAdditionalSalary);
        salaries.put("totalSalary", totalSalary);
        return salaries;
    }

    private PayrollDto mapToDto(Payroll payroll) {
        PayrollDto payrollDto = new PayrollDto();
        payrollDto.setId(payroll.getId());
        payrollDto.setAttendanceDays(payroll.getAttendanceDays());
        payrollDto.setAbsenceDays(payroll.getAbsenceDays());
        payrollDto.setBasicSalary(payroll.getBasicSalary());
        payrollDto.setTotalPayCut(payroll.getTotalPayCut());
        payrollDto.setTotalAllowance(payroll.getTotalAllowance());
        payrollDto.setHeadOfFamily(payroll.getHeadOfFamily());
        payrollDto.setTotalAdditionalSalary(payroll.getTotalAdditionalSalary());
        payrollDto.setStartDate(payroll.getStartDate());
        payrollDto.setEndDate(payroll.getEndDate());
        payrollDto.setCreatedAt(payroll.getCreatedAt());
        payrollDto.setUpdatedAt(payroll.getUpdatedAt());

        EmployeeMinimalDto employee = new EmployeeMinimalDto();
        employee.setId(payroll.getEmployee().getId());
        employee.setName(payroll.getEmployee().getName());
        payrollDto.setEmployee(employee);

        return payrollDto;
    }

    private PayrollDetailDto mapToDetailDto(Payroll payroll) {
        PayrollDetailDto payrollDetailDto = new PayrollDetailDto();
        payrollDetailDto.setId(payroll.getId());
        payrollDetailDto.setAttendanceDays(payroll.getAttendanceDays());
        payrollDetailDto.setAbsenceDays(payroll.getAbsenceDays());
        payrollDetailDto.setBasicSalary(payroll.getBasicSalary());
        payrollDetailDto.setTotalPayCut(payroll.getTotalPayCut());
        payrollDetailDto.setTotalAllowance(payroll.getTotalAllowance());
        payrollDetailDto.setHeadOfFamily(payroll.getHeadOfFamily());
        payrollDetailDto.setTotalAdditionalSalary(payroll.getTotalAdditionalSalary());
        payrollDetailDto.setTotalSalary(
                calculateTotalSalary(payroll.getBasicSalary(), payroll.getTotalAdditionalSalary(), payroll.getTotalPayCut())
        );
        payrollDetailDto.setStartDate(payroll.getStartDate());
        payrollDetailDto.setEndDate(payroll.getEndDate());
        payrollDetailDto.setCreatedAt(payroll.getCreatedAt());
        payrollDetailDto.setUpdatedAt(payroll.getUpdatedAt());

        EmployeeDto employee = new EmployeeDto();
        employee.setId(payroll.getEmployee().getId());
        employee.setName(payroll.getEmployee().getName());
        employee.setGender(payroll.getEmployee().getGender());
        employee.setGrade(payroll.getEmployee().getGrade());
        employee.setIsMarried(payroll.getEmployee().getIsMarried());
        employee.setCreatedAt(payroll.getEmployee().getCreatedAt());
        employee.setUpdatedAt(payroll.getEmployee().getUpdatedAt());
        payrollDetailDto.setEmployee(employee);

        return payrollDetailDto;
    }

}
