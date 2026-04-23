package com.example.part1api.service;

import com.example.part1api.model.Employee;
import com.example.part1api.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void promoteEmployee_shouldReturnEmpty_whenEmployeeDoesNotExist() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.promoteEmployee(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void promoteEmployee_shouldThrow_whenEmployeeStartedLessThanSixMonthsAgo() {
        Employee recentEmployee = new Employee();
        recentEmployee.setStartDate(LocalDate.now().minusMonths(2));
        recentEmployee.setSalary(50000);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(recentEmployee));

        assertThrows(IllegalStateException.class, () -> employeeService.promoteEmployee(1L));
    }

    @Test
    void promoteEmployee_shouldThrow_whenEmployeeHasNullStartDate() {
        Employee employee = new Employee();
        employee.setStartDate(null);
        employee.setSalary(50000);

        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee));

        assertThrows(IllegalStateException.class, () -> employeeService.promoteEmployee(2L));
    }

    @Test
    void promoteEmployee_shouldIncreaseSalaryByTenPercent_whenEligible() {
        Employee employee = new Employee();
        employee.setStartDate(LocalDate.now().minusMonths(12));
        employee.setSalary(50000);

        when(employeeRepository.findById(3L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Optional<Employee> result = employeeService.promoteEmployee(3L);

        assertTrue(result.isPresent());
        assertEquals(55000.0, result.get().getSalary(), 0.001);
    }
}
