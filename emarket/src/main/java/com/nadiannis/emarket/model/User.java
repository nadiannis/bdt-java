package com.nadiannis.emarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity  // This tells Hibernate to make a table out of this class
@Table(name = "users")  // In PostgreSQL, there are reserved table called user, so we should use another name
public class User {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

}
