package com.Project.StudentProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class StudentProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentProjectApplication.class, args);
	}

}
