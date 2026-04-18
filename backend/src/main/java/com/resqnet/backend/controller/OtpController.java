package com.resqnet.backend.controller;

import com.resqnet.backend.dto.SendOtpRequest;
import com.resqnet.backend.dto.SendOtpResponse;
import com.resqnet.backend.dto.VerfiyOtpRequest;
import com.resqnet.backend.dto.VerifyOtpResponse;
import com.resqnet.backend.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<SendOtpResponse> sendOtp(@Valid @RequestBody SendOtpRequest sendOtpRequest) {
        SendOtpResponse response = otpService.sendOtp(sendOtpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtpResponse> verifyOtp(@Valid @RequestBody VerfiyOtpRequest verifyOtpRequest) {
        VerifyOtpResponse response = otpService.verifyOtp(verifyOtpRequest);
        return ResponseEntity.ok(response);
    }
}
