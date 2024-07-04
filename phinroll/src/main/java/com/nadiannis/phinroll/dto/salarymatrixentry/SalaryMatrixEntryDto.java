package com.nadiannis.phinroll.dto.salarymatrixentry;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryMatrixEntryDto {

    private String id;

    @NotNull(message = "grade is required")
    @Range(min = 1, max = 10, message = "grade should be in range 1 to 10")
    private Integer grade;

    @NotNull(message = "basic_salary is required")
    @Min(value = 2000000, message = "basic_salary should be at least 2000000")
    @JsonProperty("basic_salary")
    private BigDecimal basicSalary;

    @NotNull(message = "pay_cut is required")
    @Min(value = 0, message = "pay_cut should not be a negative number")
    @JsonProperty("pay_cut")
    private BigDecimal payCut;

    @NotNull(message = "allowance is required")
    @Min(value = 0, message = "allowance should not be a negative number")
    private BigDecimal allowance;

    @NotNull(message = "head_of_family is required")
    @Min(value = 0, message = "head_of_family should not be a negative number")
    @JsonProperty("head_of_family")
    private BigDecimal headOfFamily;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
