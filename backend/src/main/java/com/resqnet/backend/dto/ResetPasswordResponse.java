package com.resqnet.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResetPasswordResponse {
    private String phoneNumber;
    private String message;
}
