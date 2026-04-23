package com.example.part1api.service;

import com.example.part1api.dto.AssignmentRequestDTO;
import com.example.part1api.model.Assignment;
import com.example.part1api.model.Department;
import com.example.part1api.model.Employee;
import com.example.part1api.repository.AssignmentRepository;
import com.example.part1api.repository.DepartmentRepository;
import com.example.part1api.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             EmployeeRepository employeeRepository,
                             DepartmentRepository departmentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Assignment createAssignment(AssignmentRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + dto.getEmployeeId()));
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + dto.getDepartmentId()));

        Assignment assignment = new Assignment();
        assignment.setEmployee(employee);
        assignment.setDepartment(department);
        assignment.setRole(dto.getRole());
        assignment.setAccessLevel(dto.getAccessLevel());
        return assignmentRepository.save(assignment);
    }

    public boolean deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            return false;
        }
        assignmentRepository.deleteById(id);
        return true;
    }
}
