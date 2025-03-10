package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()   // ✅ Allow H2 Console
                .anyRequest().authenticated()
            )
            .httpBasic()
            .and()
            // Disable CSRF (not recommended for production unless you're stateless)
            .csrf(AbstractHttpConfigurer::disable)
            // Allow H2 console to be loaded in a frame.
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            // Use stateless session management for REST APIs.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Use withDefaultPasswordEncoder() only for development/testing purposes.
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")       // Set your username.
                .password("password")    // Set your password.
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
