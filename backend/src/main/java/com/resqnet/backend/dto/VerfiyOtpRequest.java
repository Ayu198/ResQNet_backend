package com.resqnet.backend.dto;

import com.resqnet.backend.ResQNet_Enum.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerfiyOtpRequest {
    @NotBlank(message = "phone Number is required")
    private String phoneNumber;

    @NotBlank(message = "otp is required")
    private String otpCode;

    @NotBlank(message = "otp purpose is required")
    private OtpPurpose purpose;
}
