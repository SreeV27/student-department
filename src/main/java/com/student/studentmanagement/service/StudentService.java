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
    private static final String FOUNDLING = "Student not found";

    public StudentService(StudentRepository studentRepository,
                          DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    // Create Student
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

    // Get all Students
    @Cacheable(value = "students")
    public List<StudentDto> getAllStudents() {

        return studentRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get Student by ID
    @Cacheable(value = "student", key = "#id")
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FOUNDLING));

        return mapToDto(student);
    }

    // Get Students by Department
    public List<StudentDto> getStudentsByDepartment(Long departmentId) {

        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department does not exist");
        }
        List<Student> students = studentRepository.findByDepartmentIdOrderByNameAsc(departmentId);
        if (students.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No students found for this department"
            );
        }
        return students.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get Student by Mobile
    public StudentDto getStudentByMobile(String mobile) {
        Student student = studentRepository.findByMobile(mobile)
                .orElseThrow(() -> new ResourceNotFoundException(FOUNDLING));
        return mapToDto(student);
    }

    // Update Student
    public StudentDto updateStudent(Long id, StudentRequest request) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FOUNDLING));

        if (!existingStudent.getEmail().equals(request.getEmail()) &&
                studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateRecordException("Email already exists");
        }

        if (!existingStudent.getMobile().equals(request.getMobile()) &&
                studentRepository.existsByMobile(request.getMobile())) {
            throw new DuplicateRecordException("Mobile already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist"));

        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setMobile(request.getMobile());
        existingStudent.setDob(request.getDob());
        existingStudent.setDepartment(department);

        Student updatedStudent = studentRepository.save(existingStudent);
        return mapToDto(updatedStudent);
    }

    // Delete Student
    public String  deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FOUNDLING));
        studentRepository.delete(student);
        return "Student deleted successfully";
    }

    // Mapping Student to StudentDto
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
