package com.smartshop.api.controller;

import com.smartshop.api.dto.OrderRequestDTO;
import com.smartshop.api.dto.OrderResponseDTO;
import com.smartshop.api.mapper.OrderMapper;
import com.smartshop.api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody @Valid OrderRequestDTO dto) {
        return orderMapper.toDTO(orderService.createOrder(dto));
    }

    @PutMapping("/{id}/confirm")
    public OrderResponseDTO confirmOrder(@PathVariable UUID id) {
        return orderMapper.toDTO(orderService.confirmOrder(id));
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrder(@PathVariable UUID id) {
        return orderMapper.toDTO(orderService.getOrder(id));
    }

    @GetMapping("/client/{clientId}")
    public List<OrderResponseDTO> getClientOrders(@PathVariable UUID clientId) {
        return orderMapper.toDTOList(orderService.getOrdersByClient(clientId));
    }
}
