package com.smartshop.api.dto;

import com.smartshop.api.enums.CustomerTier;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ClientResponseDTO {
    private UUID id;
    private String nom;
    private String email;
    private CustomerTier tier;
    private int totalOrders;
    private double totalSpent;
    private LocalDateTime firstOrderDate;
    private LocalDateTime lastOrderDate;
}
