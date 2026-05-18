package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.DevisMateriel;
import jakarta.transaction.Transactional;
import repository.DevisMaterielRepository;

@Service
@Transactional
public class DevisMaterielService {
    @Autowired
    private final DevisMaterielRepository devisMaterielRepository;

    public DevisMaterielService(DevisMaterielRepository devisMaterielRepository) {
        this.devisMaterielRepository = devisMaterielRepository;
    }

    public void save(DevisMateriel devisMateriel) {
        this.devisMaterielRepository.save(devisMateriel);
    }

    public void deleteById(Integer id_devis) {
        this.devisMaterielRepository.deleteByIdDevis(id_devis);
    }

}
