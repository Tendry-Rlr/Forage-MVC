package repository;

import entity.DemandeStatut;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeStatutRepository extends JpaRepository<DemandeStatut, Integer> {
    @Query("select ds from DemandeStatut ds where ds.demande.id_demande = :demandeid")
    List<DemandeStatut> findByIdDemande(@Param("demandeid") Integer demandeid);

    @Query("select ds from DemandeStatut ds where ds.demande.id_demande = :idd and ds.id_demande_statut = :ids")
    DemandeStatut findDemandeStatutByDemande(@Param("idd") Integer idd, @Param("ids") Integer ids);

    @Query("select ds from DemandeStatut ds where ds.demande.id_demande = :idd and ds.statut.id_statut = :idstatut order by date desc")
    DemandeStatut findDemandeStatutByStatut(@Param("idd") Integer idd, @Param("idstatut") Integer idstatut);

    @Query("select ds from DemandeStatut ds where ds.demande.id_demande = :id_demande order by ds.date desc limit 1")
    DemandeStatut findDemandeStatutByCurrentDate(@Param("id_demande") Integer id_demande);

    @Query("select ds from DemandeStatut ds where ds.demande.id_demande = :idd and ds.statut.id_statut = :ids order by ds.date desc limit 1")
    DemandeStatut findByIdStatutAIdDemande(@Param("idd") Integer idd, @Param("ids") Integer ids);

}
