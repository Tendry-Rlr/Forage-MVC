package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>{
    
}
