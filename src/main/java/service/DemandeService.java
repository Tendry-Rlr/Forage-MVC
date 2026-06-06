package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Demande;
import entity.DemandeStatut;
import entity.Parametre;
import entity.Statut;
import repository.DemandeRepository;

import jakarta.transaction.Transactional;
import model.DemandeAlerte;

@Service
@Transactional
public class DemandeService {
    @Autowired
    private final DemandeRepository repository;

    private final ParametreService parametreService;
    private final DemandeStatutService demandeStatutService;
    private final StatutService statutService;

    public DemandeService(DemandeRepository repository, ParametreService parametreService,
            DemandeStatutService demandeStatutService, StatutService statutService) {
        this.repository = repository;
        this.parametreService = parametreService;
        this.demandeStatutService = demandeStatutService;
        this.statutService = statutService;
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

    public List<DemandeAlerte> demandeAlerte(Integer id_demande) {
        List<Parametre> parametres = parametreService.findAll();

        // Utilisation d'une Map pour éviter les doublons naturellement
        Map<Integer, DemandeAlerte> alertesMap = new HashMap<>();

        for (Parametre parametre : parametres) {
            Integer idStatut1 = parametre.getStatut1().getId_statut();
            Integer idStatut2 = parametre.getStatut2().getId_statut();

            DemandeStatut ds1 = demandeStatutService.findByIdStatutAIdDemande(id_demande, idStatut1);
            DemandeStatut ds2 = demandeStatutService.findByIdStatutAIdDemande(id_demande, idStatut2);

            if (ds1 == null && ds2 == null) {
                continue;
            }

            // Cas où un seul des deux statuts existe
            if (ds1 == null || ds2 == null) {
                DemandeStatut existingStatut = (ds2 != null) ? ds2 : ds1;
                int statutId = existingStatut.getStatut().getId_statut();

                // Vérifier si on a déjà une alerte pour ce statut
                if (!alertesMap.containsKey(statutId)) {
                    alertesMap.put(statutId, new DemandeAlerte(existingStatut, "Pas de correspondance"));
                }
                continue;
            }

            // Cas où les deux statuts existent
            int ecart = idStatut2 - idStatut1;
            double dt;

            if (ecart > 1) {
                dt = this.cumulDT(id_demande, idStatut1, idStatut2);
            } else {
                dt = ds2.getDuree_travaille();
            }

            String alerte = this.defineAlerte(parametre, dt);
            Statut autre = statutService.findById(idStatut1);

            // Utiliser l'ID du statut2 comme clé (ou l'ID du DemandeStatut)
            int key = ds2.getId_demande_statut();

            // Ne garder que la première alerte pour chaque DemandeStatut
            if (!alertesMap.containsKey(key)) {
                alertesMap.put(key, new DemandeAlerte(ds2, alerte, autre));
            }
        }

        return new ArrayList<>(alertesMap.values());
    }

    public String defineAlerte(Parametre param, double dt) {
        List<Parametre> liste = parametreService.findByStatuts(param.getStatut1().getId_statut(),
                param.getStatut2().getId_statut());
        Parametre init = liste.get(0), last = liste.get(liste.size() - 1);
        if (init.getDuree_travaille() >= dt) {
            return "Pas d'alerte";
        }
        if (dt > last.getDuree_travaille()) {
            return last.getAlerte();
        }
        for (int i = 1; i < liste.size(); i++) {
            Parametre old = liste.get(i - 1), current = liste.get(i);
            if (old.getDuree_travaille() <= dt && current.getDuree_travaille() > dt) {
                return old.getAlerte();
            }
        }
        return "Pas d'alerte";
    }

    public double cumulDT(Integer id_demande, Integer id_statut1, Integer id_statut2) {
        double dt = 0;
        for (int i = id_statut1; i <= id_statut2; i++) {
            DemandeStatut ds = demandeStatutService.findByIdStatutAIdDemande(id_demande, i);
            if (ds != null) {
                dt += ds.getDuree_travaille();
            }
        }
        return dt;
    }

}
