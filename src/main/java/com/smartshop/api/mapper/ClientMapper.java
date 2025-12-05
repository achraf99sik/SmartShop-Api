package com.smartshop.api.mapper;

import com.smartshop.api.dto.ClientRequestDTO;
import com.smartshop.api.dto.ClientResponseDTO;
import com.smartshop.api.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientResponseDTO toDTO(Client client);

    List<ClientResponseDTO> toDTOList(List<Client> clients);

    Client toEntity(ClientRequestDTO dto);

    void updateEntity(ClientRequestDTO dto, @MappingTarget Client client);
}
