package com.back.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultResponseDTO {
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private boolean check;
    private LocalDateTime clickTime;
}