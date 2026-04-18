package com.resqnet.backend.dto;

import com.resqnet.backend.ResQNet_Enum.ApprovalStatus;
import com.resqnet.backend.ResQNet_Enum.UserType;
import com.resqnet.backend.ResQNet_Enum.VolunteerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthMeResponse {
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private UserType userType;
    private VolunteerType volunteerType;
    private ApprovalStatus approvalStatus;
    private Boolean isPhoneVerified;
    private Boolean isActive;
    private String message;
}
