package com.himanshu.event_ticketing_system.service;

import com.himanshu.event_ticketing_system.dto.RegisterRequest;
import com.himanshu.event_ticketing_system.dto.UserResponse;
import com.himanshu.event_ticketing_system.entity.User;
import com.himanshu.event_ticketing_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponse registerUser(RegisterRequest request){
        if(userRepository.findByEmail(request.email()).isPresent()){
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

        User user = modelMapper.map(request , User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser , UserResponse.class);

    }

}
