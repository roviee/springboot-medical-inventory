package com.example.medInventory.auth.controller;

import com.example.medInventory.auth.dtos.LoginUserDto;
import com.example.medInventory.auth.dtos.RegisterUserDto;
import com.example.medInventory.auth.dtos.UserDto;
import com.example.medInventory.auth.model.User;
import com.example.medInventory.auth.response.LoginResponse;
import com.example.medInventory.auth.service.AuthenticationService;
import com.example.medInventory.auth.service.JwtService;
import com.example.medInventory.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


    @GetMapping
    public ResponseEntity<ApiResponse> getAllUser() {
            List<User> users = authenticationService.getAllUser();
            List<UserDto> userDtos = users.stream().map(user -> new UserDto(
                    user.getId(),
                    user.getFullname(),
                    user.getEmail()
            )).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse("Users", userDtos));
    }

    @GetMapping("/api/me")
    public ResponseEntity<ApiResponse> UserByEmail(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("not authenticated", null));
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", userDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse("User", userData));

    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signUp(registerUserDto);
            return ResponseEntity.ok(new ApiResponse("User registered successfully", registeredUser));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse();
                        loginResponse.setToken(jwtToken);
                        loginResponse.setExpiresIn(jwtService.getExpirationTime());
            return ResponseEntity.ok(new ApiResponse("User login successfully", loginResponse));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
