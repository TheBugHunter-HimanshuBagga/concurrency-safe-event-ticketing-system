package com.himanshu.event_ticketing_system.security;

import com.himanshu.event_ticketing_system.service.CustomUserDetailsService;
import com.himanshu.event_ticketing_system.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // create an object for this class
// In our case Spring will create JwtAuthenticationFilter object and inject it whenever needed
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            email = jwtUtil.extractEmail(token);
        }
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){ // “Check ID (JWT), verify it, then tell security desk who this person is”
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email); // now i have username , password and roles
            if(jwtUtil.isTokenValid(token , email)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, // who
                        null, // already verified by jwt
                        userDetails.getAuthorities() // roles
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // IP  address + session Info
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // now its authenticated and here is the user
            }
        }
        filterChain.doFilter(request, response);
    }
}

/*
✅ 1. JWT Authentication Filter

→ Reads token from request

✅ 2. UserDetailsService

→ Loads user from DB

✅ 3. SecurityConfig (FULL)

→ Tells Spring:

which routes are public
which are protected
how JWT is used


✅✅✅✅
Anywhere in your app, Spring can now do:

SecurityContextHolder.getContext().getAuthentication()

And get:

user email
roles
authorities


Request comes with JWT
Filter runs
Extract email
Load user from DB
Validate token
Create Authentication object
Store in SecurityContextHolder
Continue request
Controller runs → user is now “logged in”


🧩 Step 5: JwtAuthenticationFilter (🔥 MAIN LOGIC)

This runs before controller

It does:
Extract token
Extract email
Load user via CustomUserDetailsService
Validate token
Create Authentication
Store in SecurityContextHolder

👉 This is where user becomes “logged in” for that request
 */
