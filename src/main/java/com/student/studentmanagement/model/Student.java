/**
 * @file Student.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Student Entity
 */
package com.student.studentmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "mobile")
        },
        indexes = {
                @Index(name = "idx_student", columnList = "id,email,mobile")
        }
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Invalid email format")
    @Column(unique = true)
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    @Column(unique = true)
    private String mobile;

    @NotNull(message = "Date of birth is mandatory")
    @Column(nullable = false)
    private LocalDate dob;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Student(
            String name,
            String email,
            String mobile,
            LocalDate dob,
            Department department
    ) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.department = department;
    }


}