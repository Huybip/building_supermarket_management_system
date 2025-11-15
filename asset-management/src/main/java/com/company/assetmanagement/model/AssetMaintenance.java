package com.company.assetmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * AssetMaintenance - Lịch sử bảo trì tài sản
 * Nguyên tắc OOP: Single Responsibility, Composition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asset_maintenance")
public class AssetMaintenance extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id", nullable = false)
    @SuppressWarnings("unused")
    private Asset asset;
    
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private LocalDate maintenanceDate;
    
    @Column(length = 500)
    @SuppressWarnings("unused")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private MaintenanceType maintenanceType;
    
    @Column(precision = 10, scale = 2)
    @SuppressWarnings("unused")
    private BigDecimal cost;
    
    @Column(length = 255)
    @SuppressWarnings("unused")
    private String maintenanceBy;
    
    @Column(length = 500)
    @SuppressWarnings("unused")
    private String notes;
    
    public enum MaintenanceType {
        PREVENTIVE,   // Bảo trì định kỳ
        CORRECTIVE,   // Bảo trì sửa chữa
        EMERGENCY     // Bảo trì khẩn cấp
    }
}
