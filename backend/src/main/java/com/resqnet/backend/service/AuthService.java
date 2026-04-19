package com.resqnet.backend.service;

import com.resqnet.backend.ResQNet_Enum.ApprovalStatus;
import com.resqnet.backend.ResQNet_Enum.OtpPurpose;
import com.resqnet.backend.ResQNet_Enum.UserType;
import com.resqnet.backend.dto.*;
import com.resqnet.backend.entity.OtpRequest;
import com.resqnet.backend.entity.User;
import com.resqnet.backend.entity.VolunteerProfile;
import com.resqnet.backend.exception.DuplicateResourceException;
import com.resqnet.backend.exception.InvalidCredentialsException;
import com.resqnet.backend.exception.ResourceNotFoundException;
import com.resqnet.backend.repository.OtpRequestRepository;
import com.resqnet.backend.repository.UserRepository;
import com.resqnet.backend.repository.VolunteerProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpRequestRepository otpRequestRepository;
    private final OtpService otpService;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       VolunteerProfileRepository volunteerProfileRepository,
                       PasswordEncoder passwordEncoder,
                       OtpRequestRepository otpRequestRepository,
                       OtpService otpService,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpRequestRepository = otpRequestRepository;
        this.otpService = otpService;
        this.jwtService = jwtService;
    }

    //login Service
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid phone number or password");
        }
        String token = jwtService.generateToken(user.getId() , user.getPhoneNumber());

        LoginResponse.LoginResponseBuilder loginResponseBuilder = LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .userType(user.getUserType())
                .message("Login successful");

        if (user.getUserType() == UserType.VOLUNTEER) {
            VolunteerProfile volunteerProfile = volunteerProfileRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Volunteer profile not found"));

            loginResponseBuilder
                    .volunteerType(volunteerProfile.getVolunteerType())
                    .approvalStatus(volunteerProfile.getApprovalStatus());
        }

        return loginResponseBuilder.build();
    }

    //User Signup Service
    public SignupUserResponse signupUser(SignupUserRequest signupUserRequest) {
        if(userRepository.existsByPhoneNumber(signupUserRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists");
        }
        if(userRepository.existsByEmail(signupUserRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = new User();
        user.setFullName(signupUserRequest.getFullName());
        user.setEmail(signupUserRequest.getEmail());
        user.setPhoneNumber(signupUserRequest.getPhoneNumber());
        user.setPasswordHash(passwordEncoder.encode(signupUserRequest.getPassword()));
        user.setUserType(UserType.NORMAL_USER);
        user.setIsPhoneVerified(false);
        user.setIsActive(true);

        User saveduser = userRepository.save(user);

        SignupUserResponse response = SignupUserResponse.builder()
                .userId(saveduser.getId())
                .email(saveduser.getEmail())
                .fullName(saveduser.getFullName())
                .usertype(saveduser.getUserType())
                .phoneNumber(saveduser.getPhoneNumber())
                .message("User SignUp Successful")
                .build();
        return response;
    }

    //VolunteerSignup Service
    public SignupVolunteerResponse signupVolunteer(SignupVolunteerRequest signupVolunteerRequest) {
        if(userRepository.existsByPhoneNumber(signupVolunteerRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists");
        }
        if(userRepository.existsByEmail(signupVolunteerRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = new User();
        user.setFullName(signupVolunteerRequest.getFullName());
        user.setEmail(signupVolunteerRequest.getEmail());
        user.setPhoneNumber(signupVolunteerRequest.getPhoneNumber());
        user.setPasswordHash(passwordEncoder.encode(signupVolunteerRequest.getPassword()));
        user.setUserType(UserType.VOLUNTEER);
        user.setIsPhoneVerified(false);
        user.setIsActive(true);

        User savedUser = userRepository.save(user);

        VolunteerProfile volunteerProfile = new VolunteerProfile();
        volunteerProfile.setUser(savedUser);
        volunteerProfile.setVolunteerType(signupVolunteerRequest.getVolunteerType());
        volunteerProfile.setApprovalStatus(ApprovalStatus.PENDING);
        volunteerProfile.setBackgroundCategory(signupVolunteerRequest.getBackgroundCategory());
        volunteerProfile.setOrganization(signupVolunteerRequest.getOrganisation());
        volunteerProfile.setExperienceLevel(signupVolunteerRequest.getExperienceLevel());
        volunteerProfile.setExperienceSummary(signupVolunteerRequest.getExperienceSummary());
        volunteerProfile.setSafetyAgreed(signupVolunteerRequest.getSafetyAgreed());

        VolunteerProfile savedVolunteerProfile = volunteerProfileRepository.save(volunteerProfile);

        return SignupVolunteerResponse.builder()
                .userId(savedUser.getId())
                .volunteerProfileId(savedVolunteerProfile.getId())
                .fullName(savedUser.getFullName())
                .phoneNumber(savedUser.getPhoneNumber())
                .email(savedUser.getEmail())
                .userType(savedUser.getUserType())
                .volunteerType(savedVolunteerProfile.getVolunteerType())
                .approvalStatus(savedVolunteerProfile.getApprovalStatus())
                .message("Volunteer signup successful. Your application is under review.")
                .build();
    }

    //Auth Me service
    public AuthMeResponse authMe(User user) {
        AuthMeResponse.AuthMeResponseBuilder responseBuilder = AuthMeResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .userType(user.getUserType())
                .isPhoneVerified(user.getIsPhoneVerified())
                .isActive(user.getIsActive())
                .message("User fetched successfully");
        if(user.getUserType() == UserType.VOLUNTEER) {
            VolunteerProfile volunteerProfile = volunteerProfileRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Volunteer profile not found"));
            responseBuilder
                    .volunteerType(volunteerProfile.getVolunteerType())
                    .approvalStatus(volunteerProfile.getApprovalStatus());
        }
        return responseBuilder.build();
    }

    //forgot Password Service
    public ForgotPasswordResponse fogotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByPhoneNumber(forgotPasswordRequest.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        SendOtpRequest sendOtpRequest = new SendOtpRequest();
        sendOtpRequest.setPhoneNumber(forgotPasswordRequest.getPhoneNumber());
        sendOtpRequest.setPurpose(OtpPurpose.FORGOT_PASSWORD);

        otpService.sendOtp(sendOtpRequest);

        return ForgotPasswordResponse.builder()
                .phoneNumber(user.getPhoneNumber())
                .message("Otp sent successfully")
                .build();
    }

    //reset Password Service
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByPhoneNumber(resetPasswordRequest.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        OtpRequest verifiedOtp = otpRequestRepository.findTopByPhoneNumberAndOtpPurposeAndIsVerifiedTrueOrderByCreatedAtDesc(
                resetPasswordRequest.getPhoneNumber(),
                OtpPurpose.FORGOT_PASSWORD
        )
                .orElseThrow(() -> new InvalidCredentialsException("Otp Verification required before resetting your password"));
        user.setPasswordHash(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(user);

        verifiedOtp.setIsVerified(false);
        otpRequestRepository.save(verifiedOtp);

        return ResetPasswordResponse.builder()
                .phoneNumber(user.getPhoneNumber())
                .message("password reset successfully")
                .build();
    }
}