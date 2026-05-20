package service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
        double dt = this.calculDT(demandeStatut);
        demandeStatut.setDuree_travaille(dt);
        if (demandeStatut.getObservation() == null) {
            demandeStatut.setObservation("Pas d'observation");
        }
        return repository.save(demandeStatut);
    }

    public List<DemandeStatut> findByDemande(Integer id) {
        return repository.findByIdDemande(id);
    }

    public DemandeStatut findDemandeStatutByDemande(Integer idd, Integer ids) {
        return repository.findDemandeStatutByDemande(idd, ids);
    }

    public DemandeStatut findByDemandeStatutByStatut(Integer idd, Integer idstatut) {
        return repository.findDemandeStatutByStatut(idd, idstatut);
    }

    public DemandeStatut findByCurrentStatut(Integer id_demande) {
        return this.repository.findDemandeStatutByCurrentDate(id_demande);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public void update(DemandeStatut d) {
        if (d.getObservation() == null) {
            d.setObservation("Pas d'observation");
        }
        repository.save(d);
    }

    public double calculDT(DemandeStatut demandeStatut) {
        double heure = 0, diff = 0;
        DemandeStatut ds = this.findByCurrentStatut(demandeStatut.getDemande().getId_demande());
        if (ds == null) {
            return 0;
        }

        LocalTime intervalle_debut = LocalTime.of(8, 0);
        LocalTime intervalle_fin = LocalTime.of(16, 0);

        LocalDateTime ancien = ds.getDate();
        LocalDateTime nouveau = demandeStatut.getDate();

        ancien = this.formate(intervalle_debut, intervalle_fin, ancien);
        nouveau = this.formate(intervalle_debut, intervalle_fin, nouveau);

        if (ancien.getDayOfMonth() == nouveau.getDayOfMonth()) {
            // System.out.println("meme jour");
            heure = nouveau.getHour() - ancien.getHour();
        } else {
            // System.out.println("jour differente");
            long jours = java.time.temporal.ChronoUnit.DAYS.between(ancien.toLocalDate(),
                    nouveau.toLocalDate());
            // System.out.println(jours);

            double active = intervalle_fin.getHour() - intervalle_debut.getHour();
            heure += jours * active;
            // System.out.println("Middle days : " + heure + "h");

            LocalTime ancienTime = ancien.toLocalTime(),
                    nouveauTime = nouveau.toLocalTime();

            if ((ancienTime.isAfter(intervalle_debut) || (ancienTime.equals(intervalle_debut)) &&
                    (ancienTime.isBefore(intervalle_fin)) || (ancienTime.equals(intervalle_fin)))) {
                diff = intervalle_fin.getHour() - ancienTime.getHour();
                heure += diff;
                // System.out.println("First day " + diff + "h");
            }
            if ((nouveauTime.isAfter(intervalle_debut) || nouveauTime.equals(intervalle_debut)) &&
                    (nouveauTime.isBefore(intervalle_fin) || (nouveauTime.equals(intervalle_fin)))) {
                diff = nouveauTime.getHour() - intervalle_debut.getHour();
                heure += diff;
                // System.out.println("Last day " + diff + "h");
            }
        }
        return heure;
    }

    public LocalDateTime formate(LocalTime debut, LocalTime fin, LocalDateTime verif) {
        LocalTime test = verif.toLocalTime();
        if (test.isBefore(debut)) {
            test = debut;
        }
        if (test.isAfter(fin)) {
            test = fin;
        }
        return verif.with(test);
    }
}
