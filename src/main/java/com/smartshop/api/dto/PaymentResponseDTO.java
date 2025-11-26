package com.smartshop.api.dto;

import com.smartshop.api.enums.PaymentStatus;
import com.smartshop.api.enums.PaymentType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentResponseDTO {
    private UUID id;
    private double montant;
    private PaymentType type;
    private PaymentStatus status;
    private String reference;
    private String banque;
    private LocalDate datePaiement;
    private LocalDate dateEncaissement;
    private int numeroPaiement;
}
