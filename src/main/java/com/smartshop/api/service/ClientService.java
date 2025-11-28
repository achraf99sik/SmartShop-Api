package com.smartshop.api.service;

import com.smartshop.api.enums.CustomerTier;
import com.smartshop.api.model.Client;
import com.smartshop.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client getClient(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void updateStats(Client client, double orderTotal) {
        client.setTotalOrders(client.getTotalOrders() + 1);
        client.setTotalSpent(client.getTotalSpent() + orderTotal);
        clientRepository.save(client);
    }

    public void updateTier(Client client, CustomerTier tier) {
        client.setTier(tier);
        clientRepository.save(client);
    }
}
