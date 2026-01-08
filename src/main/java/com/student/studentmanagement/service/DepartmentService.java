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
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDto createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new DuplicateRecordException("Department already exists");
        }
        Department dept = departmentRepository.save(department);
        DepartmentDto dto = new DepartmentDto();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        return dto;
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public DepartmentDto getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return mapToDto(dept);
    }

    private DepartmentDto mapToDto(Department dept) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        return dto;
    }
}
