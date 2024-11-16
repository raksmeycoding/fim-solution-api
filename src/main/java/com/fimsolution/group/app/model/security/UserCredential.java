package com.fimsolution.group.app.model.security;

import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "user_credentials", indexes = {
        @Index(name = "idx_username", columnList = "username", unique = true)
})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential {

    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String id;


    private String firstname;

    private String lastname;

    private String phone;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean enabled;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonExpired;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean credentialsNonExpired;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean accountNonLocked;

    @OneToMany(mappedBy = "userCredential", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<UserRole> userRole;


    @OneToOne(mappedBy = "userCredential", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;


    @OneToOne(mappedBy = "userCredential", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @PrePersist
    public void generateId() {
        this.id = GenerationUtil.generateUniqueId();
    }


}
