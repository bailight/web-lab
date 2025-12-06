package com.back.controller;

import com.back.DTO.UserRequestDTO;
import com.back.DTO.UserResponseDTO;
import com.back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/back/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequest, HttpServletRequest request) {
        UserResponseDTO userResponse = userService.login(userRequest);

        request.getSession().setAttribute("username", userResponse.getUsername());

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
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();

        Map<String, String> response = new HashMap<>();
        response.put("status", "200");
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-login")
    public ResponseEntity<?> checkLoginStatus(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username != null) {
            return ResponseEntity.ok(Map.of("isAuthenticated", true, "username", username));
        } else {
            return ResponseEntity.ok(Map.of("isAuthenticated", false));
        }
    }
}
