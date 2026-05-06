package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.DemandeStatut;
import repository.DemandeStatutRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DemandeStatutService {
    @Autowired
    private final DemandeStatutRepository repository;

    public DemandeStatutService(DemandeStatutRepository repository) {
        this.repository = repository;
    }

    public List<DemandeStatut> findAll() {
        return repository.findAll();
    }
}
