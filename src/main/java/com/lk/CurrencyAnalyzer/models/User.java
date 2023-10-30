package com.lk.CurrencyAnalyzer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.Set;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false, unique = true)
    private String userName;

    @Setter
    @Column(nullable = false)
    private String firstName;

    @Setter
    @Column(nullable = false)
    private String lastName;

    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    private Date dateOfBirth;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    public User() {}
}