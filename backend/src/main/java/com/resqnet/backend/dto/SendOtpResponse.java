package com.resqnet.backend.dto;

import com.resqnet.backend.ResQNet_Enum.OtpPurpose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendOtpResponse {
    private String phoneNumber;
    private OtpPurpose purpose;
    private String otpCode;
    private String message;
}
