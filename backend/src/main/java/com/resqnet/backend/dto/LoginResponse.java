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
public class LoginResponse {
    private String token;
    private Long userId;
    private String fullName;
    private UserType userType;
    private VolunteerType volunteerType;
    private ApprovalStatus approvalStatus;
    private String message;
}
