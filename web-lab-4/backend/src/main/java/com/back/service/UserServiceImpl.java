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

import static com.back.util.SelfPasswordEncoder.*;


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
        }else if(!checkPassword(password, user.get().getPassword(), user.get().getSalt())){
            throw new WrongPasswordException("Wrong password");
        }else{
            return new UserResponseDTO(username);
        }
    }

    @Override
    public void register(UserRequestDTO registrationRequest) {
        String username = registrationRequest.getUsername();
        String password = registrationRequest.getPassword();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            throw new UserExistsException("User " + username + " exists");
        }

        String salt = generateSalt();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashPassword(password, salt));
        newUser.setSalt(salt);
        userRepository.save(newUser);

    }

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User " + username + " not found");
        }
        return user.get();
    }
}