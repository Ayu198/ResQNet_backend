package com.resqnet.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupUserRequest {
    @NotNull(message = "Full Name is required")
    private String fullName;

    @NotNull(message = "Password is required")
    private String password;

    @Email
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Phone Number is rquired")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
}
