package service;

import java.util.List;

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

    public Devis save(Devis devis) {
        return this.devisRepository.save(devis);
    }

    public List<Devis> findAll() {
        return this.devisRepository.findAll();
    }

    public Devis findById(Integer id) {
        return (Devis) this.devisRepository.findById(id).orElse(null);
    }

    public Devis findByIdDemande(Integer idDemande) {
        return this.devisRepository.findByIdDemande(idDemande);
    }

    public Devis update(Devis dev) {
        return this.devisRepository.save(dev);
    }

    public void delete(Devis devis) {
        this.devisRepository.delete(devis);
    }
}
