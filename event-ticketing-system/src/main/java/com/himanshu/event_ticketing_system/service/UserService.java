package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.config.SecurityConfig;
import com.himanshu.event_ticketing_system.dto.LoginRequest;
import com.himanshu.event_ticketing_system.dto.LoginResponse;
import com.himanshu.event_ticketing_system.dto.RegisterRequest;
import com.himanshu.event_ticketing_system.dto.UserResponse;
import com.himanshu.event_ticketing_system.entity.User;
import com.himanshu.event_ticketing_system.repository.UserRepository;
import com.himanshu.event_ticketing_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponse registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
        // if not present then create new user

//        User user = new User();
//        user.setName(request.name());
//        user.setEmail(request.email());
//        user.setPassword(request.password());
//        user.setRole(request.role());
//
//        User savedUser = userRepository.save(user);
//        return new UserResponse(
//                savedUser.getId(),
//                savedUser.getName(),
//                savedUser.getEmail()
//        );

        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);

        /* OUTPUT
           {
            "timestamp": "06:23:46 08-04-2026",
            "data": {
                        "id": 2,
                        "name": "Himanshu",
                        "email": "test@gmail.com"
             },
            "error": null
        }
         */

    }

    public LoginResponse loginUser(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        return new LoginResponse(accessToken , refreshToken , "LoggedIn Successfully");
    }

}
