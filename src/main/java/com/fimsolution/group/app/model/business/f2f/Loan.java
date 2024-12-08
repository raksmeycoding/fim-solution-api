package com.fimsolution.group.app.model.business.f2f;


import com.fimsolution.group.app.constant.LOAN_TYPE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String id;


//    @Column(name = "loan_id", unique = true)
//    private String loanId;

    private String nickName;

    @Column(name = "interest", nullable = false)
    private Double interest;


    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LOAN_TYPE loanType;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "fee")
    private Double fee;


    private String note;


    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime endDate;


    @Column(columnDefinition = "boolean default false")
    private boolean isLinkToLoanUser;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LoanUser> loanUsers;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Schedule> schedules;


    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Payment> payments;

    /**
     * @Note: Temporarily disable wait to confirm with business
     */
//    @ManyToOne(fetch = FetchType.EAGER)
//    private LoanUsers loanUsers;
    @PrePersist
    public void generatedId() {
        this.id = GenerationUtil.generateUniqueId();
    }
}
