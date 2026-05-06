package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Demande;
import repository.DemandeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DemandeService {
    @Autowired
    private final DemandeRepository repository;

    public DemandeService(DemandeRepository repository) {
        this.repository = repository;
    }

    public List<Demande> findAll() {
        return this.repository.findAll();
    }

    public Demande save(Demande demande) {
        return this.repository.save(demande);
    }

        
}
