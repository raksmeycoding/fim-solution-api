package com.fimsolution.group.app.model.business.test;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrower")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Borrower {

    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdAt;
}
