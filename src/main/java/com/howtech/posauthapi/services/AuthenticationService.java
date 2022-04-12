package com.howtech.posauthapi.services;

import java.nio.charset.Charset;
import java.util.Random;

import com.howtech.posauthapi.DTOs.TempCredentials;
import com.howtech.posauthapi.security.JwtTokenUtil;
import com.howtech.posauthapi.models.JwtRequest;
import com.howtech.posauthapi.models.JwtResponse;
import com.howtech.posauthapi.models.User;
import com.howtech.posauthapi.services.JwtUserDetailsService;
import com.howtech.posauthapi.services.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author Damond Howard
 */
@Service
public class AuthenticationService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService userDetailsService, EmailService emailService,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void delete(UserDetails userDetails) {
        userDetailsService.deleteUserByUsername(userDetails.getUsername());
    }

    public UserDetails register(JwtRequest authenticationRequest) {
        if (authenticationRequest.getRole() == null || authenticationRequest.getEmail() == null
                || authenticationRequest.getPassword() == null) {
            return null;
        }
        if (this.userDetailsService.existsUsername(authenticationRequest.getUsername())) {
            return this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        }
        final UserDetails userDetails = userDetailsService.createUserByUsername(authenticationRequest.getUsername(),
                authenticationRequest.getPassword(), authenticationRequest.getRole(), authenticationRequest.getEmail(),
                authenticationRequest.getFirstName(), authenticationRequest.getLastName());
        return userDetails;
    }

    public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * 
     * @param username
     * @param password
     * @throws Exception
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public ResponseEntity<?> createAuthenticationTokenWithTempPass(TempCredentials credentials) {
        if (credentials.getTmpPassword().equals(null)) {
            return ResponseEntity.ok("Wrong password");
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(credentials.getUsername());
        final User user = (User) userDetailsService.loadUserByUsername(credentials.getUsername());
        if (!user.getTmpPassword().equals(passwordEncoder.encode(credentials.getTmpPassword()))) {
            throw new AccessDeniedException("You don't have the correct temporary password");
        }
        user.setTmpPassword(null);
        userDetailsService.saveUser(user);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public String forgotPassword(String username) {
        String tmpPassword = generateTmpPassword();
        User user = userDetailsService.getUser(username).get();
        user.setTmpPassword(passwordEncoder.encode(tmpPassword));
        this.userDetailsService.saveUser(user);
        try {
            // emailService.send(user.getEmail(), "Temporary Password", tmpPassword);
            LOGGER.info("Send email with temp password to user");
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        return "Email sent successfully";
    }

    private String generateTmpPassword() {
        byte[] array = new byte[8];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

}
