package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Order;
import com.example.servingwebcontent.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public List<Order> findAll() {
        return repo.findAll();
    }

    public Optional<Order> findById(Long id) {
        return repo.findById(id);
    }

    public Order save(Order o) {
        return repo.save(o);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
