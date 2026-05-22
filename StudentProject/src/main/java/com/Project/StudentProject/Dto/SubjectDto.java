package com.Project.StudentProject.Dto;

import lombok.Data;

@Data
public class SubjectDto {

    private Integer subjectId;
    private String name;

    public SubjectDto(Integer subjectId, String name) {
        this.subjectId = subjectId;
        this.name = name;
    }
}
