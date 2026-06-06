package service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        DemandeStatut ds = this.findByCurrentStatut(demandeStatut.getDemande().getId_demande());
        double dt = this.calculDT(demandeStatut, ds);

        demandeStatut.setDuree_travaille(dt);
        if (demandeStatut.getObservation() == null) {
            demandeStatut.setObservation("Pas d'observation");
        }
        return repository.save(demandeStatut);
    }

    public void update(DemandeStatut nouveau, DemandeStatut ancien) {
        double dt = this.calculDT(nouveau, ancien);
        nouveau.setDuree_travaille(dt);
        repository.save(nouveau);
    }

    public List<DemandeStatut> findByDemande(Integer id) {
        return repository.findByIdDemande(id);
    }

    public List<DemandeStatut> findByDemandeOrderByDate(Integer id) {
        return repository.findByIdDemandeDateASC(id);
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

    public DemandeStatut findByIdStatutAIdDemande(Integer iddemande, Integer idstatut) {
        return this.repository.findByIdStatutAIdDemande(iddemande, idstatut);
    }

    public DemandeStatut findById(Integer id) {
        return this.repository.findById(id).orElse(null);
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

    public double calculDT(DemandeStatut demandeStatut, DemandeStatut ancienDs) {
        DemandeStatut ds = ancienDs;

        if (ds == null) {
            return 0;
        }

        LocalTime travailDebut = LocalTime.of(8, 0);
        LocalTime travailFin = LocalTime.of(16, 0);

        LocalDateTime ancien = ds.getDate();
        LocalDateTime nouveau = demandeStatut.getDate();

        ancien = this.formate(travailDebut, travailFin, ancien);
        nouveau = this.formate(travailDebut, travailFin, nouveau);

        // Utiliser LocalDate.equals() pas getDayOfMonth()
        if (ancien.toLocalDate().equals(nouveau.toLocalDate())) {
            // Utiliser les minutes
            long minutes = ChronoUnit.MINUTES.between(ancien, nouveau);
            return minutes;
        } else {
            // Calcul pour jours différents
            long weekend = this.nombreJoursWeekend(ancien.toLocalDate(), nouveau.toLocalDate());
            long jours = ChronoUnit.DAYS.between(ancien.toLocalDate(), nouveau.toLocalDate());
            // ne pas compter le dernier jours
            jours -= 1;
            jours -= weekend;

            double heuresParJour = travailFin.getHour() - travailDebut.getHour(); // 8h
            double minute = (jours * heuresParJour) * 60;

            // Premier jour
            long minutesPremierJour = ChronoUnit.MINUTES.between(ancien, ancien.with(travailFin));
            minute += minutesPremierJour;

            // Dernier jour
            long minutesDernierJour = ChronoUnit.MINUTES.between(nouveau.with(travailDebut), nouveau);
            if (minutesDernierJour > 0) {
                minute += minutesDernierJour;
            }

            return minute;
        }
    }

    public long nombreJoursWeekend(LocalDate dateDebut, LocalDate dateFin) {
        long nombre = 0;
        LocalDate current = dateDebut;

        while (!current.isAfter(dateFin)) {
            DayOfWeek jour = current.getDayOfWeek();
            if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) {
                nombre++;
            }
            current = current.plusDays(1);
        }
        return nombre;
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
