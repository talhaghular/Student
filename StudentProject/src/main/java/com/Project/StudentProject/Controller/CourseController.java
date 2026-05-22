package com.Project.StudentProject.Controller;

import com.Project.StudentProject.Dto.CourseDto;
import com.Project.StudentProject.Entity.CourseEntity;
import com.Project.StudentProject.Responce.ResponceModel;
import com.Project.StudentProject.Service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CourseController {

    @Autowired
     CourseService courseService;

    @PostMapping("/post/{id}")
    public ResponceModel saveCourse(@PathVariable int id,@RequestBody CourseEntity courseEntity){
        return courseService.saveCource(id,courseEntity);
    }

    @GetMapping("/getCourses")
    public List<CourseEntity> getAll(){
        return courseService.getAllcourses();
    }

    //Get Courses of a Student
    @GetMapping("/coursesOfStudent/{id}")
    public ResponceModel getCourses(@PathVariable int id){
        return courseService.getCourses(id);
    }
}
