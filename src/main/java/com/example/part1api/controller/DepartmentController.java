package com.example.part1api.controller;

import com.example.part1api.dto.DepartmentRequestDTO;
import com.example.part1api.model.Department;
import com.example.part1api.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // GET /api/departments — HR & MAnager (RBAC)
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // POST /api/departments — RBAC (HR Only), Sec Config
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentRequestDTO dto) {
        Department created = departmentService.createDepartment(dto);
        return ResponseEntity.status(201).body(created);
    }
}
