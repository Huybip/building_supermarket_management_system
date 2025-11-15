package com.company.assetmanagement.controller;

import com.company.assetmanagement.model.Asset;
import com.company.assetmanagement.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * AssetController - REST API cho Asset
 * Nguyên tắc OOP: Separation of Concerns
 */
@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
public class AssetController {
    
    @Autowired
    private IAssetService assetService;
    
    @PostMapping
    public ResponseEntity<?> createAsset(@RequestBody Asset asset) {
        try {
            Asset created = assetService.createAsset(asset);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetById(@PathVariable Long id) {
        Optional<Asset> asset = assetService.getAssetById(id);
        if (asset.isPresent()) {
            return new ResponseEntity<>(asset.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Asset not found", HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<?> getAssetByCode(@PathVariable String code) {
        Optional<Asset> asset = assetService.getAssetByCode(code);
        if (asset.isPresent()) {
            return new ResponseEntity<>(asset.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Asset not found", HttpStatus.NOT_FOUND);
    }
    
    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Asset>> getActiveAssets() {
        List<Asset> assets = assetService.getActiveAssets();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Asset>> getAssetsByDepartment(@PathVariable Long departmentId) {
        List<Asset> assets = assetService.getAssetsByDepartment(departmentId);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Asset>> getAssetsByStatus(@PathVariable Asset.AssetStatus status) {
        List<Asset> assets = assetService.getAssetsByStatus(status);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Asset>> getAssetsByCategory(@PathVariable Asset.AssetCategory category) {
        List<Asset> assets = assetService.getAssetsByCategory(category);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        try {
            Asset updated = assetService.updateAsset(id, asset);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}/dispose")
    public ResponseEntity<?> disposeAsset(@PathVariable Long id) {
        try {
            assetService.disposeAsset(id);
            return new ResponseEntity<>("Asset disposed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/department/{departmentId}/count")
    public ResponseEntity<Long> getAssetCountByDepartment(@PathVariable Long departmentId) {
        Long count = assetService.getAssetCountByDepartment(departmentId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @GetMapping("/department/{departmentId}/totalvalue")
    public ResponseEntity<BigDecimal> getTotalValueByDepartment(@PathVariable Long departmentId) {
        BigDecimal total = assetService.getTotalAssetValueByDepartment(departmentId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
    
    @GetMapping("/depreciated/{minValue}")
    public ResponseEntity<List<Asset>> getDepreciatedAssets(@PathVariable BigDecimal minValue) {
        List<Asset> assets = assetService.getDepreciatedAssets(minValue);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}
