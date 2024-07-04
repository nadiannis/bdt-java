package com.nadiannis.phinroll.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payrolls")
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "attendance_days", nullable = false, columnDefinition = "integer default 0")
    @JsonProperty("attendance_days")
    private Integer attendanceDays;

    @Column(name = "absence_days", nullable = false, columnDefinition = "integer default 0")
    @JsonProperty("absence_days")
    private Integer absenceDays;

    @Column(name = "basic_salary", nullable = false)
    @JsonProperty("basic_salary")
    private BigDecimal basicSalary;

    @Column(name = "total_pay_cut", nullable = false)
    @JsonProperty("total_pay_cut")
    private BigDecimal totalPayCut;

    @Column(name = "total_allowance", nullable = false)
    @JsonProperty("total_allowance")
    private BigDecimal totalAllowance;

    @Column(name = "head_of_family", nullable = false)
    @JsonProperty("head_of_family")
    private BigDecimal headOfFamily;

    @Column(name = "total_additional_salary", nullable = false)
    @JsonProperty("total_additional_salary")
    private BigDecimal totalAdditionalSalary;

    @Column(name = "start_date", nullable = false)
    @JsonProperty("start_date")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @JsonProperty("end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public Payroll(Integer attendanceDays,
                   Integer absenceDays,
                   BigDecimal basicSalary,
                   BigDecimal totalPayCut,
                   BigDecimal totalAllowance,
                   BigDecimal headOfFamily,
                   BigDecimal totalAdditionalSalary,
                   LocalDate startDate,
                   LocalDate endDate,
                   Employee employee) {
        this.attendanceDays = attendanceDays;
        this.absenceDays = absenceDays;
        this.basicSalary = basicSalary;
        this.totalPayCut = totalPayCut;
        this.totalAllowance = totalAllowance;
        this.headOfFamily = headOfFamily;
        this.totalAdditionalSalary = totalAdditionalSalary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
    }

}
