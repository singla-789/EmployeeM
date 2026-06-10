package com.hkcs.employeemanagement.controller;

import com.hkcs.employeemanagement.Dto.DepartmentRequestDTO;
import com.hkcs.employeemanagement.Dto.DepartmentResponseDTO;
import com.hkcs.employeemanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentRequestDTO requestDTO) {
        DepartmentResponseDTO response = departmentService.createDepartment(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments(@RequestParam(required = false) Boolean active) {
        List<DepartmentResponseDTO> response;
        if (active != null && active) {
            response = departmentService.getActiveDepartments();
        } else {
            response = departmentService.getAllDepartments();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable String id) {
        DepartmentResponseDTO response = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DepartmentResponseDTO>> searchDepartments(@RequestParam String keyword) {
        List<DepartmentResponseDTO> response = departmentService.searchDepartments(keyword);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(
            @PathVariable String id,
            @RequestBody DepartmentRequestDTO requestDTO) {
        DepartmentResponseDTO response = departmentService.updateDepartment(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
