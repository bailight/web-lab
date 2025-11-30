package com.back.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResultResponseDTO {
    private Long id;
    private Double x;
    private Double y;
    private Double r;
    private boolean check;
    private LocalDateTime clickTime;
}