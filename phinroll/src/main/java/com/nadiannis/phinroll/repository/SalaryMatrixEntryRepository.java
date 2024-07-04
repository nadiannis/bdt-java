package com.nadiannis.phinroll.repository;

import com.nadiannis.phinroll.model.SalaryMatrixEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryMatrixEntryRepository extends JpaRepository<SalaryMatrixEntry, String> {
    Optional<SalaryMatrixEntry> findByGrade(int grade);
}
