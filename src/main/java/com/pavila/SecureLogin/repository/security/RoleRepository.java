package com.pavila.SecureLogin.repository.security;

import com.pavila.SecureLogin.model.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String role);
}
