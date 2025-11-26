package com.smartshop.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequestDTO {
    @NotNull
    private UUID productId;
    @Positive
    private int quantity;
}
