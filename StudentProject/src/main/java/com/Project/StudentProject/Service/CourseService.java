package com.Project.StudentProject.Service;

import com.Project.StudentProject.Dto.CourseDto;
import com.Project.StudentProject.Entity.CourseEntity;
import com.Project.StudentProject.Entity.StudentEntity;
import com.Project.StudentProject.Repository.CourseRepo;
import com.Project.StudentProject.Repository.StudentRepo;
import com.Project.StudentProject.Responce.ResponceModel;
import com.Project.StudentProject.exception.NotFoundExceptionResource;
import com.Project.StudentProject.util.APIMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseService {

    @Autowired
     CourseRepo courseRepo;
    @Autowired
    StudentRepo studentRepo;

    public ResponceModel saveCource(int stud_id,CourseEntity courseEntity){
            StudentEntity exist=studentRepo.findById(stud_id).orElse(null);

            if(exist == null){
                throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND);
            }
            courseEntity.setStudentEntity(exist);
            CourseEntity saved=courseRepo.save(courseEntity);
            return new ResponceModel(
                    HttpStatus.CREATED,
                    HttpStatus.CREATED.value(),
                    APIMessage.CREATED_COURSE,
                    saved
            );

    }

    public List<CourseEntity> getAllcourses(){
        return courseRepo.findAll();
    }

    public ResponceModel getCourses(int id){
        List<CourseDto> exist=courseRepo.findByStudentEntity_Id(id);

        if(exist.isEmpty()){
            throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND);
        }
        List<CourseDto> getCourceByStudent=courseRepo.findByStudentEntity_Id(id);
        return new ResponceModel(
                HttpStatus.FOUND,
                HttpStatus.FOUND.value(),
                APIMessage.COURSE_FOUND,
                getCourceByStudent
        );
    }
}
