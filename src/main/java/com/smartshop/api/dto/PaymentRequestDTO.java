package com.smartshop.api.dto;

import com.smartshop.api.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentRequestDTO {
    @Positive
    private double montant;
    @NotNull
    private PaymentType type;

    private String reference;
    private String banque;
    private LocalDate datePaiement;
    private LocalDate dateEncaissement;
}
