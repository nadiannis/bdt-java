package com.nadiannis.phinroll.dto.payroll;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollFormDto {

    @NotBlank(message = "employee_id is required")
    @JsonProperty("employee_id")
    private String employeeId;

    @NotNull(message = "attendance_days is required")
    @Min(value = 0, message = "attendance_days should not be a negative number")
    @JsonProperty("attendance_days")
    private Integer attendanceDays;

    @NotNull(message = "absence_days is required")
    @Min(value = 0, message = "absence_days should not be a negative number")
    @JsonProperty("absence_days")
    private Integer absenceDays;

    @NotNull(message = "start_date is required")
    @JsonProperty("start_date")
    private LocalDate startDate;

    @NotNull(message = "end_date is required")
    @JsonProperty("end_date")
    private LocalDate endDate;

}
