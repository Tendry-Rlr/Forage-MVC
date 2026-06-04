import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        LocalDateTime ancien = LocalDateTime.of(2026, 05, 30, 7, 00),
                nouveau = LocalDateTime.of(2026, 06, 30, 22, 00);
        
        double heure = 0, diff = 0;
        ancien = Temps.formate(intervalle_debut, intervalle_fin, ancien);
        nouveau = Temps.formate(intervalle_debut, intervalle_fin, nouveau);

        if (ancien.getDayOfMonth() == nouveau.getDayOfMonth()) {
            System.out.println("meme jour");
            heure = nouveau.getHour() - ancien.getHour();
            System.out.println(ancien.getDayOfWeek());
        } else {
            System.out.println("jour differente");
            long jours = java.time.temporal.ChronoUnit.DAYS.between(ancien.toLocalDate(),
                    nouveau.toLocalDate());
            System.out.println(jours);

            double active = intervalle_fin.getHour() - intervalle_debut.getHour();
            heure += jours * active;
            System.out.println("Middle days : " + heure + "h");

            LocalTime ancienTime = ancien.toLocalTime(),
                    nouveauTime = nouveau.toLocalTime();

            // tokony misy egalite
            if ((ancienTime.isAfter(intervalle_debut) || (ancienTime.equals(intervalle_debut)) &&
                    (ancienTime.isBefore(intervalle_fin)) || (ancienTime.equals(intervalle_fin)))) {
                diff = intervalle_fin.getHour() - ancienTime.getHour();
                heure += diff;
                System.out.println("First day " + diff + "h");
            }
            if ((nouveauTime.isAfter(intervalle_debut) || nouveauTime.equals(intervalle_debut)) &&
                    (nouveauTime.isBefore(intervalle_fin) || (nouveauTime.equals(intervalle_fin)))) {
                diff = nouveauTime.getHour() - intervalle_debut.getHour();
                heure += diff;
                System.out.println("Last day " + diff + "h");
            }
        }
        heure *= 60;
        System.out.println(heure);

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
