package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.Devis;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Integer> {

    @EntityGraph(attributePaths = {"devis_materiels"})
    Optional findById(Integer id_devis);

    @Query("select dev from Devis dev where dev.demande.id_demande = :id_demande")
    Devis findByIdDemande(@Param("id_demande") Integer id_demande);

}
