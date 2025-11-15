package com.company.assetmanagement.service;

import com.company.assetmanagement.model.Asset;
import com.company.assetmanagement.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * AssetService - Implement IAssetService
 * Nguyên tắc OOP: Business Logic Encapsulation, Calculation Methods
 */
@Service
@Transactional
public class AssetService implements IAssetService {
    
    private static final BigDecimal DEPRECIATION_RATE = new BigDecimal("0.2"); // 20% per year
    
    @Autowired
    private AssetRepository assetRepository;
    
    @Override
    public Asset createAsset(Asset asset) {
        if (assetRepository.findByAssetCode(asset.getAssetCode()).isPresent()) {
            throw new IllegalArgumentException("Asset code already exists");
        }
        asset.setIsDisposed(false);
        asset.setCurrentValue(asset.getOriginalValue());
        return assetRepository.save(asset);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Asset> getAssetById(Long id) {
        return assetRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Asset> getAssetByCode(String code) {
        return assetRepository.findByAssetCode(code);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Asset> getAssetsByDepartment(Long departmentId) {
        return assetRepository.findByDepartmentId(departmentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Asset> getAssetsByStatus(Asset.AssetStatus status) {
        return assetRepository.findByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Asset> getAssetsByCategory(Asset.AssetCategory category) {
        return assetRepository.findByCategory(category);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Asset> getActiveAssets() {
        return assetRepository.findByIsDisposedFalse();
    }
    
    @Override
    public Asset updateAsset(Long id, Asset assetDetails) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));
        
        if (assetDetails.getAssetName() != null) {
            asset.setAssetName(assetDetails.getAssetName());
        }
        if (assetDetails.getStatus() != null) {
            asset.setStatus(assetDetails.getStatus());
        }
        if (assetDetails.getCurrentValue() != null) {
            asset.setCurrentValue(assetDetails.getCurrentValue());
        }
        if (assetDetails.getLocation() != null) {
            asset.setLocation(assetDetails.getLocation());
        }
        if (assetDetails.getNotes() != null) {
            asset.setNotes(assetDetails.getNotes());
        }
        
        return assetRepository.save(asset);
    }
    
    @Override
    public void disposeAsset(Long id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));
        asset.setIsDisposed(true);
        asset.setStatus(Asset.AssetStatus.DISPOSED);
        assetRepository.save(asset);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Asset> getDepreciatedAssets(BigDecimal minValue) {
        return assetRepository.findDepreciatedAssets(minValue);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getAssetCountByDepartment(Long departmentId) {
        return assetRepository.countActiveAssetsByDepartment(departmentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalAssetValueByDepartment(Long departmentId) {
        BigDecimal total = assetRepository.sumAssetValueByDepartment(departmentId);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Tính toán khấu hao tài sản
     * Sử dụng phương pháp straight-line depreciation
     */
    @Override
    public BigDecimal calculateDepreciation(Asset asset, int yearsOfUse) {
        if (asset.getOriginalValue() == null || yearsOfUse < 0) {
            throw new IllegalArgumentException("Invalid asset value or years of use");
        }
        
        BigDecimal depreciatedValue = asset.getOriginalValue()
                .multiply(DEPRECIATION_RATE)
                .multiply(new BigDecimal(yearsOfUse));
        
        BigDecimal finalValue = asset.getOriginalValue().subtract(depreciatedValue);
        
        // Giá trị không thể âm
        return finalValue.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }
}
