package com.company.assetmanagement.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * BaseEntity - Class cha cho tất cả entities
 * Nguyên tắc OOP: Inheritance (Kế thừa)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SuppressWarnings("unused")
    private Long id;
    
    @Column(nullable = false, updatable = false)
    @SuppressWarnings("unused")
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private LocalDateTime updatedAt;
    
    @Column(length = 50)
    @SuppressWarnings("unused")
    private String createdBy;
    
    @Column(length = 50)
    @SuppressWarnings("unused")
    private String updatedBy;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
