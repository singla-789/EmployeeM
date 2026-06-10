package com.hkcs.employeemanagement.service;

import com.hkcs.employeemanagement.Dto.DepartmentRequestDTO;
import com.hkcs.employeemanagement.Dto.DepartmentResponseDTO;
import com.hkcs.employeemanagement.Entity.Department;
import com.hkcs.employeemanagement.repo.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO) {
        if (departmentRepo.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new IllegalArgumentException("Department with name '" + requestDTO.getName() + "' already exists");
        }
        Department newDept = mapToEntity(requestDTO);
        departmentRepo.save(newDept);
        return mapToDto(newDept);
    }

    public DepartmentResponseDTO getDepartmentById(String id) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return mapToDto(department);
    }

    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<DepartmentResponseDTO> getActiveDepartments() {
        return departmentRepo.findByActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<DepartmentResponseDTO> searchDepartments(String keyword) {
        return departmentRepo.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DepartmentResponseDTO updateDepartment(String id, DepartmentRequestDTO requestDTO) {
        Department existing = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        existing.setName(requestDTO.getName());
        existing.setDescription(requestDTO.getDescription());
        existing.setLocation(requestDTO.getLocation());
        existing.setActive(requestDTO.isActive());

        Department updated = departmentRepo.save(existing);
        return mapToDto(updated);
    }

    public void deleteDepartment(String id) {
        Department department = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        departmentRepo.delete(department);
    }

    private Department mapToEntity(DepartmentRequestDTO requestDTO) {
        Department department = new Department();
        department.setName(requestDTO.getName());
        department.setDescription(requestDTO.getDescription());
        department.setLocation(requestDTO.getLocation());
        department.setActive(requestDTO.isActive());
        return department;
    }

    private DepartmentResponseDTO mapToDto(Department department) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setLocation(department.getLocation());
        dto.setActive(department.isActive());
        return dto;
    }
}
