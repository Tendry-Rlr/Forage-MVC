package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.Parametre;

@Repository
public interface ParametreRepository extends JpaRepository<Parametre, Integer> {
    @Query("select p from Parametre p where p.statut1.id_statut = :id_statut1 and p.statut2.id_statut = :id_statut2")
    List<Parametre> findByStatuts(@Param("id_statut1") Integer id_statut1, @Param("id_statut2") Integer id_statut2);

}
