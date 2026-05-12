package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.DevisMateriel;

@Repository
public interface DevisMaterielRepository extends JpaRepository<DevisMateriel, Integer> {
    
}
