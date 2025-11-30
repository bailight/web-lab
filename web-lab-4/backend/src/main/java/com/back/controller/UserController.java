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

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.ok(userService.login(userRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO registrationDTO) {
        userService.register(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody UserRequestDTO userRequest) {

        return ResponseEntity.ok("Logout successful");
    }

}
