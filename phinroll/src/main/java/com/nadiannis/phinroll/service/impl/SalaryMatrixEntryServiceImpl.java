package com.nadiannis.phinroll.service.impl;

import com.nadiannis.phinroll.dto.salarymatrixentry.SalaryMatrixEntryDto;
import com.nadiannis.phinroll.exception.ResourceAlreadyExistsException;
import com.nadiannis.phinroll.exception.ResourceNotFoundException;
import com.nadiannis.phinroll.model.SalaryMatrixEntry;
import com.nadiannis.phinroll.repository.SalaryMatrixEntryRepository;
import com.nadiannis.phinroll.service.SalaryMatrixEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SalaryMatrixEntryServiceImpl implements SalaryMatrixEntryService {

    private SalaryMatrixEntryRepository repository;

    @Autowired
    public SalaryMatrixEntryServiceImpl(SalaryMatrixEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SalaryMatrixEntryDto> findAll() {
        return repository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public SalaryMatrixEntryDto save(SalaryMatrixEntryDto salaryMatrixEntryDto) {
        Optional<SalaryMatrixEntry> result = repository.findByGrade(salaryMatrixEntryDto.getGrade());
        if (result.isPresent()) {
            throw new ResourceAlreadyExistsException("salary matrix entry", "grade", salaryMatrixEntryDto.getGrade().toString());
        }

        SalaryMatrixEntry salaryMatrixEntry = mapToEntity(salaryMatrixEntryDto);
        SalaryMatrixEntry newSalaryMatrixEntry = repository.save(salaryMatrixEntry);
        return mapToDto(newSalaryMatrixEntry);
    }

    @Override
    public SalaryMatrixEntryDto findByGrade(Integer grade) {
        Optional<SalaryMatrixEntry> result = repository.findByGrade(grade);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("salary matrix entry", "grade", Integer.toString(grade));
        }
        return mapToDto(result.get());
    }

    @Override
    public SalaryMatrixEntryDto update(String id, SalaryMatrixEntryDto salaryMatrixEntryDto) {
        Optional<SalaryMatrixEntry> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("salary matrix entry", "id", id);
        }

        if (!Objects.equals(result.get().getGrade(), salaryMatrixEntryDto.getGrade())) {
            if (repository.findByGrade(salaryMatrixEntryDto.getGrade()).isPresent()) {
                throw new ResourceAlreadyExistsException("salary matrix entry", "grade", salaryMatrixEntryDto.getGrade().toString());
            }
        }

        SalaryMatrixEntry salaryMatrixEntry = result.get();
        salaryMatrixEntry.setGrade(salaryMatrixEntryDto.getGrade());
        salaryMatrixEntry.setBasicSalary(salaryMatrixEntryDto.getBasicSalary());
        salaryMatrixEntry.setPayCut(salaryMatrixEntryDto.getPayCut());
        salaryMatrixEntry.setAllowance(salaryMatrixEntryDto.getAllowance());
        salaryMatrixEntry.setHeadOfFamily(salaryMatrixEntryDto.getHeadOfFamily());

        SalaryMatrixEntry updatedSalaryMatrixEntry = repository.save(salaryMatrixEntry);
        return mapToDto(updatedSalaryMatrixEntry);
    }

    @Override
    public SalaryMatrixEntryDto delete(String id) {
        Optional<SalaryMatrixEntry> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("salary matrix entry", "id", id);
        }

        SalaryMatrixEntry salaryMatrixEntry = result.get();
        repository.delete(salaryMatrixEntry);
        return mapToDto(salaryMatrixEntry);
    }

    private SalaryMatrixEntryDto mapToDto(SalaryMatrixEntry salaryMatrixEntry) {
        SalaryMatrixEntryDto salaryMatrixEntryDto = new SalaryMatrixEntryDto();
        salaryMatrixEntryDto.setId(salaryMatrixEntry.getId());
        salaryMatrixEntryDto.setGrade(salaryMatrixEntry.getGrade());
        salaryMatrixEntryDto.setBasicSalary(salaryMatrixEntry.getBasicSalary());
        salaryMatrixEntryDto.setPayCut(salaryMatrixEntry.getPayCut());
        salaryMatrixEntryDto.setAllowance(salaryMatrixEntry.getAllowance());
        salaryMatrixEntryDto.setHeadOfFamily(salaryMatrixEntry.getHeadOfFamily());
        salaryMatrixEntryDto.setCreatedAt(salaryMatrixEntry.getCreatedAt());
        salaryMatrixEntryDto.setUpdatedAt(salaryMatrixEntry.getUpdatedAt());
        return salaryMatrixEntryDto;
    }

    private SalaryMatrixEntry mapToEntity(SalaryMatrixEntryDto salaryMatrixEntryDto) {
        SalaryMatrixEntry salaryMatrixEntry = new SalaryMatrixEntry();
        salaryMatrixEntry.setGrade(salaryMatrixEntryDto.getGrade());
        salaryMatrixEntry.setBasicSalary(salaryMatrixEntryDto.getBasicSalary());
        salaryMatrixEntry.setPayCut(salaryMatrixEntryDto.getPayCut());
        salaryMatrixEntry.setAllowance(salaryMatrixEntryDto.getAllowance());
        salaryMatrixEntry.setHeadOfFamily(salaryMatrixEntryDto.getHeadOfFamily());
        return salaryMatrixEntry;
    }

}
