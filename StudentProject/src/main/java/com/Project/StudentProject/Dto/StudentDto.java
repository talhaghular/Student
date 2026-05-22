package com.Project.StudentProject.Dto;

import com.Project.StudentProject.Entity.StudentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class StudentDto implements Serializable {

    private int id;
    private String name;
    private int age;
//    private Status status=Status.ACTIVE;


    public StudentDto(int id, String name,int age) {
        this.id = id;
        this.name = name;
        this.age= age;
    }

    public static StudentDto toDto(StudentEntity entity){
        return new StudentDto(
                entity.getId(),
                entity.getName(),
                entity.getAge()
        );
    }
}
