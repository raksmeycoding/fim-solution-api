package com.fimsolution.group.app.model.security;


import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_role")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserRole {

    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_credential_id", nullable = false)
    private UserCredential userCredential;


    @PrePersist
    public void generateId() {
        this.id = GenerationUtil.generateUniqueId();
    }
}
