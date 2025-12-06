package com.smartshop.api.controller;

import com.smartshop.api.dto.ProductRequestDTO;
import com.smartshop.api.dto.ProductResponseDTO;
import com.smartshop.api.mapper.ProductMapper;
import com.smartshop.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productMapper.toDTOList(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProduct(@PathVariable UUID id) {
        return productMapper.toDTO(productService.getProduct(id));
    }

    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody @Valid ProductRequestDTO dto) {
        return productMapper.toDTO(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(
            @PathVariable UUID id,
            @RequestBody @Valid ProductRequestDTO dto
    ) {
        return productMapper.toDTO(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.softDeleteProduct(id);
    }
}
