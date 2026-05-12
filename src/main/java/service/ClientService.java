package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Client;
import repository.ClientRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientService {
    @Autowired
    private final ClientRepository clients;

    public ClientService(ClientRepository repository) {
        this.clients = repository;
    }

    public List<Client> findAll() {
        return clients.findAll();
    }

    public Client findById(Integer id){
        return clients.findById(id).orElse(null);
    }
    

}
