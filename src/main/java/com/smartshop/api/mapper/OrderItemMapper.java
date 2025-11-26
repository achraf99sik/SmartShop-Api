package com.smartshop.api.mapper;

import com.smartshop.api.dto.OrderItemResponseDTO;
import com.smartshop.api.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.nom", target = "productName")
    OrderItemResponseDTO toDTO(OrderItem item);
}
