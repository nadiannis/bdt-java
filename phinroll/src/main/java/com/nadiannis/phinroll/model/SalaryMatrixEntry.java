package com.nadiannis.phinroll.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary_matrix")
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryMatrixEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "grade", nullable = false, unique = true)
    private Integer grade;

    @Column(name = "basic_salary", nullable = false)
    @JsonProperty("basic_salary")
    private BigDecimal basicSalary;

    @Column(name = "pay_cut", nullable = false)
    @JsonProperty("pay_cut")
    private BigDecimal payCut;

    @Column(name = "allowance", nullable = false)
    private BigDecimal allowance;

    @Column(name = "head_of_family", nullable = false)
    @JsonProperty("head_of_family")
    private BigDecimal headOfFamily;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public SalaryMatrixEntry(Integer grade,
                             BigDecimal basicSalary,
                             BigDecimal payCut,
                             BigDecimal allowance,
                             BigDecimal headOfFamily) {
        this.grade = grade;
        this.basicSalary = basicSalary;
        this.payCut = payCut;
        this.allowance = allowance;
        this.headOfFamily = headOfFamily;
    }

}
