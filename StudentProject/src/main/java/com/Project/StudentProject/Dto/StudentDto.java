package com.Project.StudentProject.Dto;

import com.Project.StudentProject.Entity.StudentEntity;
import com.Project.StudentProject.constant.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto implements Serializable {


    private int id;

    @NotBlank(message = "Name Cannot be Empty")
    private String name;

    @NotNull(message = "Age cannot be null")
    @Min(value = 1, message = "Age must be Greater Then 0")
    @Max(value = 100, message = "Age must be under 100")
    private Integer age;

    @NotBlank(message = "Email Cannot be Blank")
    @Email(message = "Email should be Valid")
    private String email;
    private Status status=Status.ACTIVE;



    public static StudentEntity toEntity(StudentDto studentDto){
        return  StudentEntity.builder()
                .id(studentDto.getId())
                .name(studentDto.getName())
                .age(studentDto.getAge())
                .email(studentDto.getEmail())
                .status(studentDto.getStatus())
                .build();
    }

    public static StudentDto toDto(StudentEntity entity){
        return  StudentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .build();
    }
}
