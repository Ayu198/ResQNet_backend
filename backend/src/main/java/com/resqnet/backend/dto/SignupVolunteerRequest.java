package com.resqnet.backend.dto;

import com.resqnet.backend.ResQNet_Enum.VolunteerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupVolunteerRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @Email(message = "Invalid Email Formate")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "VolunteerType is required")
    private VolunteerType volunteerType;

    @NotBlank(message = "Background-info required")
    private String backgroundCategory;

    @NotBlank(message = "Experience level is required")
    private String experienceLevel;

    @NotBlank(message = "Organisation is required")
    private String organisation;

    @NotBlank(message = "Experience Summary is required")
    private String experienceSummary;

    @NotNull(message = "Safety Agreemnet is required")
    private Boolean SafetyAgreed;
}
