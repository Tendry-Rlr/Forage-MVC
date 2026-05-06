package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer>{
    @Query("select d from District d where d.region.id_region = :regionId")
    List<District> findByRegionId(@Param("regionId") Integer regionId);
}
