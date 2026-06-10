package com.hkcs.employeemanagement.repo;

import com.hkcs.employeemanagement.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, String> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<Employee> findByDepartmentId(String departmentId);

    List<Employee> findByStatus(String status);

    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    Page<Employee> findAll(Pageable pageable);

    long countByDepartmentId(String departmentId);
}