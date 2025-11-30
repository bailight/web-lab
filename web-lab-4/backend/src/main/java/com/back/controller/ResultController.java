package com.back.controller;

import com.back.DTO.PointRequestDTO;
import com.back.DTO.ResultResponseDTO;
import com.back.DTO.UserRequestDTO;
import com.back.service.ResultService;
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

    @PostMapping
    public ResponseEntity<?> getUserResults(@RequestBody UserRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        String username = request.getUsername();
        if (username == null) {
            response.put("message", "User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        List<ResultResponseDTO> results = resultService.getUserResults(username);
        response.put("message", "User" + username);
        response.put("results", results == null ? new ArrayList<>() : results);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody PointRequestDTO pointRequest) {

        return ResponseEntity.ok(resultService.addResult(pointRequest));
    }

    @PostMapping("/clear")
    public ResponseEntity<?> deleteAllResultByUsername(@RequestBody UserRequestDTO userRequest) {
        String username = userRequest.getUsername();
        resultService.clearUserResults(username);
        return ResponseEntity.ok().build();
    }
}
