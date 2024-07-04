package com.nadiannis.phinroll.dto.payroll;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nadiannis.phinroll.dto.employee.EmployeeMinimalDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDto {

    private String id;

    @JsonProperty("attendance_days")
    private Integer attendanceDays;

    @JsonProperty("absence_days")
    private Integer absenceDays;

    @JsonProperty("basic_salary")
    private BigDecimal basicSalary;

    @JsonProperty("total_pay_cut")
    private BigDecimal totalPayCut;

    @JsonProperty("total_allowance")
    private BigDecimal totalAllowance;

    @JsonProperty("head_of_family")
    private BigDecimal headOfFamily;

    @JsonProperty("total_additional_salary")
    private BigDecimal totalAdditionalSalary;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private EmployeeMinimalDto employee;

}
