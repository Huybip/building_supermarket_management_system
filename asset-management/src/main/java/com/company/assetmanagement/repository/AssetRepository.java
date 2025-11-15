package com.company.assetmanagement.repository;

import com.company.assetmanagement.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * AssetRepository - Abstraction layer cho Asset
 * Nguyên tắc OOP: Abstraction, Query Objects Pattern
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    
    Optional<Asset> findByAssetCode(String assetCode);
    
    List<Asset> findByDepartmentId(Long departmentId);
    
    List<Asset> findByStatus(Asset.AssetStatus status);
    
    List<Asset> findByCategory(Asset.AssetCategory category);
    
    List<Asset> findByIsDisposedFalse();
    
    List<Asset> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT a FROM Asset a WHERE a.currentValue < :minValue AND a.isDisposed = false")
    List<Asset> findDepreciatedAssets(@Param("minValue") BigDecimal minValue);
    
    @Query("SELECT COUNT(a) FROM Asset a WHERE a.department.id = :departmentId AND a.isDisposed = false")
    Long countActiveAssetsByDepartment(@Param("departmentId") Long departmentId);
    
    @Query("SELECT SUM(a.currentValue) FROM Asset a WHERE a.department.id = :departmentId AND a.isDisposed = false")
    BigDecimal sumAssetValueByDepartment(@Param("departmentId") Long departmentId);
}
