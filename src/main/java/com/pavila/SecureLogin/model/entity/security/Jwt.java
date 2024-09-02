package com.pavila.SecureLogin.model.entity.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "jwt_tokens")
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3000)
    private String token;
    private Date expiration;
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
