package com.smartshop.api.service;

import com.smartshop.api.dto.ClientRequestDTO;
import com.smartshop.api.exception.ResourceNotFoundException;
import com.smartshop.api.mapper.ClientMapper;
import com.smartshop.api.model.Client;
import com.smartshop.api.model.User;
import com.smartshop.api.repository.ClientRepository;
import com.smartshop.api.repository.UserRepository;
import com.smartshop.api.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientAdminService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;
    private final PasswordUtil passwordUtil;

    public Client createClient(ClientRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getUserName());
        System.out.println(dto);
        String hashed = passwordUtil.hash(dto.getPassword());
        user.setPassword(hashed);
        user.setRole(dto.getRole());
        userRepository.save(user);

        Client client = clientMapper.toEntity(dto);
        client.setUser(user);

        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(UUID id, ClientRequestDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with this id : " + id));

        clientMapper.updateEntity(dto, client);

        return clientRepository.save(client);
    }
    public Client getClient(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with this id : " + id));
    }

    public void deleteClient(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with this id : " + id));

        userRepository.delete(client.getUser());
        clientRepository.delete(client);
    }
}
