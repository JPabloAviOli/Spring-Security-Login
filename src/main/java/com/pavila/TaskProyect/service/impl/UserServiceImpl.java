package com.pavila.TaskProyect.service.impl;

import com.pavila.TaskProyect.exception.InvalidPasswordException;
import com.pavila.TaskProyect.model.dto.RegisteredUserRequest;
import com.pavila.TaskProyect.model.dto.UserRegisteredResponse;
import com.pavila.TaskProyect.model.entity.security.User;
import com.pavila.TaskProyect.repository.security.IUserRepository;
import com.pavila.TaskProyect.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User registerOneUser(RegisteredUserRequest registeredUserRequest) {

        validatePassword(registeredUserRequest);

        User user = User.builder()
                .username(registeredUserRequest.getUsername())
                .name(registeredUserRequest.getName())
                .password(passwordEncoder.encode(registeredUserRequest.getPassword()))
                .role(registeredUserRequest.getRole())
                .build();

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserRegisteredResponse> allUser() {
      return userRepository.findByRoleUser();
    }

    private void validatePassword(RegisteredUserRequest registeredUser) {

        if(!StringUtils.hasText(registeredUser.getPassword()) || !StringUtils.hasText(registeredUser.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }

        if(!registeredUser.getPassword().equals(registeredUser.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }

    }
}
