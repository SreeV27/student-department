/**
 * @file StudentRequest.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Student Data Transfer Object
 */


package com.student.studentmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentRequest {

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain letters and spaces only")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Mobile number must be exactly 10 digits"
    )
    private String mobile;

    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dob;

    @NotNull(message = "Department ID is mandatory")
    private Long departmentId;


}
