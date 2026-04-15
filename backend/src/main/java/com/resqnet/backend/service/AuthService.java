package com.resqnet.backend.service;

import com.resqnet.backend.ResQNet_Enum.UserType;
import com.resqnet.backend.dto.LoginRequest;
import com.resqnet.backend.dto.LoginResponse;
import com.resqnet.backend.entity.User;
import com.resqnet.backend.entity.VolunteerProfile;
import com.resqnet.backend.repository.UserRepository;
import com.resqnet.backend.repository.VolunteerProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final VolunteerProfileRepository volunteerProfileRepository;

    public AuthService(UserRepository userRepository, VolunteerProfileRepository volunteerProfileRepository) {
        this.userRepository = userRepository;
        this.volunteerProfileRepository = volunteerProfileRepository;
    }
    public LoginResponse login(LoginRequest loginRequest){
        User user = userRepository.findByPhoneNumber(loginRequest.getPhoneNumber())
                .orElseThrow(()->new RuntimeException("user not found"));
        if(!user.getPasswordHash().equals(loginRequest.getPassword())){
            throw new RuntimeException("password not match");
        }
        LoginResponse.LoginResponseBuilder loginResponseBuilder = LoginResponse.builder()
                .token("dummy-token-for-now")
                .userId(user.getId())
                .fullName(user.getFullName())
                .userType(user.getUserType())
                .message("Login Successfully");
        if(user.getUserType() == UserType.VOLUNTEER) {
            VolunteerProfile volunteerProfile = volunteerProfileRepository.findByUserId(user.getId())
                    .orElseThrow(()->new RuntimeException("volunteer profile not found"));
            loginResponseBuilder.volunteerType(volunteerProfile.getVolunteerType())
                    .approvalStatus(volunteerProfile.getApprovalStatus());
        }
        return loginResponseBuilder.build();
    }
}
