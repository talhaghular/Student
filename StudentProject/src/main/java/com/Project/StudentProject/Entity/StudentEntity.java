package com.Project.StudentProject.Entity;

import com.Project.StudentProject.constant.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DialectOverride;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @NotNull(message = "Name cannot null")
    @NotBlank(message = "Name Cannot be Empty")
    private String name;

    @NotBlank(message = "Email Cannot be Blank")
    @Email(message = "Email should be Valid")
    private String email;

    @NotNull(message = "Age cannot be null")
    @Min(value = 1, message = "Age must be Greater Then 0")
    @Max(value = 100, message = "Age must be under 100")
    private Integer age;

//    @NotBlank(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;

    @OneToMany(mappedBy = "studentEntity")
    private List<CourseEntity> courseEntity;


    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")

    )
    @JsonIgnore
    private List<SubjectEntity> subjectEntity = new ArrayList<>();
}
