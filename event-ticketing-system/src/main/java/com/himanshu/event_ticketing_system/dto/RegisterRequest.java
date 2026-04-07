package com.himanshu.event_ticketing_system.dto;

import com.himanshu.event_ticketing_system.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        Role role
) {
}
