package com.nadiannis.phinroll.service;

import com.nadiannis.phinroll.dto.DataList;
import com.nadiannis.phinroll.dto.payroll.PayrollDetailDto;
import com.nadiannis.phinroll.dto.payroll.PayrollDto;
import com.nadiannis.phinroll.dto.payroll.PayrollFormDto;

import java.util.List;

public interface PayrollService {

    DataList<List<PayrollDto>> findAll(int page, int limit);
    PayrollDto save(PayrollFormDto payrollFormDto);
    DataList<List<PayrollDetailDto>> findByEmployeeId(String id, int page, int limit);
    PayrollDetailDto findById(String id);
    PayrollDto delete(String id);

}
