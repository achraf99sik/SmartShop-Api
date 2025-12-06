package com.smartshop.api.controller;

import com.smartshop.api.dto.PaymentRequestDTO;
import com.smartshop.api.dto.PaymentResponseDTO;
import com.smartshop.api.mapper.PaymentMapper;
import com.smartshop.api.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders/{orderId}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    public PaymentResponseDTO createPayment(
            @PathVariable UUID orderId,
            @RequestBody @Valid PaymentRequestDTO dto
    ) {
        return paymentMapper.toDTO(paymentService.registerPayment(orderId, dto.getMontant()));
    }

    @GetMapping
    public List<PaymentResponseDTO> getPayments(@PathVariable UUID orderId) {
        return paymentMapper.toDTOList(paymentService.getPaymentsByOrder(orderId));
    }
}
