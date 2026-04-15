package com.example.part1api.service;

import com.example.part1api.dto.EmployeeRequestDTO;
import com.example.part1api.model.Employee;
import com.example.part1api.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(String department) {
        if (department != null && !department.isBlank()) {
            return employeeRepository.findByAssignments_Department_Name(department);
        }
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(EmployeeRequestDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setContractType(dto.getContractType());
        employee.setStartDate(dto.getStartDate());
        employee.setSalary(dto.getSalary());
        return employeeRepository.save(employee);
    }

    public Optional<Employee> updateEmployee(Long id, EmployeeRequestDTO dto) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(dto.getName());
            employee.setContractType(dto.getContractType());
            employee.setStartDate(dto.getStartDate());
            employee.setSalary(dto.getSalary());
            return employeeRepository.save(employee);
        });
    }

    public boolean deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }

    // thing 10% salary increase thing. returns nothing (empty) if not found, throws if < 6 months.
    public Optional<Employee> promoteEmployee(Long id) {
        Optional<Employee> found = employeeRepository.findById(id);
        if (found.isEmpty()) {
            return Optional.empty();
        }

        Employee employee = found.get();
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        if (employee.getStartDate() == null || employee.getStartDate().isAfter(sixMonthsAgo)) {
            throw new IllegalStateException("Employee has not been with the company for at least 6 months");
        }

        employee.setSalary(employee.getSalary() * 1.10);
        return Optional.of(employeeRepository.save(employee));
    }
}
