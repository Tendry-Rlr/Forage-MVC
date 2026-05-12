package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Commune;
import repository.CommuneRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommuneService {
    @Autowired
    private final CommuneRepository repository;

    public CommuneService(CommuneRepository repository) {
        this.repository = repository;
    }

    public List<Commune> findAll() {
        return repository.findAll();
    }

    public List<Commune> findByDistrict(Integer id_district) {
        return repository.findByDistrictId(id_district);
    }

    public Commune findById(Integer id) 
    {
        return repository.findById(id).orElse(null);
    }
}
