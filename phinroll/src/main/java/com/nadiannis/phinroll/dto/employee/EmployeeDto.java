package com.nadiannis.phinroll.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private String id;

    @NotBlank(message = "name is required")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;

    @NotBlank(message = "gender is required")
    @Pattern(regexp = "[mf]", message = "gender should be a character 'm' for male and 'f' for female")
    private String gender;

    @NotNull(message = "grade is required")
    @Range(min = 1, max = 10, message = "grade should be in range 1 to 10")
    private Integer grade;

    @NotNull(message = "is_married is required")
    @JsonProperty("is_married")
    private Boolean isMarried;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}
