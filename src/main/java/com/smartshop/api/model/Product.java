package com.smartshop.api.model;


import com.smartshop.api.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private double prixHT;

    @Column(nullable = false)
    private int stock;

    @Builder.Default
    private boolean deleted = false;
}
