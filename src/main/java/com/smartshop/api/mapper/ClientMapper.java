package com.smartshop.api.mapper;

import com.smartshop.api.dto.ClientRequestDTO;
import com.smartshop.api.dto.ClientResponseDTO;
import com.smartshop.api.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toDTO(Client entity);
}
