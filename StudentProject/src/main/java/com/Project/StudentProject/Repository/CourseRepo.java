package com.Project.StudentProject.Repository;

import com.Project.StudentProject.Dto.CourseDto;
import com.Project.StudentProject.Entity.CourseEntity;
import com.Project.StudentProject.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<CourseEntity,Integer> {
    List<CourseDto>  findByStudentEntity_Id(int id);
}
