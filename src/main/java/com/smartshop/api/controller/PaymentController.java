package com.smartshop.api.controller;

import com.smartshop.api.dto.PaymentRequestDTO;
import com.smartshop.api.dto.PaymentResponseDTO;
import com.smartshop.api.dto.PaymentStatusRequest;
import com.smartshop.api.enums.PaymentStatus;
import com.smartshop.api.mapper.PaymentMapper;
import com.smartshop.api.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping("/{orderId}")
    public PaymentResponseDTO createPayment(
            @PathVariable UUID orderId,
            @RequestBody @Valid PaymentRequestDTO dto
    ) {
        return paymentMapper.toDTO(paymentService.registerPayment(orderId, dto));
    }

    @GetMapping("/{orderId}")
    public List<PaymentResponseDTO> getPayments(@PathVariable UUID orderId) {
        return paymentMapper.toDTOList(paymentService.getPaymentsByOrder(orderId));
    }

    @PutMapping("/{id}/status")
    public PaymentResponseDTO statusChange(
            @PathVariable UUID id,
            @RequestBody @Valid PaymentStatusRequest requestedStatus
    ) {
        return paymentMapper.toDTO(paymentService.changeStatus(id, requestedStatus.status()));
    }
}

