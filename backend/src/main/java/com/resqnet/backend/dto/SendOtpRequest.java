package com.resqnet.backend.dto;

import com.resqnet.backend.ResQNet_Enum.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendOtpRequest {
    @NotBlank(message = "phone Number can not be blank")
    private String phoneNumber;

    @NotNull(message = "Otp purpose is Required")
    private OtpPurpose purpose;
}
