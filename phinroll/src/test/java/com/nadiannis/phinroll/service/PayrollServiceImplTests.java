package com.nadiannis.phinroll.service;

import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDetailDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDto;
import com.nadiannis.phinroll.dto.payroll.PayrollFormDto;
import com.nadiannis.phinroll.model.Employee;
import com.nadiannis.phinroll.model.Payroll;
import com.nadiannis.phinroll.model.SalaryMatrixEntry;
import com.nadiannis.phinroll.repository.EmployeeRepository;
import com.nadiannis.phinroll.repository.PayrollRepository;
import com.nadiannis.phinroll.repository.SalaryMatrixEntryRepository;
import com.nadiannis.phinroll.service.impl.PayrollServiceImpl;
import com.nadiannis.phinroll.utils.Salaries;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceImplTests {

    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SalaryMatrixEntryRepository salaryMatrixEntryRepository;

    @InjectMocks
    private PayrollServiceImpl payrollService;

    private Payroll payroll1;
    private Payroll payroll2;
    private PayrollFormDto payrollFormDto;
    private Employee employee;
    private EmployeeDto employeeDto;
    private SalaryMatrixEntry salaryMatrixEntry;

    @BeforeEach
    public void init() {
        employee = Employee.builder().id(UUID.randomUUID().toString()).name("John").gender("m").grade(1).isMarried(true).build();
        employeeDto = EmployeeDto.builder().id(employee.getId()).name(employee.getName()).gender(employee.getGender()).grade(employee.getGrade()).isMarried(employee.getIsMarried()).build();
        payroll1 = Payroll.builder()
                .id(UUID.randomUUID().toString())
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employee)
                .build();
        payroll2 = Payroll.builder()
                .id(UUID.randomUUID().toString())
                .attendanceDays(10)
                .absenceDays(10)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(500000))
                .totalAllowance(BigDecimal.valueOf(500000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(600000))
                .startDate(LocalDate.parse("2024-07-01"))
                .endDate(LocalDate.parse("2024-07-31"))
                .employee(employee)
                .build();
        payrollFormDto = PayrollFormDto.builder()
                .employeeId(employee.getId())
                .attendanceDays(15)
                .absenceDays(5)
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .build();
        salaryMatrixEntry = SalaryMatrixEntry.builder()
                .id(UUID.randomUUID().toString())
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();
    }

    // DataList<List<PayrollDto>> findAll(int page, int limit)
    @Test
    public void PayrollService_FindAll_ReturnsPayrollDtoListWithMetadata() {
        List<Payroll> listOfPayrolls = Arrays.asList(payroll1, payroll2);
        Page<Payroll> pageOfPayrolls = new PageImpl<>(listOfPayrolls);
        Pageable pageable = any(Pageable.class);
        when(payrollRepository.findAll(pageable)).thenReturn(pageOfPayrolls);

        DataList<List<PayrollDto>> payrolls = payrollService.findAll(0, 10);

        Assertions.assertThat(payrolls).isNotNull();
        Assertions.assertThat(payrolls.getData()).isNotNull();
        Assertions.assertThat(payrolls.getData().size()).isEqualTo(2);
        Assertions.assertThat(payrolls.getMetadata()).isNotNull();
    }

    // PayrollDto save(PayrollFormDto payrollFormDto)
    @Test
    public void PayrollService_Save_ReturnsPayrollDto() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee));
        when(payrollRepository.existsByEmployeeAndOverlappingPeriod(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(false);
        when(salaryMatrixEntryRepository.findByGrade(anyInt())).thenReturn(Optional.ofNullable(salaryMatrixEntry));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(payroll1);

        PayrollDto savedPayroll = payrollService.save(payrollFormDto);

        Assertions.assertThat(savedPayroll).isNotNull();
        Assertions.assertThat(savedPayroll.getId()).isNotNull();
        Assertions.assertThat(savedPayroll.getId()).isEqualTo(payroll1.getId());
    }

    // DataList<List<PayrollDetailDto>> findByEmployeeId(String id, int page, int limit)
    @Test
    public void PayrollService_FindByEmployeeId_ReturnsPayrollDetailDtoListWithMetadata() {
        String employeeId = employee.getId();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        List<Payroll> listOfPayrolls = Arrays.asList(payroll1, payroll2);
        Page<Payroll> pageOfPayrolls = new PageImpl<>(listOfPayrolls, pageable, listOfPayrolls.size());

        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee));
        when(payrollRepository.findByEmployeeId(anyString(), eq(pageable))).thenReturn(pageOfPayrolls);

        DataList<List<PayrollDetailDto>> payrolls = payrollService.findByEmployeeId(employeeId, 0, 10);

        Assertions.assertThat(payrolls).isNotNull();
        Assertions.assertThat(payrolls.getData()).isNotNull();
        Assertions.assertThat(payrolls.getData().size()).isEqualTo(2);
        Assertions.assertThat(payrolls.getMetadata()).isNotNull();
    }

    // PayrollDetailDto findById(String id)
    @Test
    public void PayrollService_FindById_ReturnPayrollDetailDto() {
        String payrollId = UUID.randomUUID().toString();
        payroll1.setId(payrollId);
        when(payrollRepository.findById(anyString())).thenReturn(Optional.ofNullable(payroll1));

        PayrollDetailDto payroll = payrollService.findById(payrollId);

        Assertions.assertThat(payroll).isNotNull();
        Assertions.assertThat(payroll.getId()).isEqualTo(payrollId);
        Assertions.assertThat(payroll.getAttendanceDays()).isEqualTo(payroll1.getAttendanceDays());
        Assertions.assertThat(payroll.getAbsenceDays()).isEqualTo(payroll1.getAbsenceDays());
        Assertions.assertThat(payroll.getBasicSalary()).isEqualTo(payroll1.getBasicSalary());
        Assertions.assertThat(payroll.getTotalPayCut()).isEqualTo(payroll1.getTotalPayCut());
        Assertions.assertThat(payroll.getTotalAllowance()).isEqualTo(payroll1.getTotalAllowance());
        Assertions.assertThat(payroll.getHeadOfFamily()).isEqualTo(payroll1.getHeadOfFamily());
        Assertions.assertThat(payroll.getTotalAdditionalSalary()).isEqualTo(payroll1.getTotalAdditionalSalary());
        Assertions.assertThat(payroll.getTotalSalary()).isEqualTo(
                Salaries.calculateTotalSalary(payroll1.getBasicSalary(), payroll1.getTotalAdditionalSalary(), payroll1.getTotalPayCut())
        );
        Assertions.assertThat(payroll.getStartDate()).isEqualTo(payroll1.getStartDate());
        Assertions.assertThat(payroll.getEndDate()).isEqualTo(payroll1.getEndDate());
        Assertions.assertThat(payroll.getCreatedAt()).isEqualTo(payroll1.getCreatedAt());
        Assertions.assertThat(payroll.getUpdatedAt()).isEqualTo(payroll1.getUpdatedAt());
        Assertions.assertThat(payroll.getEmployee()).isEqualTo(employeeDto);
    }

    // PayrollDto delete(String id)
    @Test
    public void PayrollService_Delete_ReturnPayrollDto() {
        String payrollId = UUID.randomUUID().toString();
        payroll1.setId(payrollId);
        when(payrollRepository.findById(anyString())).thenReturn(Optional.ofNullable(payroll1));
        doNothing().when(payrollRepository).delete(payroll1);

        PayrollDto payroll = payrollService.delete(payrollId);

        Assertions.assertThat(payroll).isNotNull();
        Assertions.assertThat(payroll.getId()).isEqualTo(payrollId);
    }

}
