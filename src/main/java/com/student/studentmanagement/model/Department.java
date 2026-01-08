/**
 * @author Sreenath M
 * @version 1.0
 * @file Department.java
 * @company Techversant Infotech
 * @date 1/7/2026
 * @description Department Entity
 */


package com.student.studentmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "department",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        },
        indexes = {
                @Index(name = "idx_dept", columnList = "id,name")
        }
)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be null")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain letters and spaces only")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Student> students = new ArrayList<>();

    @Column(name = "description")
    private String description;

}
