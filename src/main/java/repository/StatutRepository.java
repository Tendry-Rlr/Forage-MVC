package repository;

import entity.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Long> {
    // Ajoutez des méthodes personnalisées ici si nécessaire
}
