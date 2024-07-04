package com.nadiannis.phinroll.repository;

import com.nadiannis.phinroll.model.Employee;
import com.nadiannis.phinroll.model.Payroll;
import com.nadiannis.phinroll.model.SalaryMatrixEntry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PayrollRepositoryTests {

    @Autowired
    private PayrollRepository payrollRepository;

    // Page<Payroll> findAll(pageable)
    @Test
    public void PayrollRepository_FindAll_ReturnMoreThanOnePayrolls() {
        Employee employee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(1)
                .isMarried(true)
                .build();
        Payroll payroll1 = Payroll.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employee)
                .build();
        Payroll payroll2 = Payroll.builder()
                .attendanceDays(10)
                .absenceDays(10)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(500000))
                .totalAllowance(BigDecimal.valueOf(500000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(600000))
                .startDate(LocalDate.parse("2024-07-01"))
                .endDate(LocalDate.parse("2024-07-31"))
                .employee(employee)
                .build();

        payrollRepository.save(payroll1);
        payrollRepository.save(payroll2);

        List<Payroll> payrolls = payrollRepository.findAll();

        Assertions.assertThat(payrolls).isNotNull();
        Assertions.assertThat(payrolls.size()).isEqualTo(2);
    }

    // Payroll save(payroll)
    @Test
    public void PayrollRepository_Save_ReturnSavedPayroll() {
        Employee employee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(1)
                .isMarried(true)
                .build();
        Payroll payroll = Payroll.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employee)
                .build();

        Payroll savedPayroll = payrollRepository.save(payroll);

        Assertions.assertThat(savedPayroll).isNotNull();
        Assertions.assertThat(savedPayroll.getId()).isNotNull();
        Assertions.assertThat(savedPayroll.getId()).isEqualTo(payroll.getId());
    }

    // Optional<Payroll> findById(id)
    @Test
    public void PayrollRepository_FindById_ReturnPayroll() {
        Employee employee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(1)
                .isMarried(true)
                .build();
        Payroll newPayroll = Payroll.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employee)
                .build();

        payrollRepository.save(newPayroll);

        Payroll payroll = payrollRepository.findById(newPayroll.getId()).get();

        Assertions.assertThat(payroll).isNotNull();
        Assertions.assertThat(payroll.getId()).isEqualTo(newPayroll.getId());
    }

    // void delete(payroll)
    @Test
    public void PayrollRepository_Delete_ReturnPayrollIsEmpty() {
        Employee employee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(1)
                .isMarried(true)
                .build();
        Payroll newPayroll = Payroll.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employee)
                .build();

        payrollRepository.save(newPayroll);

        Payroll payroll = payrollRepository.findById(newPayroll.getId()).get();
        payrollRepository.delete(payroll);

        Optional<Payroll> deletedPayroll = payrollRepository.findById(payroll.getId());
        Assertions.assertThat(deletedPayroll).isEmpty();
    }

    // Page<Payroll> findByEmployeeId(id, pageable)
    @Test
    public void PayrollRepository_FindByEmployeeId_ReturnMoreThanOnePayrolls() {
        Employee employee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(1)
                .isMarried(true)
                .build();
        Payroll payroll1 = Payroll.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employee)
                .build();
        Payroll payroll2 = Payroll.builder()
                .attendanceDays(10)
                .absenceDays(10)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(500000))
                .totalAllowance(BigDecimal.valueOf(500000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(600000))
                .startDate(LocalDate.parse("2024-07-01"))
                .endDate(LocalDate.parse("2024-07-31"))
                .employee(employee)
                .build();

        payrollRepository.save(payroll1);
        payrollRepository.save(payroll2);

        List<Payroll> payrolls = payrollRepository.findByEmployeeId(employee.getId(), Pageable.unpaged()).getContent();

        Assertions.assertThat(payrolls).isNotNull();
        Assertions.assertThat(payrolls.size()).isEqualTo(2);
    }

    // boolean existsByEmployeeAndOverlappingPeriod(employeeId, startDate, endDate)
    @Test
    public void PayrollRepository_ExistsByEmployeeAndOverlappingPeriod_ReturnTrue() {
        Employee employee = Employee.builder()
                .name("John")
                .gender("m")
                .grade(1)
                .isMarried(true)
                .build();
        Payroll payroll = Payroll.builder()
                .attendanceDays(15)
                .absenceDays(5)
                .basicSalary(BigDecimal.valueOf(2000000))
                .totalPayCut(BigDecimal.valueOf(250000))
                .totalAllowance(BigDecimal.valueOf(750000))
                .headOfFamily(BigDecimal.valueOf(100000))
                .totalAdditionalSalary(BigDecimal.valueOf(850000))
                .startDate(LocalDate.parse("2024-06-01"))
                .endDate(LocalDate.parse("2024-06-30"))
                .employee(employee)
                .build();
        payrollRepository.save(payroll);

        boolean doesPayrollExists = payrollRepository.existsByEmployeeAndOverlappingPeriod(employee.getId(), LocalDate.parse("2024-06-15"), LocalDate.parse("2024-07-15"));

        Assertions.assertThat(doesPayrollExists).isTrue();
    }

}
