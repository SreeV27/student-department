/**
 * @file StudentRepository.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description StudentRepository
 */

package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    Optional<Student> findByMobile(String mobile);
    boolean existsByDepartmentId(Long departmentId);
    List<Student> findAllByOrderByNameAsc();
    List<Student> findByDepartmentIdOrderByNameAsc(Long departmentId);


}
