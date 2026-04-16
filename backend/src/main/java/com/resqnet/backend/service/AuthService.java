package com.resqnet.backend.service;

import com.resqnet.backend.ResQNet_Enum.UserType;
import com.resqnet.backend.dto.LoginRequest;
import com.resqnet.backend.dto.LoginResponse;
import com.resqnet.backend.entity.User;
import com.resqnet.backend.entity.VolunteerProfile;
import com.resqnet.backend.exception.InvalidCredentialsException;
import com.resqnet.backend.exception.ResourceNotFoundException;
import com.resqnet.backend.repository.UserRepository;
import com.resqnet.backend.repository.VolunteerProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;

    public AuthService(UserRepository userRepository,
                       VolunteerProfileRepository volunteerProfileRepository) {
        this.userRepository = userRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getPasswordHash().equals(loginRequest.getPassword())) {
            throw new InvalidCredentialsException("Invalid phone number or password");
        }

        LoginResponse.LoginResponseBuilder loginResponseBuilder = LoginResponse.builder()
                .token("dummy-token-for-now")
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
}
