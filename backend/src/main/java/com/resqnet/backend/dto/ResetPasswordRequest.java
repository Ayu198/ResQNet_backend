package com.resqnet.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    @NotBlank(message = "phone Number is required")
    private String phoneNumber;

    @NotBlank(message = "New Password required")
    private String newPassword;
}
