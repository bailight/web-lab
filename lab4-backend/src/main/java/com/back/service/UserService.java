package com.back.service;

import com.back.repository.UserRepository;
import com.back.util.JwtUtil;
import com.back.util.PasswordEncoder;

public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;  // 密码加密器
    private JwtUtil jwtUtil;



}
