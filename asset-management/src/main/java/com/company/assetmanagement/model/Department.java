package com.company.assetmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Department - Đơn vị/Phòng ban quản lý tài sản
 * Nguyên tắc OOP: Encapsulation (Đóng gói), Composition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
    
    @Column(nullable = false, unique = true, length = 100)
    @SuppressWarnings("unused")
    private String departmentName;
    
    @Column(length = 500)
    @SuppressWarnings("unused")
    private String description;
    
    @Column(length = 20)
    @SuppressWarnings("unused")
    private String departmentCode;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @SuppressWarnings("unused")
    private List<Asset> assets;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @SuppressWarnings("unused")
    private List<User> users;
    
    @Column(name = "is_active")
    @SuppressWarnings("unused")
    private Boolean isActive = true;
}
