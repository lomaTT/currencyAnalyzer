package com.lk.CurrencyAnalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class SignUpDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
}
