package com.himanshu.event_ticketing_system.config;

import com.himanshu.event_ticketing_system.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // “Use BCrypt for encoding passwords”
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        // ADMIN only
                        .requestMatchers(HttpMethod.POST,"/events/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/events/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/events/**").hasRole("ADMIN")
                        // USER + ADMIN
                        .requestMatchers(HttpMethod.GET,"/events/**").authenticated()
                        // bookings → any logged-in user
                        .requestMatchers("/bookings/**").authenticated()
                        // fallback
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        httpSecurity.addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build(); //  Converts config → actual working filter chain
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
/*
Request → Security Filters → Authentication Check → Authorization Check → Controller

Request
   ↓
Our JWTAuthentication Filter ✅
   ↓
Spring Security checks
   ↓
Controller


Request → Filter 1 → Filter 2 → Filter 3 → ... → Controller


SecurityFilterChain → rules + flow
JwtAuthenticationFilter → extracts token
AuthenticationManager → verifies credentials

 */
