package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Devis;

@Repository
public interface DevisRepository  extends JpaRepository<Devis, Integer>{
    
}
