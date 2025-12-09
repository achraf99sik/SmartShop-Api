package com.smartshop.api.service;

import com.smartshop.api.dto.OrderItemRequestDTO;
import com.smartshop.api.dto.OrderRequestDTO;
import com.smartshop.api.enums.CustomerTier;
import com.smartshop.api.enums.OrderStatus;
import com.smartshop.api.exception.BusinessException;
import com.smartshop.api.exception.ResourceNotFoundException;
import com.smartshop.api.model.*;
import com.smartshop.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${TVA}")
    private double tvaRate;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientService clientService;
    private final ProductService productService;
    private final FidelityService fidelityService;
    private final ClientRepository clientRepository;

    public Order createOrder(OrderRequestDTO request) {

        Client client = clientService.getClient(request.getClientId());

        double subtotal = 0;
        for (OrderItemRequestDTO itemReq : request.getItems()) {

            Product product = productService.checkStock(
                    itemReq.getProductId(),
                    itemReq.getQuantity()
            );

            subtotal += product.getPrixHT() * itemReq.getQuantity();
        }

        CustomerTier tier = client.getTier();
        double rate = fidelityService.getDiscountRate(tier);

        double afterDiscount = subtotal - (subtotal * rate);

        Order orderAfterDiscount = getOrder(request, afterDiscount, client, tvaRate);

        Order saved = orderRepository.save(orderAfterDiscount);

        for (OrderItemRequestDTO itemReq : request.getItems()) {
            Product product = productService.getProduct(itemReq.getProductId());

            OrderItem item = new OrderItem();
            item.setOrder(saved);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());

            double unitPrice = product.getPrixHT();
            double totalLine = unitPrice * itemReq.getQuantity();

            item.setUnitPriceHT(unitPrice);
            item.setTotalLineHT(totalLine);

            orderItemRepository.save(item);
        }

        return saved;
    }

    private static Order getOrder(OrderRequestDTO request, double afterDiscount, Client client, double tvaRate) {

        BigDecimal subtotal = BigDecimal.valueOf(afterDiscount).setScale(2, RoundingMode.HALF_UP);
        BigDecimal promoDiscount = BigDecimal.ZERO;

        if (request.getPromoCode() != null) {
            if (!request.getPromoCode().matches("^PROMO-\\d{4}$")) {
                throw new BusinessException("Invalid promo code format");
            }
            promoDiscount = subtotal.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal afterPromo = subtotal.subtract(promoDiscount).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tva = afterPromo.multiply(BigDecimal.valueOf(tvaRate)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalTTC = afterPromo.add(tva).setScale(2, RoundingMode.HALF_UP);

        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.PENDING);
        order.setSubtotalHT(subtotal.doubleValue());
        order.setRemise(promoDiscount.doubleValue());
        order.setMontantHTAfterRemise(afterPromo.doubleValue());
        order.setTva(tva.doubleValue());
        order.setTotalTTC(totalTTC.doubleValue());
        order.setMontantRestant(totalTTC.doubleValue());
        order.setPromoCode(request.getPromoCode());

        return order;
    }

    public Order confirmOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getMontantRestant() > 0) {
            throw new BusinessException("Order not fully paid");
        }
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new BusinessException("This order is already confirmed");
        }

        List<OrderItem> items = orderItemRepository.findAllByOrderId(orderId);

        for (OrderItem item : items) {
            productService.decrementStock(item.getProduct().getId(), item.getQuantity());
        }

        Client client = order.getClient();
        client.setTotalOrders(client.getTotalOrders() + 1);
        client.setTotalSpent(client.getTotalSpent() + order.getTotalTTC());

        CustomerTier newTier = fidelityService.recalcTier(
                client.getTotalOrders(),
                client.getTotalSpent()
        );

        client.setTier(newTier);

        if (client.getFirstOrderDate() == null) {
            client.setFirstOrderDate(LocalDateTime.now());
        }
        client.setLastOrderDate(LocalDateTime.now());

        clientRepository.save(client);

        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }
    public Order cancelOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with this id: " + orderId));

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new BusinessException("This order is already confirmed");
        }

        order.setStatus(OrderStatus.CANCELED);
        return orderRepository.save(order);
    }

    public Order getOrder(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByClient(UUID clientId) {
        return orderRepository.findByClientId(clientId);
    }
}

