package com.pavila.TaskProyect.service;

import com.pavila.TaskProyect.model.dto.RegisteredUserRequest;
import com.pavila.TaskProyect.model.dto.UserRegisteredResponse;
import com.pavila.TaskProyect.model.entity.security.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User registerOneUser(RegisteredUserRequest registeredUserRequest);

    Optional<User> findOneByUsername(String username);

    List<UserRegisteredResponse> allUser();
}
