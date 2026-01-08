/**
 * @file Student.java
 * @company Techversant Infotech
 * @author Sreenath M
 * @date 1/7/2026
 * @version 1.0
 * @description Main Application Class
 */
package com.student.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class StudentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

}
