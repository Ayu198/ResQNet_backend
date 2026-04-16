package com.resqnet.backend.entity;

import com.resqnet.backend.ResQNet_Enum.ApprovalStatus;
import com.resqnet.backend.ResQNet_Enum.VolunteerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "volunteer_profiles")
@Getter
@Setter
public class VolunteerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "volunteer_type", nullable = false)
    private VolunteerType volunteerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false)
    private ApprovalStatus approvalStatus;

    @Column(name = "background_category")
    private String backgroundCategory;

    @Column(name = "experience_level")
    private String experienceLevel;

    @Column(name = "organization")
    private String organization;

    @Column(name = "experience_summary")
    private String experienceSummary;

    @Column(name = "safety_agreed")
    private Boolean safetyAgreed;
}
