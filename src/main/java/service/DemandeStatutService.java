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

    public DemandeStatut save(DemandeStatut demandeStatut) {
        return repository.save(demandeStatut);
    }

    public List<DemandeStatut> findByDemande(Integer id) {
        return repository.findByIdDemande(id);
    }

    public DemandeStatut findDemandeStatutByDemande(Integer idd, Integer ids) {
        return repository.findDemandeStatutByDemande(idd, ids);
    }

    public DemandeStatut finByDemandeStatutByStatut(Integer idd, Integer idstatut) {
        return repository.findDemandeStatutByStatut(idd, idstatut);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public void update(DemandeStatut d) {
        repository.save(d);
    }
}
