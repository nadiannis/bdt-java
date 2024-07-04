package com.nadiannis.phinroll.repository;

import com.nadiannis.phinroll.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
