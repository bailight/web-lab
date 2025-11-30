package com.back.controller;

import com.back.DTO.UserRequestDTO;
import com.back.DTO.UserResponseDTO;
import com.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/back/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO userResponse = userService.login(userRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "200");
        response.put("message", "Login Successful ");
        response.put("username", userResponse.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO registrationDTO) {
        userService.register(registrationDTO);
        Map<String, String> response = new HashMap<>();
        response.put("status", "201");
        response.put("message", "Registration successful");
        response.put("username", registrationDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "200");
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }

}
