package com.smartshop.api.mapper;

import com.smartshop.api.dto.OrderRequestDTO;
import com.smartshop.api.dto.OrderResponseDTO;
import com.smartshop.api.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemMapper.class}
)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "subtotalHT", ignore = true)
    @Mapping(target = "remise", ignore = true)
    @Mapping(target = "montantHTAfterRemise", ignore = true)
    @Mapping(target = "tva", ignore = true)
    @Mapping(target = "totalTTC", ignore = true)
    @Mapping(target = "montantRestant", ignore = true)
    @Mapping(target = "status", ignore = true)
    Order toEntity(OrderRequestDTO dto);
    @Mapping(source = "client.id", target = "clientId")
    OrderResponseDTO toDTO(Order order);
}
