package com.fimsolution.group.app.model.business.f2f;


import com.fimsolution.group.app.constant.business.f2f.schedule.DELINQUENCY;
import com.fimsolution.group.app.constant.business.f2f.schedule.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NamedEntityGraph
@Setter
@Getter
@ToString
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule {


    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;


    // Equal to data of the business
    private LocalDateTime createAt;

    private Double amount;

    private Double adjustment;

    private Double paid;

    private Double due;

    private Double interest;

    private Double principle;

    private Double fimFee;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private SOURCE source;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    @Enumerated(EnumType.STRING)
    private DELINQUENCY delinquency;

    private String memo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "obligation_id")
    @ToString.Exclude
    private Obligation obligation;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Payment> payment;

    @PrePersist
    public void generatedId() {
        this.id = GenerationUtil.generateUniqueId();
    }
}
