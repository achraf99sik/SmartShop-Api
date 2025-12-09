package com.smartshop.api.service;

import com.smartshop.api.enums.CustomerTier;
import org.springframework.stereotype.Service;

@Service
public class FidelityService {

    public double getDiscountRate(CustomerTier tier) {
        if (tier == null) return 0;

        return switch (tier) {
            case SILVER -> 0.05;
            case GOLD -> 0.10;
            case PLATINUM -> 0.15;
            default -> 0.0;
        };
    }

    public CustomerTier recalcTier(int totalOrders, double totalSpent) {

        if (totalOrders >= 20 || totalSpent >= 15000) {
            return CustomerTier.PLATINUM;
        }

        if (totalOrders >= 10 || totalSpent >= 5000) {
            return CustomerTier.GOLD;
        }

        if (totalOrders >= 5 || totalSpent >= 1000) {
            return CustomerTier.SILVER;
        }

        return CustomerTier.BASIC;
    }
}