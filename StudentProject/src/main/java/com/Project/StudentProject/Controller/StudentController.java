package com.Project.StudentProject.Controller;

import com.Project.StudentProject.Dto.StudentDto;
import com.Project.StudentProject.Entity.CourseEntity;
import com.Project.StudentProject.Entity.StudentEntity;
import com.Project.StudentProject.Entity.SubjectEntity;
import com.Project.StudentProject.Responce.ResponceModel;
import com.Project.StudentProject.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    //Post Data For Student
    @PostMapping
    public ResponceModel post(@Valid @RequestBody StudentDto studentDto){
        return studentService.postdata(studentDto);
    }

    //Get All Data for Student
    @GetMapping("/public")
    public ResponceModel getall(){
        return studentService.getdata();
    }

    //for update student
    @PutMapping("/{id}")
    public ResponceModel put(@PathVariable int id,@RequestBody StudentDto studentDto){
        return  studentService.updatedata(id,studentDto);
    }

    //for Delete Student
    @DeleteMapping("/{id}")
    public String  deletedata(@PathVariable int id){
        return studentService.delete(id);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponceModel getbyemail(@PathVariable String email){
        return studentService.getByEmail(email);
    }

    //Count all  the students
    @GetMapping("/count/private")
    public  ResponceModel getCount(){
        return studentService.getCount();
    }


    //Post The Subjects
    @PostMapping("/addSubjects")
    public String add(@RequestBody SubjectEntity subjectEntity){
        return studentService.addSubjects(subjectEntity);
    }

    //for connnection beetween students and subjects
    @PostMapping("/stud_id/{stud_id}/subj_id/{subj_id}")
    public String addSubjecttoStudent(@PathVariable int stud_id,@PathVariable int subj_id){
        return studentService.addSubjectToStudent(stud_id,subj_id);
    }

    //Search Subject of a Student
    @GetMapping("/getSubjectofStudent/{stud_id}")
    public String getsubject(@PathVariable int stud_id){
        return studentService.getSubjectofStudent(stud_id);
    }

    @GetMapping("/getByPagination")
    public List<StudentDto> getPagination(@RequestParam int pageNo,
                                          @RequestParam int pageSize){
        return studentService.getStudentwithPagination(pageSize,pageNo);
    }
//    @PostMapping("/add/{id}")
//    public String getsubjects(@PathVariable int id, @RequestBody SubjectEntity subjects){
//        return studentService.addSubjectToStudent(id,subjects);
//    }

    @GetMapping("/getWithPageable")
    public Page<StudentDto> getStudentWithPagable(@RequestParam int  pageNo,
                                                  @RequestParam int pageSize,
                                                  @RequestParam String sortBy,
                                                  @RequestParam String sortDir){
        return studentService.getAllWithPageble(pageNo-1,pageSize,sortBy,sortDir);
    }

    @GetMapping("/getStudentsAgeWithJpql")
    public  List<StudentDto> getStudentsAgeWithJpql(@RequestParam int age){
        return studentService.getStudentsAgeWithJpql(age);
    }

    @GetMapping("/searchStudentWithName")
    public List<StudentDto> searchStudentWithName(@RequestParam String name){
        return studentService.searchStudentWithName(name);
    }
}
