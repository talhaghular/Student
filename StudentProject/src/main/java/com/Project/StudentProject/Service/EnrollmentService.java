package com.Project.StudentProject.Service;

import com.Project.StudentProject.Dto.EnrollmentDto;
import com.Project.StudentProject.Entity.CourseEntity;
import com.Project.StudentProject.Entity.Enrollment;
import com.Project.StudentProject.Entity.StudentEntity;
import com.Project.StudentProject.Repository.CourseRepo;
import com.Project.StudentProject.Repository.EnrollmentRepo;
import com.Project.StudentProject.Repository.StudentRepo;
import com.Project.StudentProject.Responce.ResponceModel;
import com.Project.StudentProject.exception.DuplicateExceptionResource;
import com.Project.StudentProject.exception.MaxLimitExceptionResource;
import com.Project.StudentProject.exception.NotFoundExceptionResource;
import com.Project.StudentProject.util.APIMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepo enrollmentRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;

    //store in memory -> if exception is not found then -> store in DB
    @Transactional(rollbackOn = Exception.class)
    public ResponceModel insertEnrollment(EnrollmentDto enrollmentDto){
        //1. is the courseId is valid
        //2. is the student Id is valid
        //3. is the course reach the max limit

//        Optional<CourseEntity> courseEntity=courseRepo.findById(enrollmentDto.getCourseId());
        CourseEntity courseEntity = courseRepo.findById(enrollmentDto.getCourseId()).orElse(null);
        if (courseEntity==null){
            throw new NotFoundExceptionResource(APIMessage.COURSE_NOT_FOUND);
        } else if (courseEntity.getCurrentEnrollment() == courseEntity.getMaxEnrollment()) {
            throw new MaxLimitExceptionResource(APIMessage.MAX_LIMIT_REACHED);
        }

        StudentEntity studentEntity=studentRepo.findById(enrollmentDto.getStudentId()).orElse(null);
        if (studentEntity == null){
            throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND);
        }

        boolean enrollmentAlreadyExist=enrollmentRepo.existsByCourse_courseIdAndStudent_Id(courseEntity.getCourseId(),studentEntity.getId());
        if (enrollmentAlreadyExist){
            throw new DuplicateExceptionResource(APIMessage.ENROLLMENT_ALREADY_EXIST);
        }

        //DB Operation
        //1. course->  currentCourseEnrolment++ -> record will be updated
        //2. Enrollment table -> insert New row

        courseEntity.setCurrentEnrollment(courseEntity.getCurrentEnrollment()+ 1);
        //Update the course
        courseRepo.save(courseEntity);

//        int a=10/0;

        Enrollment enrollment=EnrollmentDto.toEntity(enrollmentDto,courseEntity,studentEntity);
        enrollmentRepo.save(enrollment);

        return new ResponceModel(
                HttpStatus.CREATED,
                HttpStatus.CREATED.value(),
                APIMessage.ENROLLMENT_CREATED,
                EnrollmentDto.toDto(enrollment)
        );
    }

    public ResponceModel getAllEnrollment(int pageNo,int pageSize){

        System.out.println("Fetching from Database...");
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);
        Page<Enrollment> enrollments=enrollmentRepo.findAll(pageable);
        List<EnrollmentDto> dtoList = enrollments.getContent()
                .stream()
                .map(EnrollmentDto::toDto)
                .toList();

        return new ResponceModel(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                APIMessage.ENROLLMENT_FETCH_SUCCESS,
                dtoList
        );
    }

    public ResponceModel updateEnrollment(int enrollmentId,EnrollmentDto enrollmentDto){

        Enrollment exists=enrollmentRepo.findById(enrollmentId).orElse(null);
        if (exists == null){
            throw new NotFoundExceptionResource(APIMessage.ENROLLMENT_NOT_FOUND);
        }else {
            if (enrollmentDto.getCourseId() != 0){
                CourseEntity course=courseRepo.findById(enrollmentDto.getCourseId()).orElse(null);
                if (course == null){
                    throw new NotFoundExceptionResource(APIMessage.COURSE_NOT_FOUND);
                }
                StudentEntity student=studentRepo.findById(enrollmentDto.getStudentId()).orElse(null);
                if (student == null){
                    throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND);
                }
                boolean checkEnrollment=enrollmentRepo.existsByCourse_courseIdAndStudent_Id(course.getCourseId(),student.getId());
                if (checkEnrollment){
                    throw new DuplicateExceptionResource(APIMessage.ENROLLMENT_ALREADY_EXIST);
                }
                exists.setCourse(course);
                exists.setStudent(student);
            }
            Enrollment saved=enrollmentRepo.save(exists);

            EnrollmentDto dto=EnrollmentDto.toDto(saved);
            return new ResponceModel(
                    HttpStatus.OK,
                    HttpStatus.OK.value(),
                    APIMessage.ENROLLMENT_UPDATED,
                    dto
            );

        }
    }

    public ResponceModel deleteEnrollment(int enrollmentId){
        Enrollment exists=enrollmentRepo.findById(enrollmentId).orElse(null);
        if (exists == null){
            throw new NotFoundExceptionResource(APIMessage.ENROLLMENT_NOT_FOUND);
        }
        else {
            enrollmentRepo.deleteById(enrollmentId);
            EnrollmentDto dto=EnrollmentDto.toDto(exists);
            return new ResponceModel(
                    HttpStatus.OK,
                    HttpStatus.OK.value(),
                    APIMessage.ENROLLMENT_DELETED,
                    dto
            );
        }
    }
}
