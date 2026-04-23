package com.example.part1api.service;

import com.example.part1api.dto.DepartmentRequestDTO;
import com.example.part1api.model.Department;
import com.example.part1api.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void createDepartment_shouldThrow_whenBudgetIsNegative() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Operations");
        dto.setBudget(-5000);
        dto.setLocation("Dublin");

        assertThrows(IllegalArgumentException.class, () -> departmentService.createDepartment(dto));
    }

    @Test
    void createDepartment_shouldThrow_whenBudgetIsZero() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Operations");
        dto.setBudget(0);
        dto.setLocation("Dublin");

        assertThrows(IllegalArgumentException.class, () -> departmentService.createDepartment(dto));
    }

    @Test
    void createDepartment_shouldReturnSavedDepartment_whenBudgetIsPositive() {
        DepartmentRequestDTO dto = new DepartmentRequestDTO();
        dto.setName("Operations");
        dto.setBudget(100000);
        dto.setLocation("Dublin");

        Department saved = new Department();
        saved.setName("Operations");
        saved.setBudget(100000);
        saved.setLocation("Dublin");

        when(departmentRepository.save(any(Department.class))).thenReturn(saved);

        Department result = departmentService.createDepartment(dto);

        assertNotNull(result);
        assertEquals("Operations", result.getName());
        assertEquals(100000, result.getBudget(), 0.001);
    }
}
