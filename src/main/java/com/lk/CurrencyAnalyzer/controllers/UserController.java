package com.lk.CurrencyAnalyzer.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/test")
    public String userAccess() {
        return "User Content.";
    }
}
