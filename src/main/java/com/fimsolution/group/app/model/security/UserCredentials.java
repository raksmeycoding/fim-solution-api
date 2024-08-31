package com.fimsolution.group.app.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user_credentials", indexes = {
        @Index(name = "idx_username", columnList = "username", unique = true)
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userId;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles", // The join table name
            joinColumns = @JoinColumn(name = "user_credentials_id"), // Foreign key for UserCredentials
            inverseJoinColumns = @JoinColumn(name = "role_id") // Foreign key for Role
    )
    private Set<Role> roles = new HashSet<>();

}
