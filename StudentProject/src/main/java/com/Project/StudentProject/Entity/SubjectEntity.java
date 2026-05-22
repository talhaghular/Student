package com.Project.StudentProject.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SubjectEntity {

    @Id
    private Integer subjectId;
    private String name;

    @ManyToMany(mappedBy = "subjectEntity")
    private List<StudentEntity> studentEntity=new ArrayList<>();
}
