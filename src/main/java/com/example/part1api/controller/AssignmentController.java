package com.example.part1api.controller;

import com.example.part1api.dto.AssignmentRequestDTO;
import com.example.part1api.model.Assignment;
import com.example.part1api.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // POST /api/assignments, with the sec config for RBAC (HR)
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody AssignmentRequestDTO dto) {
        Assignment created = assignmentService.createAssignment(dto);
        return ResponseEntity.status(201).body(created);
    }

    // DELETE /api/assignments/{id}, RBAC (HR)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        if (assignmentService.deleteAssignment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
