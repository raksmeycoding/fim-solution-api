package com.fimsolution.group.app.model.security;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_credential_id", referencedColumnName = "id")
    private UserCredentials userCredentials;


    @Column(nullable = false, unique = true)
    private String token;


    @Column(nullable = false)
    private Instant expiryDate;


}
