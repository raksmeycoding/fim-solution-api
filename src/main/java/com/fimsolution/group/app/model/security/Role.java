package com.fimsolution.group.app.model.security;

import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String id;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<UserRole> userRole;

    // Generate the ID when creating a new user
    @PrePersist
    public void generateId() {
        this.id = GenerationUtil.generateUniqueId();
    }
}
