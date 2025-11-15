package com.company.assetmanagement.repository;

import com.company.assetmanagement.model.AssetMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * AssetMaintenanceRepository - Abstraction layer cho AssetMaintenance
 * Nguyên tắc OOP: Abstraction, Single Responsibility
 */
@Repository
public interface AssetMaintenanceRepository extends JpaRepository<AssetMaintenance, Long> {
    
    List<AssetMaintenance> findByAssetId(Long assetId);
    
    List<AssetMaintenance> findByMaintenanceDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<AssetMaintenance> findByMaintenanceType(AssetMaintenance.MaintenanceType maintenanceType);
    
    @Query("SELECT am FROM AssetMaintenance am WHERE am.asset.id = :assetId ORDER BY am.maintenanceDate DESC")
    List<AssetMaintenance> findMaintenanceHistoryByAsset(@Param("assetId") Long assetId);
}
