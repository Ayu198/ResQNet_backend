package com.resqnet.backend.entity;

import com.resqnet.backend.ResQNet_Enum.ApprovalStatus;
import com.resqnet.backend.ResQNet_Enum.VolunteerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "volunteer_profiles")
@Getter
@Setter
public class VolunteerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Column(name = "user_id",nullable = false,unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "volunteer_type" , nullable = false,length = 30)
    private VolunteerType volunteerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status" , nullable = false,length = 20)
    private ApprovalStatus approvalStatus =  ApprovalStatus.PENDING;

    @Column(name = "background_category" , length = 100)
    private String backgroundCategory;

    @Column(name = "experience_level" , length = 50)
    private String experienceLevel;

    @Column(name = "organization" , length = 150)
    private String organization;

    @Column(name = "experience_summary" , columnDefinition = "TEXt")
    private String experienceSummary;

    @Column(name = "safety_agreed")
    private Boolean safetyAgreed = false;

    @Column(name = "created_at" , insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at" , insertable = false , updatable = false)
    private LocalDateTime updatedAt;
}
