package com.back.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointRequestDTO {
    private String username;
    private Double x;
    private Double y;
    private Double r;
}
