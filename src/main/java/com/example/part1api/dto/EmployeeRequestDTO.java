package com.example.part1api.dto;

import java.time.LocalDate;

public class EmployeeRequestDTO {
    private String name;
    private String contractType;
    private LocalDate startDate;
    private double salary;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContractType() { return contractType; }
    public void setContractType(String contractType) { this.contractType = contractType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}
