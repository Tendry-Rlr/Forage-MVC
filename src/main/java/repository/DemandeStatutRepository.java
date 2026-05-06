package repository;

import entity.DemandeStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeStatutRepository extends JpaRepository<DemandeStatut, Long> {
    // Ajoutez des méthodes personnalisées ici si nécessaire
}
