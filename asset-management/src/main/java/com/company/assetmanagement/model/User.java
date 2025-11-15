package com.company.assetmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * User - Người dùng hệ thống
 * Nguyên tắc OOP: Encapsulation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    
    @Column(nullable = false, unique = true, length = 100)
    @SuppressWarnings("unused")
    private String username;
    
    @Column(nullable = false, length = 255)
    @SuppressWarnings("unused")
    private String password;
    
    @Column(nullable = false, length = 100)
    @SuppressWarnings("unused")
    private String fullName;
    
    @Column(unique = true, length = 100)
    @SuppressWarnings("unused")
    private String email;
    
    @Column(length = 20)
    @SuppressWarnings("unused")
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private UserRole role;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    @SuppressWarnings("unused")
    private Department department;
    
    @Column(name = "is_active")
    @SuppressWarnings("unused")
    private Boolean isActive = true;
    
    public enum UserRole {
        ADMIN,
        MANAGER,
        STAFF,
        VIEWER
    }
}
