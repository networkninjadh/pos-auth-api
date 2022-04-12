package com.howtech.posauthapi.controllers;

import com.howtech.posauthapi.DTOs.TempCredentials;
import com.howtech.posauthapi.models.JwtRequest;
import com.howtech.posauthapi.services.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Damond Howard
 * @apiNote this is a restful controller that handles all registration and
 *          authentication of users both customers and store owners
 *
 */
@RestController
@RequestMapping(path = "/auth-api/")
public class AuthenticationController {

	private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
		authenticationService.delete(userDetails);
	}

	/**
	 * 
	 * @param authenticationRequest
	 * @return the newly registered users information
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public UserDetails registerUser(@RequestBody JwtRequest authenticationRequest) throws Exception {
		return authenticationService.register(authenticationRequest);
	}

	/**
	 * 
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		return authenticationService.createAuthenticationToken(authenticationRequest);
	}

	/**
	 * 
	 * @param credentials
	 * @return
	 */
	@RequestMapping(value = "/tmp-signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenWithTempPass(@RequestBody TempCredentials credentials) {
		return authenticationService.createAuthenticationTokenWithTempPass(credentials);
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/forgot-password/{username}", method = RequestMethod.POST)
	public String forgotPassword(@PathVariable(name = "username") String username) {
		return authenticationService.forgotPassword(username);
	}

}