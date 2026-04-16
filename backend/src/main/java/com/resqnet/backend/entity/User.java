package com.resqnet.backend.entity;

import com.resqnet.backend.ResQNet_Enum.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", unique = true, length = 120)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 15 , unique = true)
    private String phoneNumber;

    @Column(name = "password_hash" , nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "userType" , nullable = false , length = 20)
    private UserType userType;

    @Column(name = "is_phone_verified", nullable = false)
    private Boolean isPhoneVerified = false;

    @Column(name = "is_active" , nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at" , insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at" , insertable = false , updatable = false)
    private LocalDateTime updatedAt;
}