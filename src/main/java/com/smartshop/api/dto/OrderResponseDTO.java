package com.smartshop.api.dto;

import com.smartshop.api.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponseDTO {
    private UUID id;
    private UUID clientId;
    private List<OrderItemResponseDTO> items;

    private double subtotalHT;
    private double remise;
    private double montantHTAfterRemise;
    private double tva;
    private double totalTTC;
    private double montantRestant;

    private String promoCode;
    private OrderStatus status;

    private LocalDateTime createdAt;
}
