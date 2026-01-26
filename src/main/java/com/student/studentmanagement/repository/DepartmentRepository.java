/**
 * @file DDepartmentRepository.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Department Repository
 */

package com.student.studentmanagement.repository;

import com.student.studentmanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
    List<Department> findAllByOrderByNameAsc();
}
