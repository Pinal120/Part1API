package com.example.part1api.dto;

public class AssignmentRequestDTO {
    private Long employeeId;
    private Long departmentId;
    private String role;
    private String accessLevel;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
}
