package com.smartshop.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDTO {

    @NotNull
    private UUID clientId;

    @NotEmpty
    private List<OrderItemRequestDTO> items;

    @Pattern(regexp = "PROMO-[A-Z0-9]{4}")
    private String promoCode;
}
