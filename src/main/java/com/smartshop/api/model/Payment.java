package com.smartshop.api.model;

import com.smartshop.api.enums.PaymentStatus;
import com.smartshop.api.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    private int numeroPaiement;

    @Column(nullable = false)
    private double montant;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private LocalDate datePaiement;

    private LocalDate dateEncaissement = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String reference;

    private String banque;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
