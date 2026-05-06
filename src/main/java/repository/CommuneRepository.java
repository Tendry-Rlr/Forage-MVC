package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.Commune;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Integer>{
    @Query("select c from Commune c where c.district.id_district = :districtId")
    List<Commune> findByDistrictId(@Param("districtId") Integer districtId);

}
