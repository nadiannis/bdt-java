package com.nadiannis.phinroll.service;

import com.nadiannis.phinroll.dto.salarymatrixentry.SalaryMatrixEntryDto;

import java.util.List;

public interface SalaryMatrixEntryService {

    List<SalaryMatrixEntryDto> findAll();
    SalaryMatrixEntryDto save(SalaryMatrixEntryDto salaryMatrixEntryDto);
    SalaryMatrixEntryDto findByGrade(Integer grade);
    SalaryMatrixEntryDto update(String id, SalaryMatrixEntryDto salaryMatrixEntryDto);
    SalaryMatrixEntryDto delete(String id);

}
