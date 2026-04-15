package com.example.part1api.controller;

import com.example.part1api.dto.EmployeeRequestDTO;
import com.example.part1api.model.Employee;
import com.example.part1api.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /api/employees?department
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestParam(required = false) String department) {
        return ResponseEntity.ok(employeeService.getAllEmployees(department));
    }

    // GET /api/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/employees — HR RBAC
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeRequestDTO dto) {
        Employee created = employeeService.createEmployee(dto);
        return ResponseEntity.status(201).body(created);
    }

    // PUT /api/employees/{id} — HR RBAXC
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody EmployeeRequestDTO dto) {
        return employeeService.updateEmployee(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/employees/{id} — HR RBAC
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /api/employees/{id}/promote — HR RBAC
    @PutMapping("/{id}/promote")
    public ResponseEntity<Object> promoteEmployee(@PathVariable Long id) {
        try {
            return employeeService.promoteEmployee(id)
                    .<ResponseEntity<Object>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
