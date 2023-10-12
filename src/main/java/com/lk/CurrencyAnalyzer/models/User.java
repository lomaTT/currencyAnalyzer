package com.lk.CurrencyAnalyzer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Setter @Getter
    private String firstName;

    @Setter @Getter
    private String lastName;

    @Setter @Getter
    private String email;

    @Setter @Getter
    private LocalDate dateOfBirth;

    @Setter @Getter
    private String password;



    // Hibernate expects entities to have a no-arg constructor,
    // though it does not necessarily have to be public.
    public User() {}

    public User(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

}