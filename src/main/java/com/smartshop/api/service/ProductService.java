package com.smartshop.api.service;

import com.smartshop.api.dto.ProductRequestDTO;
import com.smartshop.api.exception.BusinessException;
import com.smartshop.api.exception.ResourceNotFoundException;
import com.smartshop.api.model.Product;
import com.smartshop.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findByDeletedFalse();
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(ProductRequestDTO dto) {
        Product product = Product.builder()
                .nom(dto.getNom())
                .prixHT(dto.getPrixHT())
                .stock(dto.getStock())
                .deleted(false)
                .build();

        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, ProductRequestDTO dto) {
        Product product = getProduct(id);

        product.setNom(dto.getNom());
        product.setPrixHT(dto.getPrixHT());
        product.setStock(dto.getStock());

        return productRepository.save(product);
    }

    public void softDeleteProduct(UUID id) {
        Product product = getProduct(id);
        product.setDeleted(true);
        productRepository.save(product);
    }

    public Product checkStock(UUID productId, int qty) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (product.getStock() < qty) {
            throw new BusinessException("Insufficient stock for product: " + product.getNom());
        }

        return product;
    }

    public void decrementStock(UUID productId, int qty) {
        Product product = checkStock(productId, qty);
        product.setStock(product.getStock() - qty);
        productRepository.save(product);
    }
}

