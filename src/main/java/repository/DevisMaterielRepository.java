package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.DevisMateriel;

@Repository
public interface DevisMaterielRepository extends JpaRepository<DevisMateriel, Integer> {
    @Modifying
    @Query("delete from DevisMateriel dm where dm.devis.id_devis = :id_devis")
    void deleteByIdDevis(@Param("id_devis") Integer id_devis);

    // void deleteByDevis_Id_devis(int id_devis);
}
