package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Parametre;
import jakarta.transaction.Transactional;
import repository.ParametreRepository;

@Transactional
@Service
public class ParametreService {
    @Autowired
    private final ParametreRepository parametreRepository;

    public ParametreService(ParametreRepository parametreRepository) {
        this.parametreRepository = parametreRepository;
    }

    public List<Parametre> findAll() {
        return this.parametreRepository.findAll();
    }

    public List<Parametre> findByStatuts(Integer statut1, Integer statut2) {
        return this.parametreRepository.findByStatuts(statut1, statut2);
    }
}
