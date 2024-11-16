package com.fimsolution.group.app.model.business.f2f;


import com.fimsolution.group.app.constant.business.f2f.payment.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.payment.STATUS;
import com.fimsolution.group.app.constant.business.f2f.payment.TYPE;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;


    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private Double amount;
    private Double adjust;
    private Double principle;
    private Double fimFee;
    private Double receipt;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private TYPE type;
    @Enumerated(EnumType.STRING)
    private STATUS status;
    @Enumerated(EnumType.STRING)
    private SOURCE source;
    private String memo;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = true)
    @ToString.Exclude
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_no")
    @ToString.Exclude
    private Loan loan;

    @PrePersist
    public void generatedId() {
        this.id = GenerationUtil.generateUniqueId();
    }
}
