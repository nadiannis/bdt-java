package com.nadiannis.phinroll.repository;

import com.nadiannis.phinroll.model.SalaryMatrixEntry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SalaryMatrixEntryRepositoryTests {

    @Autowired
    private SalaryMatrixEntryRepository salaryMatrixEntryRepository;

    // List<SalaryMatrixEntry> findAll()
    @Test
    public void SalaryMatrixEntryRepository_FindAll_ReturnMoreThanOneSalaryMatrixEntries() {
        SalaryMatrixEntry salaryMatrixEntry1 = SalaryMatrixEntry.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();
        SalaryMatrixEntry salaryMatrixEntry2 = SalaryMatrixEntry.builder()
                .grade(2)
                .basicSalary(BigDecimal.valueOf(4000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();

        salaryMatrixEntryRepository.save(salaryMatrixEntry1);
        salaryMatrixEntryRepository.save(salaryMatrixEntry2);

        List<SalaryMatrixEntry> salaryMatrixEntries = salaryMatrixEntryRepository.findAll();

        Assertions.assertThat(salaryMatrixEntries).isNotNull();
        Assertions.assertThat(salaryMatrixEntries.size()).isEqualTo(2);
    }

    // SalaryMatrixEntry save(salaryMatrixEntry)
    @Test
    public void SalaryMatrixEntryRepository_Save_ReturnSavedSalaryMatrixEntry() {
        SalaryMatrixEntry salaryMatrixEntry = SalaryMatrixEntry.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();

        SalaryMatrixEntry savedSalaryMatrixEntry = salaryMatrixEntryRepository.save(salaryMatrixEntry);

        Assertions.assertThat(savedSalaryMatrixEntry).isNotNull();
        Assertions.assertThat(savedSalaryMatrixEntry.getId()).isNotNull();
        Assertions.assertThat(savedSalaryMatrixEntry.getId()).isEqualTo(salaryMatrixEntry.getId());
    }

    // Optional<SalaryMatrixEntry> findById(id)
    @Test
    public void SalaryMatrixEntryRepository_FindById_ReturnSalaryMatrixEntry() {
        SalaryMatrixEntry newSalaryMatrixEntry = SalaryMatrixEntry.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();

        salaryMatrixEntryRepository.save(newSalaryMatrixEntry);

        SalaryMatrixEntry salaryMatrixEntry = salaryMatrixEntryRepository.findById(newSalaryMatrixEntry.getId()).get();

        Assertions.assertThat(salaryMatrixEntry).isNotNull();
        Assertions.assertThat(salaryMatrixEntry.getId()).isEqualTo(newSalaryMatrixEntry.getId());
    }

    // Optional<SalaryMatrixEntry> findByGrade(grade)
    @Test
    public void SalaryMatrixEntryRepository_FindByGrade_ReturnSalaryMatrixEntry() {
        SalaryMatrixEntry newSalaryMatrixEntry = SalaryMatrixEntry.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();

        salaryMatrixEntryRepository.save(newSalaryMatrixEntry);

        SalaryMatrixEntry salaryMatrixEntry = salaryMatrixEntryRepository.findByGrade(newSalaryMatrixEntry.getGrade()).get();

        Assertions.assertThat(salaryMatrixEntry).isNotNull();
        Assertions.assertThat(salaryMatrixEntry.getGrade()).isEqualTo(newSalaryMatrixEntry.getGrade());
    }

    @Test
    public void SalaryMatrixEntryRepository_Update_ReturnUpdatedSalaryMatrixEntry() {
        SalaryMatrixEntry newSalaryMatrixEntry = SalaryMatrixEntry.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();

        salaryMatrixEntryRepository.save(newSalaryMatrixEntry);

        SalaryMatrixEntry salaryMatrixEntry = salaryMatrixEntryRepository.findById(newSalaryMatrixEntry.getId()).get();
        salaryMatrixEntry.setGrade(3);
        salaryMatrixEntry.setBasicSalary(BigDecimal.valueOf(6000000));
        salaryMatrixEntry.setPayCut(BigDecimal.valueOf(100000));
        salaryMatrixEntry.setAllowance(BigDecimal.valueOf(100000));
        salaryMatrixEntry.setHeadOfFamily(BigDecimal.valueOf(150000));

        SalaryMatrixEntry updatedSalaryMatrixEntry = salaryMatrixEntryRepository.save(salaryMatrixEntry);

        Assertions.assertThat(updatedSalaryMatrixEntry).isNotNull();
        Assertions.assertThat(updatedSalaryMatrixEntry.getGrade()).isEqualTo(salaryMatrixEntry.getGrade());
        Assertions.assertThat(updatedSalaryMatrixEntry.getBasicSalary()).isEqualTo(salaryMatrixEntry.getBasicSalary());
        Assertions.assertThat(updatedSalaryMatrixEntry.getPayCut()).isEqualTo(salaryMatrixEntry.getPayCut());
        Assertions.assertThat(updatedSalaryMatrixEntry.getAllowance()).isEqualTo(salaryMatrixEntry.getAllowance());
        Assertions.assertThat(updatedSalaryMatrixEntry.getHeadOfFamily()).isEqualTo(salaryMatrixEntry.getHeadOfFamily());
    }

    // void delete(salaryMatrixEntry)
    @Test
    public void SalaryMatrixEntryRepository_Delete_ReturnSalaryMatrixEntryIsEmpty() {
        SalaryMatrixEntry newSalaryMatrixEntry = SalaryMatrixEntry.builder()
                .grade(1)
                .basicSalary(BigDecimal.valueOf(2000000))
                .payCut(BigDecimal.valueOf(50000))
                .allowance(BigDecimal.valueOf(50000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .build();

        salaryMatrixEntryRepository.save(newSalaryMatrixEntry);

        SalaryMatrixEntry salaryMatrixEntry = salaryMatrixEntryRepository.findById(newSalaryMatrixEntry.getId()).get();
        salaryMatrixEntryRepository.delete(salaryMatrixEntry);

        Optional<SalaryMatrixEntry> deletedSalaryMatrixEntry = salaryMatrixEntryRepository.findById(salaryMatrixEntry.getId());
        Assertions.assertThat(deletedSalaryMatrixEntry).isEmpty();
    }

}
