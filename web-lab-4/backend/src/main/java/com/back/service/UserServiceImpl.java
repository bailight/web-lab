package com.back.service;

import com.back.DTO.UserRequestDTO;
import com.back.DTO.UserResponseDTO;
import com.back.entity.User;
import com.back.exception.UserExistsException;
import com.back.exception.UserNotFoundException;
import com.back.exception.WrongPasswordException;
import com.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.back.util.PasswordEncoder.checkPassword;
import static com.back.util.PasswordEncoder.hashPassword;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO login(UserRequestDTO loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User " + username + " not found");
        }else if(!checkPassword(password, user.get().getPassword())){
            throw new WrongPasswordException("Wrong password");
        }else{
            return new UserResponseDTO(username);
        }
    }

    @Override
    public UserResponseDTO register(UserRequestDTO registrationRequest) {
        String username = registrationRequest.getUsername();
        String password = registrationRequest.getPassword();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            throw new UserExistsException("User " + username + " exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashPassword(password));
        userRepository.save(newUser);

        return new UserResponseDTO(username);

    }
}