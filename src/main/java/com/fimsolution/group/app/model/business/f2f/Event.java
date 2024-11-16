package com.fimsolution.group.app.model.business.f2f;


import com.fimsolution.group.app.constant.business.f2f.event.MEDIUM;
import com.fimsolution.group.app.constant.business.f2f.event.TYPE;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @Column(unique = true, length = 36)
    private String id;


    @Column(name = "createdEventAt")
    private LocalDateTime createdEventDateTime;

    @Enumerated(EnumType.STRING)
    private TYPE type;

    @Enumerated(EnumType.STRING)
    private MEDIUM medium;


    private String memo;


    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private LoanUser loanUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_no")
    @ToString.Exclude
    private Loan loan;


    @PrePersist
    public void generatedId() {
        this.id = GenerationUtil.generateUniqueId();
    }

}
