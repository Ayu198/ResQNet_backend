package com.resqnet.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull(message = "phone number is required")
    private String phoneNumber;

    @NotNull(message = "password is required")
    private String password;
}
