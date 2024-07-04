package com.nadiannis.phinroll.service;

import com.nadiannis.phinroll.dto.salarymatrixentry.SalaryMatrixEntryDto;
import com.nadiannis.phinroll.model.SalaryMatrixEntry;
import com.nadiannis.phinroll.repository.SalaryMatrixEntryRepository;
import com.nadiannis.phinroll.service.impl.SalaryMatrixEntryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalaryMatrixEntryServiceTests {

    @Mock
    private SalaryMatrixEntryRepository salaryMatrixEntryRepository;

    @InjectMocks
    private SalaryMatrixEntryServiceImpl salaryMatrixEntryService;

    private SalaryMatrixEntry salaryMatrixEntry1;
    private SalaryMatrixEntry salaryMatrixEntry2;
    private SalaryMatrixEntryDto salaryMatrixEntryDto1;

    @BeforeEach
    public void init() {
        salaryMatrixEntry1 = SalaryMatrixEntry.builder()
                .id(UUID.randomUUID().toString())
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();
        salaryMatrixEntry2 = SalaryMatrixEntry.builder()
                .id(UUID.randomUUID().toString())
                .grade(2)
                .basicSalary(BigDecimal.valueOf(4000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();
        salaryMatrixEntryDto1 = SalaryMatrixEntryDto.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();
    }

    // List<SalaryMatrixEntryDto> findAll()
    @Test
    public void SalaryMatrixEntryService_FindAll_ReturnsSalaryMatrixEntryDtoList() {
        List<SalaryMatrixEntry> listOfSalaryMatrixEntries = Arrays.asList(salaryMatrixEntry1, salaryMatrixEntry2);
        when(salaryMatrixEntryRepository.findAll()).thenReturn(listOfSalaryMatrixEntries);

        List<SalaryMatrixEntryDto> salaryMatrixEntries = salaryMatrixEntryService.findAll();

        Assertions.assertThat(salaryMatrixEntries).isNotNull();
        Assertions.assertThat(salaryMatrixEntries.size()).isEqualTo(2);
    }

    // SalaryMatrixEntryDto save(SalaryMatrixEntryDto salaryMatrixEntryDto)
    @Test
    public void SalaryMatrixEntryService_Save_ReturnsSalaryMatrixEntryDto() {
        when(salaryMatrixEntryRepository.save(any(SalaryMatrixEntry.class))).thenReturn(salaryMatrixEntry1);

        SalaryMatrixEntryDto savedSalaryMatrixEntry = salaryMatrixEntryService.save(salaryMatrixEntryDto1);

        Assertions.assertThat(savedSalaryMatrixEntry).isNotNull();
        Assertions.assertThat(savedSalaryMatrixEntry.getId()).isNotNull();
        Assertions.assertThat(savedSalaryMatrixEntry.getId()).isEqualTo(salaryMatrixEntry1.getId());
    }

    // SalaryMatrixEntryDto findByGrade(Integer grade)
    @Test
    public void SalaryMatrixEntryService_FindByGrade_ReturnsSalaryMatrixEntryDto() {
        Integer grade = 1;
        salaryMatrixEntry1.setGrade(grade);
        when(salaryMatrixEntryRepository.findByGrade(anyInt())).thenReturn(Optional.ofNullable(salaryMatrixEntry1));

        SalaryMatrixEntryDto salaryMatrixEntry = salaryMatrixEntryService.findByGrade(grade);

        Assertions.assertThat(salaryMatrixEntry).isNotNull();
        Assertions.assertThat(salaryMatrixEntry.getGrade()).isEqualTo(grade);
    }

    // SalaryMatrixEntryDto update(String id, SalaryMatrixEntryDto salaryMatrixEntryDto)
    @Test
    public void SalaryMatrixEntryService_Update_ReturnsSalaryMatrixEntryDto() {
        String salaryMatrixEntryId = UUID.randomUUID().toString();
        salaryMatrixEntry1.setId(salaryMatrixEntryId);
        when(salaryMatrixEntryRepository.findById(anyString())).thenReturn(Optional.ofNullable(salaryMatrixEntry1));

        salaryMatrixEntry2.setId(salaryMatrixEntryId);
        when(salaryMatrixEntryRepository.save(any(SalaryMatrixEntry.class))).thenReturn(salaryMatrixEntry2);

        SalaryMatrixEntryDto updatedSalaryMatrixEntry = salaryMatrixEntryService.update(salaryMatrixEntryId, salaryMatrixEntryDto1);

        Assertions.assertThat(updatedSalaryMatrixEntry).isNotNull();
        Assertions.assertThat(updatedSalaryMatrixEntry.getId()).isEqualTo(salaryMatrixEntryId);
        Assertions.assertThat(updatedSalaryMatrixEntry.getGrade()).isEqualTo(salaryMatrixEntry2.getGrade());
        Assertions.assertThat(updatedSalaryMatrixEntry.getBasicSalary()).isEqualTo(salaryMatrixEntry2.getBasicSalary());
        Assertions.assertThat(updatedSalaryMatrixEntry.getPayCut()).isEqualTo(salaryMatrixEntry2.getPayCut());
        Assertions.assertThat(updatedSalaryMatrixEntry.getAllowance()).isEqualTo(salaryMatrixEntry2.getAllowance());
        Assertions.assertThat(updatedSalaryMatrixEntry.getHeadOfFamily()).isEqualTo(salaryMatrixEntry2.getHeadOfFamily());
    }

    // SalaryMatrixEntryDto delete(String id)
    @Test
    public void SalaryMatrixEntryService_Delete_ReturnsSalaryMatrixEntryDto() {
        String salaryMatrixEntryId = UUID.randomUUID().toString();
        salaryMatrixEntry1.setId(salaryMatrixEntryId);
        when(salaryMatrixEntryRepository.findById(anyString())).thenReturn(Optional.ofNullable(salaryMatrixEntry1));
        doNothing().when(salaryMatrixEntryRepository).delete(salaryMatrixEntry1);

        SalaryMatrixEntryDto salaryMatrixEntry = salaryMatrixEntryService.delete(salaryMatrixEntryId);

        Assertions.assertThat(salaryMatrixEntry).isNotNull();
        Assertions.assertThat(salaryMatrixEntry.getId()).isEqualTo(salaryMatrixEntryId);
    }

}
