package com.Project.StudentProject.Dto;

import com.Project.StudentProject.Entity.CourseEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CourseDto {

    private Integer courseId;
    private String courseName;

    public CourseDto(Integer courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public static CourseDto toDto(CourseEntity courseEntity){
        return CourseDto.builder()
                .courseId(courseEntity.getCourseId())
                .courseName(courseEntity.getCourseName())
                .build();
    }
}
