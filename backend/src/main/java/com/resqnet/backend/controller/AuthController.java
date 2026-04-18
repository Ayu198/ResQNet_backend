package com.resqnet.backend.controller;

import com.resqnet.backend.dto.*;
import com.resqnet.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup-user")
    public ResponseEntity<SignupUserResponse> signupUser(@Valid @RequestBody SignupUserRequest signupUserRequest){
        SignupUserResponse response = authService.signupUser(signupUserRequest);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/signup-volunteer")
    public ResponseEntity<SignupVolunteerResponse> signupVolunteer(@Valid @RequestBody SignupVolunteerRequest signupVolunteerRequest){
        SignupVolunteerResponse response = authService.signupVolunteer(signupVolunteerRequest);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/me")
    public ResponseEntity<AuthMeResponse> authMe(@Valid @RequestBody AuthMeRequest authMeRequest){
        AuthMeResponse response = authService.authMe(authMeRequest);
        return ResponseEntity.ok(response);
    }
}
