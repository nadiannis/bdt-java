package com.nadiannis.phinroll.service.impl;

import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;
import com.nadiannis.phinroll.dto.Metadata;
import com.nadiannis.phinroll.exception.ResourceNotFoundException;
import com.nadiannis.phinroll.model.Employee;
import com.nadiannis.phinroll.repository.EmployeeRepository;
import com.nadiannis.phinroll.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataList<List<EmployeeDto>> findAll(int page, int limit) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Employee> pageOfEmployees = repository.findAll(pageable);
        List<EmployeeDto> employees = pageOfEmployees.getContent()
                .stream()
                .map(this::mapToDTO)
                .toList();

        Metadata metadata = new Metadata(
                pageOfEmployees.getNumber(),
                pageOfEmployees.getSize(),
                pageOfEmployees.getTotalElements(),
                pageOfEmployees.getTotalPages(),
                pageOfEmployees.isLast());
        return new DataList<List<EmployeeDto>>(employees, metadata);
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = mapToEntity(employeeDto);
        Employee newEmployee = repository.save(employee);
        return mapToDTO(newEmployee);
    }

    @Override
    public EmployeeDto findById(String id) {
        Optional<Employee> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("employee", "id", id);
        }
        return mapToDTO(result.get());
    }

    @Override
    public EmployeeDto update(String id, EmployeeDto employeeDto) {
        Optional<Employee> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("employee", "id", id);
        }

        Employee employee = result.get();
        employee.setName(employeeDto.getName());
        employee.setGender(employeeDto.getGender());
        employee.setGrade(employeeDto.getGrade());
        employee.setIsMarried(employeeDto.getIsMarried());

        Employee updatedEmployee = repository.save(employee);
        return mapToDTO(updatedEmployee);
    }

    @Override
    public EmployeeDto delete(String id) {
        Optional<Employee> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("employee", "id", id);
        }

        Employee employee = result.get();
        repository.delete(employee);
        return mapToDTO(employee);
    }

    private EmployeeDto mapToDTO(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setGender(employee.getGender());
        employeeDto.setGrade(employee.getGrade());
        employeeDto.setIsMarried(employee.getIsMarried());
        employeeDto.setCreatedAt(employee.getCreatedAt());
        employeeDto.setUpdatedAt(employee.getUpdatedAt());
        return employeeDto;
    }

    private Employee mapToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setGender(employeeDto.getGender());
        employee.setGrade(employeeDto.getGrade());
        employee.setIsMarried(employeeDto.getIsMarried());
        return employee;
    }

}
