package com.smartshop.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductResponseDTO {
    private UUID id;
    private String nom;
    private double prixHT;
    private int stock;
    private boolean deleted;
}
