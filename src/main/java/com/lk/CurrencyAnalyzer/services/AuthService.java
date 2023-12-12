package com.lk.CurrencyAnalyzer.Services;

import com.lk.CurrencyAnalyzer.Payload.Request.LoginRequest;
import com.lk.CurrencyAnalyzer.Payload.Request.SignupRequest;
import com.lk.CurrencyAnalyzer.Payload.Response.MessageResponse;
import com.lk.CurrencyAnalyzer.Payload.Response.UserInfoResponse;
import com.lk.CurrencyAnalyzer.Security.jwt.JwtUtils;
import com.lk.CurrencyAnalyzer.services.UserDetailsImpl;
import org.springframework.http.ResponseCookie;

public interface AuthService {

    UserDetailsImpl authenticateUser(LoginRequest loginRequest);

    ResponseCookie generateJwtCookie(UserDetailsImpl userDetails);

    MessageResponse registerUser(SignupRequest signUpRequest);

    ResponseCookie getCleanJwtCookie();
}