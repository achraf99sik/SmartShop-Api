package com.smartshop.api.service;

import com.smartshop.api.dto.OrderItemRequestDTO;
import com.smartshop.api.dto.OrderRequestDTO;
import com.smartshop.api.enums.CustomerTier;
import com.smartshop.api.enums.OrderStatus;
import com.smartshop.api.model.*;
import com.smartshop.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

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
            Product product = productService.checkStock(itemReq.getProductId(), itemReq.getQuantity());
            subtotal += product.getPrixHT() * itemReq.getQuantity();
        }

        CustomerTier tier = client.getTier();
        double rate = fidelityService.getDiscountRate(tier);
        double afterDiscount = subtotal - (subtotal * rate);

        Order orderAfterDiscount = getOrder(request, afterDiscount, client);

        Order saved = orderRepository.save(orderAfterDiscount);

        for (OrderItemRequestDTO itemReq : request.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(saved);
            item.getProduct().setId(itemReq.getProductId());
            item.setQuantity(itemReq.getQuantity());
            orderItemRepository.save(item);
        }

        return saved;
    }

    private static Order getOrder(OrderRequestDTO request, double afterDiscount, Client client) {
        double promoDiscount = 0;
        if (request.getPromoCode() != null && request.getPromoCode().matches("^PROMO-\\d{4}$")) {
            promoDiscount = afterDiscount * 0.05;
        }

        double afterPromo = afterDiscount - promoDiscount;

        double tva = afterPromo * Double.parseDouble(System.getProperty("TVA"));
        double totalTTC = afterPromo + tva;

        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalTTC(totalTTC);
        order.setMontantRestant(totalTTC);
        return order;
    }

    public Order confirmOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getMontantRestant() > 0)
            throw new RuntimeException("Order not fully paid");

        List<OrderItem> items = orderItemRepository.findAllByOrderId(orderId);

        for (OrderItem item : items) {
            productService.decrementStock(item.getProduct().getId(), item.getQuantity());
        }

        Client client = order.getClient();
        client.setTotalOrders(client.getTotalOrders() + 1);
        client.setTotalSpent(client.getTotalSpent() + order.getTotalTTC());

        CustomerTier newTier = fidelityService.recalcTier(client.getTotalOrders(), client.getTotalSpent());
        client.setTier(newTier);
        clientRepository.save(client);

        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }
}
