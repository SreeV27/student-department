/**
 * @file StudentService.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Student Service
 */
package com.student.studentmanagement.service;


import com.student.studentmanagement.dto.StudentDto;
import com.student.studentmanagement.dto.StudentRequest;
import com.student.studentmanagement.exception.DuplicateRecordException;
import com.student.studentmanagement.exception.ResourceNotFoundException;
import com.student.studentmanagement.model.Department;
import com.student.studentmanagement.model.Student;
import com.student.studentmanagement.repository.DepartmentRepository;
import com.student.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public StudentService(StudentRepository studentRepository,
                          DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    public StudentDto createStudent(StudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateRecordException("Email already exists");
        }

        if (studentRepository.existsByMobile(request.getMobile())) {
            throw new DuplicateRecordException("Mobile already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist"));

        Student student = new Student(
                request.getName(),
                request.getEmail(),
                request.getMobile(),
                request.getDob(),
                department
        );

        Student savedStudent = studentRepository.save(student);
        return mapToDto(savedStudent);
    }

    @Cacheable(value = "students")
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Cacheable(value = "student", key = "#id")
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return mapToDto(student);
    }

    public List<StudentDto> getStudentsByDepartment(Long departmentId) {


        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department does not exist");
        }

        return studentRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public StudentDto getStudentByMobile(String mobile) {
        Student student = studentRepository.findByMobile(mobile)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return mapToDto(student);
    }

    private StudentDto mapToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setMobile(student.getMobile());
        dto.setDob(String.valueOf(student.getDob()));
        dto.setDepartmentId(student.getDepartment().getId());
        dto.setDepartment(student.getDepartment().getName());
        return dto;
    }
}
