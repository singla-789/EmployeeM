package com.hkcs.employeemanagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employees")
public class Employee {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;
    private Double salary;
    private LocalDate joiningDate;
    private String status = "ACTIVE";

    @DBRef
    private Department department;

    public enum EmploymentStatus {
        ACTIVE, INACTIVE, ON_LEAVE, TERMINATED
    }
}