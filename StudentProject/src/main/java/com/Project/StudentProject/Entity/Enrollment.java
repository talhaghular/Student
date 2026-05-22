package com.Project.StudentProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"courseId","studentId"})
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollmentId;
    private LocalDate enrollmentDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "studentId")
    private StudentEntity student;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "courseId")
    private CourseEntity course;

    @PrePersist
    public void prePersistCourse(){
        this.enrollmentDate=LocalDate.now();
    }
}
