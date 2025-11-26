package com.smartshop.api.model;

import com.smartshop.api.enums.PaymentStatus;
import com.smartshop.api.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(optional = false)
    private Order order;

    private int numeroPaiement;

    @Column(nullable = false)
    private double montant;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private LocalDate datePaiement;

    private LocalDate dateEncaissement;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String reference;

    private String banque;
}
