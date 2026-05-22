package com.Project.StudentProject.Dto;

import com.Project.StudentProject.Entity.CourseEntity;
import com.Project.StudentProject.Entity.Enrollment;
import com.Project.StudentProject.Entity.StudentEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrollmentDto implements Serializable{

    private int enrollmentId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;

    @NotNull(message = "studentId cannot be Null!")
    private int studentId;

    @NotNull(message = "courseId cannot be Null!")
    private int courseId;

    // Entity -> DTO
    public static EnrollmentDto toDto(Enrollment enrollment){
        return EnrollmentDto.builder()
                .enrollmentId(enrollment.getEnrollmentId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .courseId(enrollment.getCourse().getCourseId())
                .studentId(enrollment.getStudent().getId())
                .build();
    }

    // DTO -> Entity
    public static Enrollment toEntity(EnrollmentDto dto,
                                          CourseEntity courseEntity,
                                          StudentEntity studentEntity){

        return Enrollment.builder()
                .enrollmentId(dto.getEnrollmentId())
                .enrollmentDate(dto.getEnrollmentDate())
                .course(courseEntity)
                .student(studentEntity)
                .build();
    }

}
