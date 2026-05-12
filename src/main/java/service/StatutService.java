package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Statut;
import repository.StatutRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StatutService {
    @Autowired
    private final StatutRepository repository;

    public StatutService(StatutRepository repository) {
        this.repository = repository;
    }

    public List<Statut> findAll() {
        return repository.findAll();
    }

    public Statut findByNom(String nom) {
        return repository.findByLibelle(nom);
    }

    public Statut findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
