package com.pavila.SecureLogin.service.impl;

import com.pavila.SecureLogin.model.entity.security.User;
import com.pavila.SecureLogin.repository.security.UserRepository;
import com.pavila.SecureLogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
