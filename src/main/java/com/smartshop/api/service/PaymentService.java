package com.smartshop.api.service;

import com.smartshop.api.model.Order;
import com.smartshop.api.model.Payment;
import com.smartshop.api.repository.OrderRepository;
import com.smartshop.api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment registerPayment(UUID orderId, double amount) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (amount > order.getMontantRestant()) {
            throw new RuntimeException("Payment exceeds remaining balance");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMontant(amount);
        paymentRepository.save(payment);

        order.setMontantRestant(order.getMontantRestant() - amount);
        orderRepository.save(order);

        return payment;
    }
}
