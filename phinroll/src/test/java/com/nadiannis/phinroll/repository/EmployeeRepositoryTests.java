package com.nadiannis.phinroll.repository;

import com.nadiannis.phinroll.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Page<Employee> findAll(pageable)
    @Test
    public void EmployeeRepository_FindAll_ReturnMoreThanOneEmployees() {
        Employee employee1 = Employee.builder()
                .name("John")
                .gender("m")
                .grade(5)
                .isMarried(true)
                .build();
        Employee employee2 = Employee.builder()
                .name("Taylor")
                .gender("f")
                .grade(7)
                .isMarried(false)
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

    // Employee save(employee)
    @Test
    public void EmployeeRepository_Save_ReturnSavedEmployee() {
        Employee employee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(5)
                .isMarried(true)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
    }

    // Optional<Employee> findById(id)
    @Test
    public void EmployeeRepository_FindById_ReturnEmployee() {
        Employee newEmployee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(5)
                .isMarried(true)
                .build();

        employeeRepository.save(newEmployee);

        Employee employee = employeeRepository.findById(newEmployee.getId()).get();

        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee.getId()).isEqualTo(newEmployee.getId());
    }

    @Test
    public void EmployeeRepository_Update_ReturnUpdatedEmployee() {
        Employee newEmployee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(5)
                .isMarried(true)
                .build();

        employeeRepository.save(newEmployee);

        Employee employee = employeeRepository.findById(newEmployee.getId()).get();
        employee.setName("Swift");
        employee.setGender("f");
        employee.setGrade(10);
        employee.setIsMarried(false);

        Employee updatedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getName()).isEqualTo(employee.getName());
        Assertions.assertThat(updatedEmployee.getGender()).isEqualTo(employee.getGender());
        Assertions.assertThat(updatedEmployee.getGrade()).isEqualTo(employee.getGrade());
        Assertions.assertThat(updatedEmployee.getIsMarried()).isEqualTo(employee.getIsMarried());
    }

    // void delete(employee)
    @Test
    public void EmployeeRepository_Delete_ReturnEmployeeIsEmpty() {
        Employee newEmployee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(5)
                .isMarried(true)
                .build();

        employeeRepository.save(newEmployee);

        Employee employee = employeeRepository.findById(newEmployee.getId()).get();
        employeeRepository.delete(employee);

        Optional<Employee> deletedEmployee = employeeRepository.findById(employee.getId());
        Assertions.assertThat(deletedEmployee).isEmpty();
    }

}
