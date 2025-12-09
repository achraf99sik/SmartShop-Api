package com.smartshop.api.repository;

import com.smartshop.api.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findPaymentsByOrderId(UUID orderId);
    @Query("SELECT MAX(p.numeroPaiement) FROM Payment p")
    Integer findMaxNumeroPaiement();
}
