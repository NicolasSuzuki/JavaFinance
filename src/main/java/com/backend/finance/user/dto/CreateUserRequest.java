package com.backend.finance.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 120, message = "Name must have at most 120 characters")
    String name,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 180, message = "Email must have at most 180 characters")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must have at least 6 characters and at most 255 characters")
    String password
) {
    
}
