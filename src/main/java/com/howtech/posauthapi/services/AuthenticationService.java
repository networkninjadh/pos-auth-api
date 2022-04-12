package com.howtech.posauthapi.services;

import com.howtech.posauthapi.DTOs.TempCredentials;
import com.howtech.posauthapi.models.JwtRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationService {

    public void delete(UserDetails userDetails) {
    }

    public UserDetails register(JwtRequest authenticationRequest) {
        return null;
    }

    public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) {
        return null;
    }

    public String forgotPassword(String username) {
        return null;
    }

    public ResponseEntity<?> createAuthenticationTokenWithTempPass(TempCredentials credentials) {
        return null;
    }

}
