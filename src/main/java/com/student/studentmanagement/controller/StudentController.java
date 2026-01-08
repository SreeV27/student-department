/**
 * @file StudentController.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Student Controller
 */

package com.student.studentmanagement.controller;

import com.student.studentmanagement.dto.StudentDto;
import com.student.studentmanagement.dto.StudentRequest;
import com.student.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create Student (mapped to existing Department)
    @PostMapping
    public StudentDto createStudent(@Valid @RequestBody StudentRequest request) {
        return studentService.createStudent(request);
    }

    // Get all Students
    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get Student by ID
    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // Get Students by Department
    @GetMapping("/department/{departmentId}")
    public List<StudentDto> getStudentsByDepartment(@PathVariable Long departmentId) {
        return studentService.getStudentsByDepartment(departmentId);
    }

    // Get Student by Mobile
    @GetMapping("/mobile/{mobile}")
    public StudentDto getStudentByMobile(@PathVariable String mobile) {
        return studentService.getStudentByMobile(mobile);
    }
}
