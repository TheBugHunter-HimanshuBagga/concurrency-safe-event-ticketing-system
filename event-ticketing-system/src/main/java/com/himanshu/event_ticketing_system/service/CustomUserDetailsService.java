package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.dto.UserResponse;
import com.himanshu.event_ticketing_system.entity.User;
import com.himanshu.event_ticketing_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name()) // USER / ADMIN
                .build();
    }
}
/*
🔐 User logs in
JWT gets generated
Later request comes with token
JwtAuthenticationFilter → extracts email
👉 calls loadUserByUsername(email) (this class)
You return:
email ✅
password ✅
role ✅


🧩 Step 3: CustomUserDetailsService

✔️  implemented:

loadUserByUsername(email)

👉 Purpose:

“Given email → fetch user from DB → convert into Spring Security format”

⚠️ Important:

This is used by Spring internally
Not directly by controller
 */
