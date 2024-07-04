package com.nadiannis.phinroll.repository;

import com.nadiannis.phinroll.model.Payroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PayrollRepository extends JpaRepository<Payroll, String> {

    @Query("SELECT p FROM Payroll p WHERE p.employee.id = :employeeId")
    Page<Payroll> findByEmployeeId(@Param("employeeId") String employeeId, Pageable pageable);

    @Query("SELECT COUNT(p) > 0 FROM Payroll p WHERE p.employee.id = :employeeId AND (" +
            "(:startDate BETWEEN p.startDate AND p.endDate) OR " +
            "(:endDate BETWEEN p.startDate AND p.endDate) OR " +
            "(p.startDate BETWEEN :startDate AND :endDate) OR " +
            "(p.endDate BETWEEN :startDate AND :endDate))")
    boolean existsByEmployeeAndOverlappingPeriod(@Param("employeeId") String employeeId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

}
