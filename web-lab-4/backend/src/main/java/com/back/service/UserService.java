package com.back.service;


import com.back.DTO.UserRequestDTO;
import com.back.DTO.UserResponseDTO;

public interface UserService {

    UserResponseDTO login(UserRequestDTO loginRequest);

    void register(UserRequestDTO registrationRequest);

}
