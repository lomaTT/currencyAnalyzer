package com.lk.CurrencyAnalyzer.controllers;

import com.lk.CurrencyAnalyzer.dto.LoginDto;
import com.lk.CurrencyAnalyzer.models.Currency;
import com.lk.CurrencyAnalyzer.models.Role;
import com.lk.CurrencyAnalyzer.models.User;
import com.lk.CurrencyAnalyzer.repositories.CurrencyRepository;
import com.lk.CurrencyAnalyzer.repositories.RoleRepository;
import com.lk.CurrencyAnalyzer.repositories.UserRepository;
import com.lk.CurrencyAnalyzer.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*")
@RequestMapping("/api")
public class HomeController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CurrencyRepository currencyRepository;

    @PostMapping("/login")
    public Map authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(), loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Collections.singletonMap("status", "ok");
        } catch (Exception e) {
            return Collections.singletonMap("status", "incorrectCredentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        if (userRepository.existsUserByUserName(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsUserByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(signUpDto.getUsername());
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setDateOfBirth(signUpDto.getDateOfBirth());

        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        Currency currency = currencyRepository.findByValue("CURRENCY_USD");
        user.setCurrency(currency);

        userRepository.save(user);
        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}
