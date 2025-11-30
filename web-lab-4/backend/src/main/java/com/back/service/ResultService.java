package com.back.service;

import com.back.DTO.PointRequestDTO;
import com.back.DTO.ResultResponseDTO;

import java.util.List;

public interface ResultService {
    List<ResultResponseDTO> getUserResults(String username);

    ResultResponseDTO addResult(PointRequestDTO request);

    void clearUserResults(String username);
}
