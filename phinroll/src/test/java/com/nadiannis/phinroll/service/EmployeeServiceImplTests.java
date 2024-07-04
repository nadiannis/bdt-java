package com.nadiannis.phinroll.service;

import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;
import com.nadiannis.phinroll.model.Employee;
import com.nadiannis.phinroll.repository.EmployeeRepository;
import com.nadiannis.phinroll.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee1;
    private Employee employee2;
    private EmployeeDto employeeDto1;

    @BeforeEach
    public void init() {
        employee1 = Employee.builder().id(UUID.randomUUID().toString()).name("John").gender("m").grade(1).isMarried(true).build();
        employee2 = Employee.builder().id(UUID.randomUUID().toString()).name("Taylor").gender("f").grade(2).isMarried(false).build();
        employeeDto1 = EmployeeDto.builder().name("John").gender("m").grade(1).isMarried(true).build();
    }

    // DataList<List<EmployeeDto>> findAll(int page, int limit)
    @Test
    public void EmployeeService_FindAll_ReturnsEmployeeDtoListWithMetadata() {
        List<Employee> listOfEmployees = Arrays.asList(employee1, employee2);
        Page<Employee> pageOfEmployees = new PageImpl<>(listOfEmployees);
        Pageable pageable = any(Pageable.class);
        when(employeeRepository.findAll(pageable)).thenReturn(pageOfEmployees);

        DataList<List<EmployeeDto>> employees = employeeService.findAll(0, 10);

        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.getData()).isNotNull();
        Assertions.assertThat(employees.getData().size()).isEqualTo(2);
        Assertions.assertThat(employees.getMetadata()).isNotNull();
    }

    // EmployeeDto save(EmployeeDto employeeDto)
    @Test
    public void EmployeeService_Save_ReturnsEmployeeDto() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        EmployeeDto savedEmployee = employeeService.save(employeeDto1);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(employee1.getId());
    }

    // EmployeeDto findById(String id)
    @Test
    public void EmployeeService_FindById_ReturnEmployeeDto() {
        String employeeId = UUID.randomUUID().toString();
        employee1.setId(employeeId);
        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee1));

        EmployeeDto employee = employeeService.findById(employeeId);

        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee.getId()).isEqualTo(employeeId);
    }

    // EmployeeDto update(String id, EmployeeDto employeeDto)
    @Test
    public void EmployeeService_Update_ReturnEmployeeDto() {
        String employeeId = UUID.randomUUID().toString();
        employee1.setId(employeeId);
        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee1));

        employee2.setId(employeeId);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee2);

        EmployeeDto updatedEmployee = employeeService.update(employeeId, employeeDto1);

        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getId()).isEqualTo(employeeId);
        Assertions.assertThat(updatedEmployee.getName()).isEqualTo(employee2.getName());
        Assertions.assertThat(updatedEmployee.getGender()).isEqualTo(employee2.getGender());
        Assertions.assertThat(updatedEmployee.getGrade()).isEqualTo(employee2.getGrade());
        Assertions.assertThat(updatedEmployee.getIsMarried()).isEqualTo(employee2.getIsMarried());
    }

    // EmployeeDto delete(String id)
    @Test
    public void EmployeeService_Delete_ReturnEmployeeDto() {
        String employeeId = UUID.randomUUID().toString();
        employee1.setId(employeeId);
        when(employeeRepository.findById(anyString())).thenReturn(Optional.ofNullable(employee1));
        doNothing().when(employeeRepository).delete(employee1);

        EmployeeDto employee = employeeService.delete(employeeId);

        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee.getId()).isEqualTo(employeeId);
    }

}
