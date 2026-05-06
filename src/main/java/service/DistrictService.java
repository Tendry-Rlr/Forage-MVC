package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.District;
import repository.DistrictRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DistrictService {
    @Autowired
    private final DistrictRepository repository;

    public DistrictService(DistrictRepository repository) {
        this.repository = repository;
    }

    public List<District> findAll() {
        return repository.findAll();
    }

    public List<District> findByRegion(Integer id) {
        return this.repository.findByRegionId(id);
    }
}
