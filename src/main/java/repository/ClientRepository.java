package repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    // Dans votre ClientRepository
    @Query("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.demandes WHERE c.id = :id")
    Optional<Client> findByIdWithDemandes(@Param("id") Integer id);
}
