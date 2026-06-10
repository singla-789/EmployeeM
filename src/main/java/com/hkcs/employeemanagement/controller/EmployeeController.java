package com.hkcs.employeemanagement.controller;

import com.hkcs.employeemanagement.Dto.EmployeeRequestDto;
import com.hkcs.employeemanagement.Dto.EmployeeResponseDto;
import com.hkcs.employeemanagement.Entity.Employee.EmploymentStatus;
import com.hkcs.employeemanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto requestDTO) {
        EmployeeResponseDto response = employeeService.createEmployee(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "firstName") String sort) {

        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            Page<EmployeeResponseDto> response = employeeService.getAllEmployeesPaginated(pageable);
            return ResponseEntity.ok(response);
        }

        List<EmployeeResponseDto> response = employeeService.getAllEmployees();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable String id) {
        EmployeeResponseDto response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/department/{departmentId}", "/dept/{departmentId}"})
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDepartment(@PathVariable String departmentId) {
        List<EmployeeResponseDto> response = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByStatus(@PathVariable EmploymentStatus status) {
        List<EmployeeResponseDto> response = employeeService.getEmployeesByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDto>> searchEmployees(@RequestParam String keyword) {
        List<EmployeeResponseDto> response = employeeService.searchEmployees(keyword);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable String id,
            @RequestBody EmployeeRequestDto requestDTO) {
        EmployeeResponseDto response = employeeService.updateEmployee(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeStatus(
            @PathVariable String id,
            @RequestParam EmploymentStatus status) {
        EmployeeResponseDto response = employeeService.updateEmployeeStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
