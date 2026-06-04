import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Temps {
    public static LocalDateTime formate(LocalTime debut, LocalTime fin, LocalDateTime verif) {
        LocalTime test = verif.toLocalTime();
        if (test.isBefore(debut)) {
            test = debut;
        }
        if (test.isAfter(fin)) {
            test = fin;
        }
        return verif.with(test);
    }

    public static void main(String[] args) {
        LocalTime intervalle_debut = LocalTime.of(8, 0);
        LocalTime intervalle_fin = LocalTime.of(16, 0);

        // 12 Décembre 2026 à 14h30
        LocalDateTime dateSpecifique = LocalDateTime.of(2026, 05, 31, 14, 30);

        System.out.println(dateSpecifique.getDayOfWeek());
        dateSpecifique.getDayOfWeek();
        if (dateSpecifique.getDayOfWeek() == DayOfWeek.SUNDAY || dateSpecifique.getDayOfWeek() == DayOfWeek.SATURDAY) {
            System.out.println("weekend");
        }
        LocalDateTime ancien = LocalDateTime.of(2026, 06, 04, 15, 10),
                nouveau = LocalDateTime.of(2026, 06, 4, 18, 00);

        double heure = 0, diff = 0;
        ancien = Temps.formate(intervalle_debut, intervalle_fin, ancien);
        nouveau = Temps.formate(intervalle_debut, intervalle_fin, nouveau);

        double minutes = 0;
        if (ancien.toLocalDate().equals(nouveau.toLocalDate())) {
            // Utiliser les minutes, pas seulement les heures
            minutes = ChronoUnit.MINUTES.between(ancien, nouveau);
        } else {
            // Calcul pour jours différents
            long weekend = ancien.nombreJoursWeekend(ancien.toLocalDate(), nouveau.toLocalDate());
            long jours = ChronoUnit.DAYS.between(ancien.toLocalDate(), nouveau.toLocalDate());
            jours -= weekend;

            double heuresParJour = travailFin.getHour() - travailDebut.getHour(); // 8h
            minutes = (jours * heuresParJour) * 60;

            // Premier jour
            long minutesPremierJour = ChronoUnit.MINUTES.between(ancien, ancien.with(travailFin));
            minutes += minutesPremierJour;

            // Dernier jour
            long minutesDernierJour = ChronoUnit.MINUTES.between(nouveau.with(travailDebut), nouveau);
            if (minutesDernierJour > 0) {
                minutes += minutesDernierJour;
            }
            System.out.println(minutes);

            long nombre = 0;
            LocalDate current = ancien.toLocalDate();

            while (!current.isAfter(nouveau.toLocalDate())) {
                DayOfWeek jour = current.getDayOfWeek();
                if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) {
                    nombre++;
                }
                current = current.plusDays(1);
            }

            System.out.println(nombre);
        }

    }
}