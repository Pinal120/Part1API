package com.example.part1api.dto;

public class DepartmentRequestDTO {
    private String name;
    private double budget;
    private String location;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
