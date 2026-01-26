/**
 * @file StudentDto.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Student Dto
 */
package com.student.studentmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String dob;
    private Long departmentId;
    private String department;
}
