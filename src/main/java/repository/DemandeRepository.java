package repository;

import entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    // Ajoutez des méthodes personnalisées ici si nécessaire

    @Query("SELECT d FROM Demande d LEFT JOIN FETCH d.demandeStatuts ds LEFT JOIN FETCH ds.statut")
    List<Demande> findAllWithStatuts();

}

