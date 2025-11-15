package com.company.assetmanagement.service;

import com.company.assetmanagement.model.Department;
import java.util.List;
import java.util.Optional;

/**
 * IDepartmentService - Interface cho Department Service
 * Nguyên tắc OOP: Abstraction, Interface Segregation
 */
public interface IDepartmentService {
    
    Department createDepartment(Department department);
    
    Optional<Department> getDepartmentById(Long id);
    
    Optional<Department> getDepartmentByCode(String code);
    
    List<Department> getAllActiveDepartments();
    
    List<Department> getAllDepartments();
    
    Department updateDepartment(Long id, Department department);
    
    void deleteDepartment(Long id);
    
    boolean isDepartmentExists(String departmentCode);
}
