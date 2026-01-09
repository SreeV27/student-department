/**
 * @file DepartmentService.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Department Service
 */

package com.student.studentmanagement.service;

import com.student.studentmanagement.dto.DepartmentDto;
import com.student.studentmanagement.exception.DuplicateRecordException;
import com.student.studentmanagement.exception.ResourceNotFoundException;
import com.student.studentmanagement.model.Department;
import com.student.studentmanagement.repository.DepartmentRepository;
import com.student.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private static final String DEPT_NOT_FOUND = "Department not found";
    public DepartmentService(DepartmentRepository departmentRepository, StudentRepository studentRepository) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
    }

    // Create Department
    public DepartmentDto createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new DuplicateRecordException("Department already exists");
        }
        Department dept = departmentRepository.save(department);
        DepartmentDto dto = new DepartmentDto();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setDescription(dept.getDescription());
        return dto;
    }

    // Get all Departments
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAllByOrderByNameAsc();
        if (departments.isEmpty()) {
            throw new ResourceNotFoundException("No departments found");
        }
        return departments.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get Department by ID
    public DepartmentDto getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DEPT_NOT_FOUND));
        return mapToDto(dept);
    }

    // Update Department
    public DepartmentDto updateDepartment(Long id, Department department) {
        Department existingDept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DEPT_NOT_FOUND));

        if (!existingDept.getName().equals(department.getName()) &&
                departmentRepository.existsByName(department.getName())) {
            throw new DuplicateRecordException("Department name already exists");
        }

        existingDept.setName(department.getName());
        existingDept.setDescription(department.getDescription());
        Department updatedDept = departmentRepository.save(existingDept);
        return mapToDto(updatedDept);
    }

    // Delete Department
    public String deleteDepartment(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DEPT_NOT_FOUND));
        boolean hasStudents = studentRepository.existsByDepartmentId(id);
        if (hasStudents) {
            throw new IllegalStateException(
                    "Department cannot be deleted because students are assigned to it"
            );
        }
        departmentRepository.delete(dept);
        return "Department deleted successfully";
    }

    // Map Department to DepartmentDto
    private DepartmentDto mapToDto(Department dept) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setDescription(dept.getDescription());
        return dto;
    }
}
