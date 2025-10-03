package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Supplier;
import com.example.servingwebcontent.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final SupplierRepository repo;

    public SupplierService(SupplierRepository repo) {
        this.repo = repo;
    }

    public List<Supplier> findAll() {
        return repo.findAll();
    }

    public Optional<Supplier> findById(Long id) {
        return repo.findById(id);
    }

    public Supplier save(Supplier s) {
        return repo.save(s);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
