package com.lk.CurrencyAnalyzer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Imię jest obowiązkowe")
    private String firstName;

    @NotBlank(message = "Nazwisko jest obowiązkowe")
    private String lastName;

    @NotBlank(message = "Proszę wprowadzić datę urodzenia")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Hasło jest niezbędne")
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