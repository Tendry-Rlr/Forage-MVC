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

    public List<Demande> findAllWithStatuts() {
        return this.repository.findAllWithStatuts();
    }

    public Demande save(Demande demande) {
        return this.repository.save(demande);
    }

    public Demande findById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }
    
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    } 

    public void update(Demande d) {
        this.repository.save(d);
    }
    
}
