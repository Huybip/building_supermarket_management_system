package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Category;
import com.example.servingwebcontent.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Optional<Category> findById(Long id) {
        return repo.findById(id);
    }

    public Category save(Category c) {
        return repo.save(c);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
