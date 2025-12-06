package com.back.controller;

import com.back.DTO.PointRequestDTO;
import com.back.DTO.ResultResponseDTO;
import com.back.service.ResultService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public ResponseEntity<?> getUserResults(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Map<String, Object> response = new HashMap<>();
        List<ResultResponseDTO> results = resultService.getUserResults(username);
        response.put("message", "User" + username);
        response.put("results", results == null ? new ArrayList<>() : results);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody PointRequestDTO pointRequest, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        pointRequest.setUsername(username);
        return ResponseEntity.ok(resultService.addResult(pointRequest));
    }

    @PostMapping("/clear")
    public ResponseEntity<?> deleteAllResultByUsername(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        resultService.clearUserResults(username);
        return ResponseEntity.ok().build();
    }
}
