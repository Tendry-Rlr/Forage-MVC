package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.TypeDevis;
import jakarta.transaction.Transactional;
import repository.TypeDevisRepository;

@Service
@Transactional
public class TypeDevisService {
    @Autowired
    private final TypeDevisRepository typeDevisRepository;

    public TypeDevisService(TypeDevisRepository typeDevisRepository) {
        this.typeDevisRepository = typeDevisRepository;
    }

    public TypeDevis save(TypeDevis type) {
        return this.typeDevisRepository.save(type);
    }

    public TypeDevis findById(int id) {
        return this.typeDevisRepository.findById(id).orElse(null);
    }

    public List<TypeDevis> findAll() {
        return this.typeDevisRepository.findAll();
    }

}
