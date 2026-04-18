package com.resqnet.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthMeRequest {
    @NotNull(message = "User Id is required")
    private Long userId;
}
