package com.hkcs.employeemanagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDTO {

    private String id;
    private String name;
    private String description;
    private String location;
    private boolean active;
}