package com.fimsolution.group.app.model.business.f2f;


import com.fimsolution.group.app.constant.business.f2f.obligation.CHANGE;
import com.fimsolution.group.app.constant.business.f2f.obligation.SOURCE;
import com.fimsolution.group.app.utils.GenerationUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "obligation")
public class Obligation {
    @Id
    private String id;



    /*In business filed call @param data*/
    private LocalDateTime createdAt;

    private Double free;

    @Enumerated(EnumType.STRING)
    private SOURCE source;

    private Boolean use;

    @Enumerated(EnumType.STRING)
    private CHANGE change;





    @JoinColumn(name = "event_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Event event;


    @OneToMany(mappedBy = "obligation",fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<Schedule> schedules;



    @PrePersist
    public void generatedId() {
        this.id = GenerationUtil.generateUniqueId();
    }

}
