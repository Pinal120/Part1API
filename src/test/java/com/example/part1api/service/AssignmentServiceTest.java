package com.example.part1api.service;

import com.example.part1api.dto.AssignmentRequestDTO;
import com.example.part1api.model.Assignment;
import com.example.part1api.model.Department;
import com.example.part1api.model.Employee;
import com.example.part1api.repository.AssignmentRepository;
import com.example.part1api.repository.DepartmentRepository;
import com.example.part1api.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void createAssignment_shouldThrow_whenEmployeeDoesNotExist() {
        AssignmentRequestDTO dto = new AssignmentRequestDTO();
        dto.setEmployeeId(99L);
        dto.setDepartmentId(1L);
        dto.setRole("Dispatcher");
        dto.setAccessLevel("READ");

        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> assignmentService.createAssignment(dto));
    }

    @Test
    void createAssignment_shouldThrow_whenDepartmentDoesNotExist() {
        AssignmentRequestDTO dto = new AssignmentRequestDTO();
        dto.setEmployeeId(1L);
        dto.setDepartmentId(99L);
        dto.setRole("Dispatcher");
        dto.setAccessLevel("READ");

        Employee employee = new Employee();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> assignmentService.createAssignment(dto));
    }

    @Test
    void createAssignment_shouldReturnSavedAssignment_whenBothExist() {
        AssignmentRequestDTO dto = new AssignmentRequestDTO();
        dto.setEmployeeId(1L);
        dto.setDepartmentId(1L);
        dto.setRole("Dispatcher");
        dto.setAccessLevel("READ");

        Employee employee = new Employee();
        Department department = new Department();
        Assignment assignment = new Assignment();
        assignment.setEmployee(employee);
        assignment.setDepartment(department);
        assignment.setRole("Dispatcher");
        assignment.setAccessLevel("READ");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        Assignment result = assignmentService.createAssignment(dto);

        assertNotNull(result);
        assertEquals("Dispatcher", result.getRole());
    }
}
