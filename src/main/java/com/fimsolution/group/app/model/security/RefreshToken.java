package com.fimsolution.group.app.model.security;


import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String id;


    @Column(nullable = false, unique = true)
    private String token;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_credential_id", unique = true, nullable = false)
    @ToString.Exclude
    private UserCredential userCredential;


    @Version
    @Column(name = "version")
    private Long version;  // Optimistic lock version

    // Generate the ID when creating a new user
    @PrePersist
    public void generateId() {
        this.id = GenerationUtil.generateUniqueId();
    }
}
