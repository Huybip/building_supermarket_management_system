package com.company.assetmanagement.service;

import com.company.assetmanagement.model.Asset;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * IAssetService - Interface cho Asset Service
 * Nguyên tắc OOP: Abstraction, Service Locator Pattern
 */
public interface IAssetService {
    
    Asset createAsset(Asset asset);
    
    Optional<Asset> getAssetById(Long id);
    
    Optional<Asset> getAssetByCode(String code);
    
    List<Asset> getAllAssets();
    
    List<Asset> getAssetsByDepartment(Long departmentId);
    
    List<Asset> getAssetsByStatus(Asset.AssetStatus status);
    
    List<Asset> getAssetsByCategory(Asset.AssetCategory category);
    
    List<Asset> getActiveAssets();
    
    Asset updateAsset(Long id, Asset asset);
    
    void disposeAsset(Long id);
    
    List<Asset> getDepreciatedAssets(BigDecimal minValue);
    
    Long getAssetCountByDepartment(Long departmentId);
    
    BigDecimal getTotalAssetValueByDepartment(Long departmentId);
    
    BigDecimal calculateDepreciation(Asset asset, int yearsOfUse);
}
