package com.fimsolution.group.app.model.business.f2f;


import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email")
})
public class User {

    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String id;

    String givenName;

    String middleName;

    String familyName;

    String nickName;

    @Column(unique = true, nullable = false)
    String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_credential_id")
    @ToString.Exclude
    private UserCredential userCredential;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<LoanUser> loanUser;


    @PrePersist
    public void generatedId() {
        this.id = GenerationUtil.generateUniqueId();
    }
}
