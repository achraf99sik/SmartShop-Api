package com.smartshop.api.service;

import com.smartshop.api.dto.PaymentRequestDTO;
import com.smartshop.api.enums.OrderStatus;
import com.smartshop.api.enums.PaymentStatus;
import com.smartshop.api.exception.BusinessException;
import com.smartshop.api.exception.ResourceNotFoundException;
import com.smartshop.api.model.Order;
import com.smartshop.api.model.Payment;
import com.smartshop.api.repository.OrderRepository;
import com.smartshop.api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment registerPayment(UUID orderId, PaymentRequestDTO request) {
        double amount = request.getMontant();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (amount <= 0) {
            throw new BusinessException("Payment amount must be positive");
        }

        if (amount > order.getMontantRestant()) {
            throw new BusinessException("Payment exceeds remaining balance");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMontant(amount);
        payment.setStatus(PaymentStatus.EN_ATTENTE);
        payment.setBanque(request.getBanque());
        payment.setDatePaiement(request.getDatePaiement());
        payment.setNumeroPaiement(generatePaymentNumber());
        payment.setType(request.getType());
        paymentRepository.save(payment);

        order.setMontantRestant(order.getMontantRestant() - amount);

        if (order.getMontantRestant() == 0) {
            order.setStatus(OrderStatus.CONFIRMED);
        }

        orderRepository.save(order);

        return payment;
    }
    public int generatePaymentNumber() {
        Integer lastNumber = paymentRepository.findMaxNumeroPaiement();
        if (lastNumber == null) {
            return 1;
        }
        return lastNumber + 1;
    }
    public List<Payment> getPaymentsByOrder(UUID orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found");
        }
        return paymentRepository.findPaymentsByOrderId(orderId);
    }
}

