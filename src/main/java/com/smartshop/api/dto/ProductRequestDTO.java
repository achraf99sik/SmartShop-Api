package com.smartshop.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductRequestDTO {
    @NotBlank
    private String nom;
    @Positive
    private double prixHT;
    @PositiveOrZero
    private int stock;
}
