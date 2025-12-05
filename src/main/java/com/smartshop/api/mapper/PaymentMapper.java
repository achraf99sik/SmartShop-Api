package com.smartshop.api.mapper;

import com.smartshop.api.dto.PaymentRequestDTO;
import com.smartshop.api.dto.PaymentResponseDTO;
import com.smartshop.api.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "numeroPaiement", ignore = true)
    Payment toEntity(PaymentRequestDTO dto);

    PaymentResponseDTO toDTO(Payment entity);
    List<PaymentResponseDTO> toDTOList(List<Payment> entities);
}