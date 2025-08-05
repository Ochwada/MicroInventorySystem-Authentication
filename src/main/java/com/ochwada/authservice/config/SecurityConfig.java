package com.ochwada.authservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * *******************************************************
 * Package: com.ochwada.authservice.config
 * File: SecurityConfig.java
 * Author: Ochwada
 * Date: Tuesday, 05.Aug.2025, 1:40 PM
 * Description:Used to define beans, settings, and application setup logic
 * *******************************************************
 */

@Configuration
public class SecurityConfig {

    /**
     * Defines the security filter chain bean for the application.
     *
     * @param httpSecurity the HttpSecurity object used to configure security features
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity
                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow unrestricted access to the home page, login page, and all OAuth2-related endpoints
                        .requestMatchers("/", "/my_login", "/oauth2/**").permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                // Enable OAuth2 Login
                .oauth2Login(oauth2 -> oauth2
                        // Custom login page path
                        .loginPage("/my_login")
                        // Redirect to dashboard after successful login
                        .defaultSuccessUrl("/dashboard", true));  // Enables OAuth2 social my_login.html

        // Build and return the configured filter chain
        return httpSecurity.build();
    }
}
