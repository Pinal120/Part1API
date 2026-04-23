package com.example.part1api.service;

import com.example.part1api.dto.DepartmentRequestDTO;
import com.example.part1api.model.Department;
import com.example.part1api.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(DepartmentRequestDTO dto) {
        if (dto.getBudget() <= 0) {
            throw new IllegalArgumentException("Budget must be a positive value");
        }
        Department department = new Department();
        department.setName(dto.getName());
        department.setBudget(dto.getBudget());
        department.setLocation(dto.getLocation());
        return departmentRepository.save(department);
    }
}
