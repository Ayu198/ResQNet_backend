package com.resqnet.backend.service;

import com.resqnet.backend.ResQNet_Enum.OtpPurpose;
import com.resqnet.backend.dto.SendOtpRequest;
import com.resqnet.backend.dto.SendOtpResponse;
import com.resqnet.backend.dto.VerfiyOtpRequest;
import com.resqnet.backend.dto.VerifyOtpResponse;
import com.resqnet.backend.entity.OtpRequest;
import com.resqnet.backend.entity.User;
import com.resqnet.backend.exception.InvalidCredentialsException;
import com.resqnet.backend.exception.ResourceNotFoundException;
import com.resqnet.backend.repository.OtpRequestRepository;
import com.resqnet.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRequestRepository otpRequestRepository;
    private final UserRepository userRepository;

    public OtpService(OtpRequestRepository otpRequestRepository,
                      UserRepository userRepository) {
        this.otpRequestRepository = otpRequestRepository;
        this.userRepository = userRepository;
    }

    public SendOtpResponse sendOtp(SendOtpRequest sendOtpRequest) {
        String generatedOtp = generateSixDigitOtp();

        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setPhoneNumber(sendOtpRequest.getPhoneNumber());
        otpRequest.setOtpCode(generatedOtp);
        otpRequest.setOtpPurpose(sendOtpRequest.getPurpose());
        otpRequest.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otpRequest.setIsVerified(false);

        otpRequestRepository.save(otpRequest);

        return SendOtpResponse.builder()
                .phoneNumber(sendOtpRequest.getPhoneNumber())
                .purpose(sendOtpRequest.getPurpose())
                .otpCode(generatedOtp)
                .message("OTP sent successfully")
                .build();
    }

    public VerifyOtpResponse verifyOtp(VerfiyOtpRequest verifyOtpRequest) {
        OtpRequest otpRequest = otpRequestRepository
                .findTopByPhoneNumberAndPurposeOrderByCreatedAtDesc(
                        verifyOtpRequest.getPhoneNumber(),
                        verifyOtpRequest.getPurpose()
                )
                .orElseThrow(() -> new ResourceNotFoundException("OTP request not found"));

        if (Boolean.TRUE.equals(otpRequest.getIsVerified())) {
            throw new InvalidCredentialsException("OTP already used");
        }

        if (otpRequest.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidCredentialsException("OTP has expired");
        }

        if (!otpRequest.getOtpCode().equals(verifyOtpRequest.getOtpCode())) {
            throw new InvalidCredentialsException("Invalid OTP");
        }

        otpRequest.setIsVerified(true);
        otpRequestRepository.save(otpRequest);

        if (otpRequest.getOtpPurpose() == OtpPurpose.SIGNUP ||
                otpRequest.getOtpPurpose() == OtpPurpose.VOLUNTEER_SIGNUP) {

            User user = userRepository.findByPhoneNumber(verifyOtpRequest.getPhoneNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            user.setIsPhoneVerified(true);
            userRepository.save(user);
        }

        return VerifyOtpResponse.builder()
                .phoneNumber(verifyOtpRequest.getPhoneNumber())
                .purpose(verifyOtpRequest.getPurpose())
                .verified(true)
                .message("OTP verified successfully")
                .build();
    }

    private String generateSixDigitOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }
}
