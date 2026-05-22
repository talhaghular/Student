package com.Project.StudentProject.Controller;

import com.Project.StudentProject.Dto.EnrollmentDto;
import com.Project.StudentProject.Responce.ResponceModel;
import com.Project.StudentProject.Service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CourseEnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/post")
    public ResponceModel insertEnrollment(@RequestBody @Valid EnrollmentDto enrollmentDto){
        return enrollmentService.insertEnrollment(enrollmentDto);
    }

    @GetMapping("/getAllEnrollments")
    public ResponceModel getAllEnrollments(@RequestParam int pageNo,
                                           @RequestParam int pageSize){
        return enrollmentService.getAllEnrollment(pageNo,pageSize);
    }

    @PutMapping("/updateEnrollment/{enrollmentId}")
    public ResponceModel updateEnrollment(@PathVariable int enrollmentId,
                                          @RequestBody EnrollmentDto enrollmentDto){
        return enrollmentService.updateEnrollment(enrollmentId,enrollmentDto);
    }

    @DeleteMapping("/deleteEnrollment/{enrollmentId}")
    public ResponceModel deleteEnrollment(@PathVariable int enrollmentId){
        return enrollmentService.deleteEnrollment(enrollmentId);
    }
}
