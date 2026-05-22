package com.Project.StudentProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String courseName;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private StudentEntity studentEntity;

    private int maxEnrollment;

    private int currentEnrollment;
}
