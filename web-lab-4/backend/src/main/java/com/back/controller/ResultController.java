package com.back.controller;

import com.back.DTO.PointRequestDTO;
import com.back.DTO.ResultResponseDTO;
import com.back.DTO.UserRequestDTO;
import com.back.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping
    public ResponseEntity<List<ResultResponseDTO>> getUserResults(@RequestBody UserRequestDTO userRequest) {
        String username = userRequest.getUsername();
        return ResponseEntity.ok(resultService.getUserResults(username));
    }

    @PostMapping("/check")
    public ResponseEntity<ResultResponseDTO> check(@RequestBody PointRequestDTO pointRequest) {
        return ResponseEntity.ok(resultService.addResult(pointRequest));
    }

    @PostMapping("/clear")
    public ResponseEntity<Void> deleteAllResultByUsername(@RequestBody UserRequestDTO userRequest) {
        String username = userRequest.getUsername();
        resultService.clearUserResults(username);
        return ResponseEntity.ok().build();
    }

}
