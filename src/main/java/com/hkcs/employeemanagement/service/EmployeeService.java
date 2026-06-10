package com.hkcs.employeemanagement.service;

import com.hkcs.employeemanagement.Dto.DepartmentResponseDTO;
import com.hkcs.employeemanagement.Dto.EmployeeRequestDto;
import com.hkcs.employeemanagement.Dto.EmployeeResponseDto;
import com.hkcs.employeemanagement.Entity.Department;
import com.hkcs.employeemanagement.Entity.Employee;
import com.hkcs.employeemanagement.Entity.Employee.EmploymentStatus;
import com.hkcs.employeemanagement.repo.DepartmentRepo;
import com.hkcs.employeemanagement.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepository;
    private final DepartmentRepo departmentRepository;

    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDTO) {
        if (employeeRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Employee with email '" + requestDTO.getEmail() + "' already exists");
        }

        if (employeeRepository.existsByPhone(requestDTO.getPhone())) {
            throw new IllegalArgumentException("Employee with phone '" + requestDTO.getPhone() + "' already exists");
        }

        Department department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + requestDTO.getDepartmentId()));

        Employee employee = mapToEntity(requestDTO, department);
        Employee saved = employeeRepository.save(employee);
        return mapToResponseDTO(saved);
    }

    public EmployeeResponseDto getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return mapToResponseDTO(employee);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<EmployeeResponseDto> getAllEmployeesPaginated(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    public EmployeeResponseDto updateEmployee(String id, EmployeeRequestDto requestDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        if (!existing.getEmail().equalsIgnoreCase(requestDTO.getEmail()) &&
                employeeRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Employee with email '" + requestDTO.getEmail() + "' already exists");
        }

        if (!existing.getPhone().equals(requestDTO.getPhone()) &&
                employeeRepository.existsByPhone(requestDTO.getPhone())) {
            throw new IllegalArgumentException("Employee with phone '" + requestDTO.getPhone() + "' already exists");
        }

        Department department = departmentRepository.findById(requestDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + requestDTO.getDepartmentId()));

        existing.setFirstName(requestDTO.getFirstName());
        existing.setLastName(requestDTO.getLastName());
        existing.setEmail(requestDTO.getEmail());
        existing.setPhone(requestDTO.getPhone());
        existing.setDesignation(requestDTO.getDesignation());
        existing.setSalary(requestDTO.getSalary());
        existing.setJoiningDate(requestDTO.getJoiningDate());
        existing.setStatus(requestDTO.getStatus());
        existing.setDepartment(department);

        Employee updated = employeeRepository.save(existing);
        return mapToResponseDTO(updated);
    }

    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    public List<EmployeeResponseDto> getEmployeesByDepartment(String departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));

        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDto> getEmployeesByStatus(EmploymentStatus status) {
        return employeeRepository.findByStatus(status.name()).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDto> searchEmployees(String keyword) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDto updateEmployeeStatus(String id, EmploymentStatus status) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setStatus(status.name());
        Employee updated = employeeRepository.save(employee);
        return mapToResponseDTO(updated);
    }

    private Employee mapToEntity(EmployeeRequestDto dto, Department department) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setDesignation(dto.getDesignation());
        employee.setSalary(dto.getSalary());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setStatus(dto.getStatus());
        employee.setDepartment(department);
        return employee;
    }

    private EmployeeResponseDto mapToResponseDTO(Employee employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setDesignation(employee.getDesignation());
        dto.setSalary(employee.getSalary());
        dto.setJoiningDate(employee.getJoiningDate());
        dto.setStatus(employee.getStatus());

        if (employee.getDepartment() != null) {
            DepartmentResponseDTO deptDTO = new DepartmentResponseDTO();
            deptDTO.setId(employee.getDepartment().getId());
            deptDTO.setName(employee.getDepartment().getName());
            deptDTO.setDescription(employee.getDepartment().getDescription());
            deptDTO.setLocation(employee.getDepartment().getLocation());
            deptDTO.setActive(employee.getDepartment().isActive());
            dto.setDepartment(deptDTO);
        }

        return dto;
    }
}