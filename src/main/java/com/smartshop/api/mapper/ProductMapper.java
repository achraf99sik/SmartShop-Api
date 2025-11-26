package com.smartshop.api.mapper;

import com.smartshop.api.dto.ProductRequestDTO;
import com.smartshop.api.dto.ProductResponseDTO;
import com.smartshop.api.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDTO(Product entity);
}
