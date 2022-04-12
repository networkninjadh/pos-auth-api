package com.howtech.posauthapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.howtech.posauthapi.security.JwtRequestFilter;
import com.howtech.posauthapi.security.ApplicationUserPermission;
import com.howtech.posauthapi.security.JwtAuthenticationEntryPoint;

/**
 * 
 * @author Damond Howard
 * @apiNote Entire security policy configuration for the application this
 *          secures all routes behind certain user permissions
 */

@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * 
	 * @param passwordEncoder @Autowired passwordEncoder from our bean configuration
	 */
	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
	}

	/**
	 * @param http
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.cors().and().csrf().disable().authorizeRequests()
				/**
				 * These are a list of the permissions a user needs to have to access each
				 * endpoint admin can hit any endpoint admin will be a user on our side
				 * I will make that there can be only one admin
				 */

				/**
				 * Authentication for store api
				 */
				.antMatchers(HttpMethod.DELETE, "/store-api/**")
				.hasAuthority(ApplicationUserPermission.STORE_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/store-api/**")
				.hasAuthority(ApplicationUserPermission.STORE_READ.getPermission())
				.antMatchers(HttpMethod.POST, "/store-api/**")
				.hasAuthority(ApplicationUserPermission.STORE_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/store-api/**")
				.hasAuthority(ApplicationUserPermission.STORE_WRITE.getPermission())
				/**
				 * Authentication for store owner api
				 */
				.antMatchers(HttpMethod.GET, "/store-owner-api/**")
				.hasAuthority(ApplicationUserPermission.STORE_READ.getPermission())
				/**
				 * Authentication for file api
				 */
				.antMatchers(HttpMethod.DELETE, "/images-api/**")
				.hasAuthority(ApplicationUserPermission.FILE_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/images-api/**")
				.hasAuthority(ApplicationUserPermission.FILE_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/images-api/**")
				.hasAuthority(ApplicationUserPermission.FILE_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/images-api/**")
				.hasAuthority(ApplicationUserPermission.FILE_READ.getPermission())
				/**
				 * Authentication for Beauty Hero api
				 */
				.antMatchers(HttpMethod.DELETE, "/helper-api/**")
				.hasAuthority(ApplicationUserPermission.HELPER_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/helper-api/**")
				.hasAuthority(ApplicationUserPermission.HELPER_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/helpler-api/**")
				.hasAuthority(ApplicationUserPermission.HELPER_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/helper-api/**")
				.hasAuthority(ApplicationUserPermission.HELPER_READ.getPermission())
				/**
				 * Authentication for orders and shipments controller
				 */
				.antMatchers(HttpMethod.DELETE, "/order-api/**")
				.hasAuthority(ApplicationUserPermission.ORDER_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/order-api/**")
				.hasAuthority(ApplicationUserPermission.ORDER_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/order-api/**")
				.hasAuthority(ApplicationUserPermission.ORDER_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/order-api/**")
				.hasAuthority(ApplicationUserPermission.ORDER_READ.getPermission())
				/**
				 * Authentication for inventory api
				 */
				.antMatchers(HttpMethod.DELETE, "/inventory-api/**")
				.hasAuthority(ApplicationUserPermission.INVENTORY_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/inventory-api/**")
				.hasAuthority(ApplicationUserPermission.INVENTORY_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/inventory-api/**")
				.hasAuthority(ApplicationUserPermission.INVENTORY_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/inventory-api/**")
				.hasAuthority(ApplicationUserPermission.INVENTORY_READ.getPermission())
				/**
				 * Authentication for employee api
				 */
				.antMatchers(HttpMethod.DELETE, "/employee-api/**")
				.hasAuthority(ApplicationUserPermission.EMPLOYEE_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/employee-api/**")
				.hasAuthority(ApplicationUserPermission.EMPLOYEE_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/employee-api/**")
				.hasAuthority(ApplicationUserPermission.EMPLOYEE_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/employee-api/**")
				.hasAuthority(ApplicationUserPermission.EMPLOYEE_READ.getPermission())
				/**
				 * Authentication for driver api
				 */
				.antMatchers(HttpMethod.DELETE, "/driver-api/**")
				.hasAuthority(ApplicationUserPermission.DRIVER_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/driver-api/**")
				.hasAuthority(ApplicationUserPermission.DRIVER_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/driver-api/**")
				.hasAuthority(ApplicationUserPermission.DRIVER_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/driver-api/**")
				.hasAuthority(ApplicationUserPermission.DRIVER_READ.getPermission())
				/**
				 * Authentication for customer api
				 */
				.antMatchers(HttpMethod.DELETE, "/customer-api/**")
				.hasAuthority(ApplicationUserPermission.CUSTOMER_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/customer-api/**")
				.hasAuthority(ApplicationUserPermission.CUSTOMER_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/customer-api/**")
				.hasAuthority(ApplicationUserPermission.CUSTOMER_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/customer-api/**")
				.hasAuthority(ApplicationUserPermission.CUSTOMER_READ.getPermission())
				/**
				 * Authentication for payment api
				 */
				.antMatchers(HttpMethod.DELETE, "/payment-api/**")
				.hasAuthority(ApplicationUserPermission.PAYMENT_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/payment-api/**")
				.hasAuthority(ApplicationUserPermission.PAYMENT_WRITE.getPermission())
				.antMatchers(HttpMethod.PUT, "/payment-api/**")
				.hasAuthority(ApplicationUserPermission.PAYMENT_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/payment-api/**")
				.hasAuthority(ApplicationUserPermission.PAYMENT_READ.getPermission())
				.antMatchers(
						"/swagger-resources/**",
						"/v2/api-docs",
						"/configuration/ui",
						"/configuration/security",
						"/swagger-ui.html",
						"/webjars/**",
						"/auth-api/signin",
						"/auth-api/register",
						"/auth-api/forgot-password/**",
						"/auth-api/tmp-signin",
						"/mappings",
						"/storage/**",
						"/swagger-ui/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
