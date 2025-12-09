package com.smartshop.api.controller;

import com.smartshop.api.dto.ClientRequestDTO;
import com.smartshop.api.dto.ClientResponseDTO;
import com.smartshop.api.mapper.ClientMapper;
import com.smartshop.api.service.ClientAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientAdminService clientAdminService;
    private final ClientMapper clientMapper;

    @GetMapping
    public List<ClientResponseDTO> getAll() {
        return clientMapper.toDTOList(clientAdminService.getAllClients());
    }

    @PostMapping
    public ClientResponseDTO create(@RequestBody ClientRequestDTO dto) {
        return clientMapper.toDTO(clientAdminService.createClient(dto));
    }

    @PutMapping("/{id}")
    public ClientResponseDTO update(@PathVariable UUID id, @RequestBody ClientRequestDTO dto) {
        return clientMapper.toDTO(clientAdminService.updateClient(id, dto));
    }
    @GetMapping("/{id}")
    public ClientResponseDTO get(@PathVariable UUID id) {
        return clientMapper.toDTO(clientAdminService.getClient(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        clientAdminService.deleteClient(id);
    }
}
