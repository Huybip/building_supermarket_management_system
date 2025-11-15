package com.company.assetmanagement.service;

import com.company.assetmanagement.model.Department;
import com.company.assetmanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * DepartmentService - Implement IDepartmentService
 * Nguyên tắc OOP: Dependency Injection, Transactional Management
 */
@Service
@Transactional
public class DepartmentService implements IDepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Override
    public Department createDepartment(Department department) {
        if (isDepartmentExists(department.getDepartmentCode())) {
            throw new IllegalArgumentException("Department code already exists");
        }
        department.setIsActive(true);
        return departmentRepository.save(department);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Department> getDepartmentByCode(String code) {
        return departmentRepository.findByDepartmentCode(code);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Department> getAllActiveDepartments() {
        return departmentRepository.findByIsActiveTrue();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    @Override
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        
        if (departmentDetails.getDepartmentName() != null) {
            department.setDepartmentName(departmentDetails.getDepartmentName());
        }
        if (departmentDetails.getDescription() != null) {
            department.setDescription(departmentDetails.getDescription());
        }
        if (departmentDetails.getIsActive() != null) {
            department.setIsActive(departmentDetails.getIsActive());
        }
        
        return departmentRepository.save(department);
    }
    
    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        department.setIsActive(false);
        departmentRepository.save(department);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isDepartmentExists(String departmentCode) {
        return departmentRepository.findByDepartmentCode(departmentCode).isPresent();
    }
}
