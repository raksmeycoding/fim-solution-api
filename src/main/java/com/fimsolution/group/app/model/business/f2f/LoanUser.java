package com.fimsolution.group.app.model.business.f2f;


import com.fimsolution.group.app.constant.business.f2f.loanuser.*;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "loan_user")
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class LoanUser {

    @Id
    @Column(nullable = false, length = 36, unique = true)
    private String id;

//
//    @Column(nullable = true, unique = true, length = 36)
//    private String loanUserId;


    private String loanName;

    @Enumerated(EnumType.STRING)
    private ROLE role;
    @Enumerated(EnumType.STRING)
    private TYPE type;

    //    @Enumerated(EnumType.STRING)
//    private EMAIL email;
//    @Enumerated(EnumType.STRING)
//    private TEXT text;
//    @Enumerated(EnumType.STRING)
//    private PHONE phone;
//    @Enumerated(EnumType.STRING)
//    private ADDRESS address;
    private String memo;

    @Enumerated(EnumType.STRING)
    private PRIORITIZE prioritize;

    /**
     * @Note: Developer flow
     */
    private String email;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_no")
    @ToString.Exclude
    private Loan loan;


    @OneToMany(mappedBy = "loanUser", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Event> events;


    /**
     * @Note: Temporarily disable wait to confirm with business
     */
//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//    @JoinColumn(name = "loan_no", nullable = false)
//    private List<Loan> loan;
    @PrePersist
    public void generatedId() {
        this.id = GenerationUtil.generateUniqueId();
    }


}
