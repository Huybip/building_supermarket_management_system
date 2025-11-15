package com.company.assetmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Asset - Tài sản công
 * Nguyên tắc OOP: Encapsulation, Polymorphism (có thể mở rộng)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assets")
public class Asset extends BaseEntity {
    
    @Column(nullable = false, unique = true, length = 50)
    @SuppressWarnings("unused")
    private String assetCode;
    
    @Column(nullable = false, length = 255)
    @SuppressWarnings("unused")
    private String assetName;
    
    @Column(length = 500)
    @SuppressWarnings("unused")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private AssetCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private AssetStatus status;
    
    @Column(nullable = false, precision = 15, scale = 2)
    @SuppressWarnings("unused")
    private BigDecimal originalValue;
    
    @Column(precision = 15, scale = 2)
    @SuppressWarnings("unused")
    private BigDecimal currentValue;
    
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private LocalDate purchaseDate;
    
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private LocalDate warrantyDate;
    
    @Column(length = 255)
    @SuppressWarnings("unused")
    private String serialNumber;
    
    @Column(length = 255)
    @SuppressWarnings("unused")
    private String location;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    @SuppressWarnings("unused")
    private Department department;
    
    @Column(length = 255)
    @SuppressWarnings("unused")
    private String imagePath;
    
    @Column(nullable = false)
    @SuppressWarnings("unused")
    private Boolean isDisposed = false;
    
    @Column(length = 500)
    @SuppressWarnings("unused")
    private String notes;
    
    // Enum cho danh mục tài sản
    public enum AssetCategory {
        ELECTRONICS,      // Thiết bị điện tử
        FURNITURE,        // Nội thất
        MACHINERY,        // Máy móc
        BUILDING,         // Xây dựng
        VEHICLE,          // Phương tiện
        OFFICE_SUPPLIES,  // Văn phòng phẩm
        OTHER             // Khác
    }
    
    // Enum cho trạng thái tài sản
    public enum AssetStatus {
        ACTIVE,           // Đang sử dụng
        MAINTENANCE,      // Bảo trì
        DAMAGED,          // Hỏng
        DISPOSED,         // Đã thanh lý
        STORED            // Lưu kho
    }
}
