package com.hkcs.employeemanagement.repo;

import com.hkcs.employeemanagement.Entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepo extends MongoRepository<Department, String> {

    boolean existsByNameIgnoreCase(String name);

    List<Department> findByActiveTrue();

    List<Department> findByNameContainingIgnoreCase(String keyword);
}