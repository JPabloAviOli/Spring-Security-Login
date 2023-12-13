package com.pavila.TaskProyect.repository.security;

import com.pavila.TaskProyect.model.dto.UserRegisteredResponse;
import com.pavila.TaskProyect.model.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u where u.role = USER")
    List<UserRegisteredResponse> findByRoleUser();

}
