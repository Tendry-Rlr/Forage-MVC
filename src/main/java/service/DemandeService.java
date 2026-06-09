package service;

import java.util.ArrayList;
import java.util.Comparator;
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
        List<DemandeAlerte> resultat = new ArrayList<>();

        // Récupérer tous les statuts de la demande triés par date
        List<DemandeStatut> statutsDemande = demandeStatutService.findByDemandeOrderByDate(id_demande);

        if (statutsDemande == null || statutsDemande.isEmpty()) {
            return resultat;
        }

        // Parcourir les transitions entre statuts consécutifs
        for (int i = 0; i < statutsDemande.size() - 1; i++) {
            DemandeStatut courant = statutsDemande.get(i);
            DemandeStatut suivant = statutsDemande.get(i + 1);

            int idStatutCourant = courant.getStatut().getId_statut();
            int idStatutSuivant = suivant.getStatut().getId_statut();

            // Calculer le DT cumulé entre ces deux statuts
            double dtCumule = cumulDT(id_demande, idStatutCourant, idStatutSuivant);

            // Récupérer les paramètres pour ce couple
            List<Parametre> parametresCouple = parametreService.findByStatuts(idStatutCourant, idStatutSuivant);

            String alerte = "Pas d'alerte";

            // Utiliser defineAlerte si des paramètres existent
            if (parametresCouple != null && !parametresCouple.isEmpty()) {
                // Prendre le premier paramètre (ou n'importe lequel car defineAlerte recherche
                // tous)
                alerte = defineAlerte(parametresCouple.get(0), dtCumule);
            }

            resultat.add(new DemandeAlerte(suivant, alerte, courant.getStatut()));
        }
        return resultat;
    }

    public String defineAlerte(Parametre param, double dt) {
        List<Parametre> liste = parametreService.findByStatuts(
                param.getStatut1().getId_statut(),
                param.getStatut2().getId_statut());

        if (liste == null || liste.isEmpty()) {
            return "Pas d'alerte";
        }

        // Trier par durée croissante
        liste.sort(Comparator.comparingDouble(Parametre::getDuree_travaille));

        // if (dt < liste.get(0).getDuree_travaille()) {
        // return "Pas d'alerte";
        // }
        // if (dt == liste.get(0).getDuree_travaille()) {
        // return liste.get(0).getAlerte();
        // }

        // Trouver le premier seuil dépassé
        for (int i = 1; i < liste.size(); i++) {
            if (liste.get(i).getDuree_travaille() <= dt && dt <= liste.get(i).getDuree_travaille1()) {
                return liste.get(i).getAlerte();
            }
            // if (dt < liste.get(i).getDuree_travaille()) {
            // return liste.get(i - 1).getAlerte();
            // }
        }

        // Si dépasse tous les seuils
        return "Pas d'alerte";
    }

    public double cumulDT(Integer id_demande, Integer id_statut1, Integer id_statut2) {
        double dt = 0;

        List<DemandeStatut> statuts = demandeStatutService.findByDemandeOrderByDate(id_demande);
        boolean commencer = false;
        for (DemandeStatut ds : statuts) {
            int idStatut = ds.getStatut().getId_statut();

            if (idStatut == id_statut1) {
                commencer = true;
            }
            if (commencer) {
                dt += ds.getDuree_travaille();
            }
            if (idStatut == id_statut2) {
                break;
            }
        }
        return dt;
    }
}
