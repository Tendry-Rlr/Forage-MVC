package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Devis;
import jakarta.transaction.Transactional;
import repository.DevisRepository;

@Service
@Transactional
public class DevisService {
    @Autowired
    private final DevisRepository devisRepository;

    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    public Devis save(Devis devis){
        return this.devisRepository.save(devis);
    }
}
