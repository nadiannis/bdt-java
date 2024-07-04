package com.nadiannis.phinroll.service;

import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    DataList<List<EmployeeDto>> findAll(int page, int limit);
    EmployeeDto save(EmployeeDto employeeDto);
    EmployeeDto findById(String id);
    EmployeeDto update(String id, EmployeeDto employeeDto);
    EmployeeDto delete(String id);

}
