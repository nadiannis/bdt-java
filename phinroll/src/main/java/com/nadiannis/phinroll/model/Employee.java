package com.nadiannis.phinroll.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "gender", length = 1, nullable = false)
    private String gender;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "is_married", nullable = false, columnDefinition = "boolean default false")
    @JsonProperty("is_married")
    private Boolean isMarried;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public Employee(String name, String gender, Integer grade, Boolean isMarried) {
        this.name = name;
        this.gender = gender;
        this.grade = grade;
        this.isMarried = isMarried;
    }

}
