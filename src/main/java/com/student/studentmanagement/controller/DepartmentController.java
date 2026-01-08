/**
 * @file DepartmentController.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Department Controller
 */
package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.DepartmentDto;
import com.student.studentmanagement.model.Department;
import com.student.studentmanagement.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Create Department
    @PostMapping
    public DepartmentDto createDepartment(@Valid @RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    // Get all Departments
    @GetMapping
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    // Get Department by ID
    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    //update Department
    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(@PathVariable Long id, @Valid @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    //delete Department
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDepartment(@PathVariable Long id) {
        String message = departmentService.deleteDepartment(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}
