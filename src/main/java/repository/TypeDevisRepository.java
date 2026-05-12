package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.TypeDevis;

@Repository
public interface TypeDevisRepository extends JpaRepository<TypeDevis, Integer> {

}
