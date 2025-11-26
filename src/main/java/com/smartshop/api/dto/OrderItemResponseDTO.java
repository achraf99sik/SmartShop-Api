package com.smartshop.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemResponseDTO {
    private UUID productId;
    private String productName;
    private int quantity;
    private double unitPriceHT;
    private double totalLineHT;
}