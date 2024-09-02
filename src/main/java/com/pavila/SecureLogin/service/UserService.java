package com.pavila.SecureLogin.service;

import com.pavila.SecureLogin.model.entity.security.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String username);

}
