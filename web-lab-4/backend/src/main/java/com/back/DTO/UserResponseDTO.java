package com.back.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String username;

    public UserResponseDTO(String username) {
        this.username = username;
    }
}