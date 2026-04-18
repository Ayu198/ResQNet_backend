package com.resqnet.backend.entity;

import com.resqnet.backend.ResQNet_Enum.OtpPurpose;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_requests")
@Getter
@Setter
public class OtpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number" , nullable = false , length = 15)
    private String phoneNumber;

    @Column(name = "otp_code" , nullable = false , length = 10)
    private String otpCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose" , nullable = false)
    private OtpPurpose otpPurpose;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
