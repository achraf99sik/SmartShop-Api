package com.smartshop.api.service;

import com.smartshop.api.model.Product;
import com.smartshop.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product checkStock(UUID productId, int qty) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < qty) {
            throw new RuntimeException("Insufficient stock for product: " + product.getNom());
        }

        return product;
    }

    public void decrementStock(UUID productId, int qty) {
        Product product = checkStock(productId, qty);
        product.setStock(product.getStock() - qty);
        productRepository.save(product);
    }
}
