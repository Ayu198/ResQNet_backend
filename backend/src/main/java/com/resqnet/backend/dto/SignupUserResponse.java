package com.resqnet.backend.dto;

import com.resqnet.backend.ResQNet_Enum.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignupUserResponse {
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private UserType usertype;
    private String message;
}
