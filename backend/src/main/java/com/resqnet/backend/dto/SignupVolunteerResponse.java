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
public class SignupVolunteerResponse {
    private Long userId;
    private Long volunteerProfileId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private UserType userType;
    private VolunteerType volunteerType;
    private ApprovalStatus approvalStatus;
    private String message;
}
