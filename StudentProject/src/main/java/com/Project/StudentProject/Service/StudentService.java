package com.Project.StudentProject.Service;

import com.Project.StudentProject.Dto.CourseDto;
import com.Project.StudentProject.Dto.StudentDto;
import com.Project.StudentProject.Dto.SubjectDto;
import com.Project.StudentProject.Entity.CourseEntity;
import com.Project.StudentProject.Entity.StudentEntity;
import com.Project.StudentProject.Entity.SubjectEntity;
import com.Project.StudentProject.Repository.CourseRepo;
import com.Project.StudentProject.Repository.StudentRepo;
import com.Project.StudentProject.Repository.SubjectRepo;
import com.Project.StudentProject.Responce.ResponceModel;
import com.Project.StudentProject.constant.Status;
import com.Project.StudentProject.exception.DuplicateExceptionResource;
import com.Project.StudentProject.exception.NotFoundExceptionResource;
import com.Project.StudentProject.util.APIMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private SubjectRepo subjectRepo;

    @Transactional(rollbackOn = Exception.class)
    public ResponceModel postdata(StudentEntity studentEntity){
        List<StudentEntity> exist=studentRepo.findByEmail(studentEntity.getEmail());
        if (exist.isEmpty()) {
            StudentEntity saved = studentRepo.save(studentEntity);
            return new ResponceModel(
                    HttpStatus.CREATED,
                    HttpStatus.CREATED.value(),
                    APIMessage.CREATED,
                    saved
            );

        }
        else {
            throw new DuplicateExceptionResource(APIMessage.STUDENT_ALREADY_PRESENT);
//            return new ResponceModel(
//                    HttpStatus.CONFLICT,
//                    HttpStatus.CONFLICT.value(),
//                    APIMessage.STUDENT_ALREADY_PRESENT,
//                    null
//            );
        }
    }

    @Cacheable(value = "students")
    public ResponceModel getdata(){
        List<StudentEntity> studententity= studentRepo.findByStatusActive();
        List<StudentDto> dtoList=studententity
                .stream()
                .map(StudentDto::toDto)
                .toList();
        System.out.println("Data Fetched From DB.");
        return new ResponceModel(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                "Fetched Successfully.",
                dtoList
        );
    }

    public ResponceModel updatedata(int  id,StudentEntity studentEntity){
       StudentEntity stud= studentRepo.findById(id).orElse(null);
       List<StudentEntity> exist=studentRepo.findByEmail(studentEntity.getEmail());

       if (exist.isEmpty()) {
           if (stud != null) {
               if (studentEntity.getName() != null) stud.setName(studentEntity.getName());
               if (studentEntity.getEmail() != null) stud.setEmail(studentEntity.getEmail());
               if (studentEntity.getAge() != null) stud.setAge(studentEntity.getAge());
               if (studentEntity.getStatus() != null) stud.setStatus(studentEntity.getStatus());
               StudentEntity updated=studentRepo.save(stud);
               return new ResponceModel(
                       HttpStatus.CREATED,
                       HttpStatus.CREATED.value(),
                       APIMessage.UPDATED,
                       updated
               );
           }
       }
        return new ResponceModel(
                HttpStatus.CONFLICT, //Conflict means duplicate
                HttpStatus.CONFLICT.value(),
                APIMessage.STUDENT_ALREADY_PRESENT,
                null
        );
    }

    public String delete(int id){
        StudentEntity st=studentRepo.findById(id).orElse(null);

        if (st != null && st.getStatus() == Status.DELETED) {
            return "Student already Deleted...";
        }
        else if (st !=null){
            st.setStatus(Status.DELETED);
            studentRepo.save(st);
        }
        else {
            return "Data Not Found!";
        }
    return "Your Data is Successfully Deleted..";
    }

    public ResponceModel getByEmail(String email) {
        List<StudentEntity> st = studentRepo.findByEmail(email);

        if (st.isEmpty()) {
            throw new NotFoundExceptionResource(APIMessage.STUDENT_NOT_FOUND);
        } else {
            return new ResponceModel(HttpStatus.FOUND,
                    HttpStatus.FOUND.value(),
                    APIMessage.STUDENT_FOUND,
                   st);
        }

    }

    public String getCount(){
        long st= studentRepo.count();
        return "Total Student is : "+st;
    }




    public String addSubjects(SubjectEntity subjectEntity){
        subjectRepo.save(subjectEntity);
        return "Data Successfully Stored..";
    }

    public String addSubjectToStudent(int stud_id,int subj_id){
        StudentEntity exist=studentRepo.findById(stud_id).orElse(null);
        SubjectEntity sc=subjectRepo.findById(subj_id).orElse(null);

        if(exist==null || sc==null){
            return "Student Or Subject Not Found!";
        }
        exist.getSubjectEntity().add(sc);
        studentRepo.save(exist);
        return "Data Successfully Stored...";
    }

    public String getSubjectofStudent(int stud_id){
        StudentEntity exist=studentRepo.findById(stud_id).orElse(null);

        if(exist==null){
            return "Student Not Found!";
        }

        List<SubjectDto> sc=subjectRepo.findByStudentEntity_Id(stud_id);
        if(sc.isEmpty()){
            return "Subject Not Found with student_id "+stud_id;
        }
        return "Subject is: "+sc;
    }


    public List<StudentDto> getStudentwithPagination(int pageSize,int pageNo){

        int offset=(pageNo  - 1)*pageSize;
        List<StudentDto> student= studentRepo.findByPagination(pageSize,offset);
        return student;
    }

    public Page<StudentDto> getAllWithPageble(int pageNo, int pageSize,String sortBy,String sortDir){

        Sort sort=sortDir.equalsIgnoreCase("ASC")? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();


//        if(sortDir.equalsIgnoreCase("ASC")){
//            Sort.by(sortBy).ascending();
//        }
//        else{
//            Sort.by(sortBy).descending();
//        }

        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<StudentEntity> page=studentRepo.findAll(pageable);
        return page.map(studentEntity ->  new StudentDto(
                studentEntity.getId(),
                studentEntity.getName(),
                studentEntity.getAge()
        ));
    }

    public List<StudentDto> getStudentsAgeWithJpql(int age){
        List<StudentEntity> studentEntityList=studentRepo.getStudentsAge(age);

        return studentEntityList.stream().map(studentEntity -> new StudentDto(
                studentEntity.getId(),
                studentEntity.getName(),
                studentEntity.getAge()
        )).toList();
    }

    public List<StudentDto> searchStudentWithName(String name){
        List<StudentEntity> s=studentRepo.searchStudentByName(name);

        return s.stream().map(studentEntity -> new StudentDto(
                studentEntity.getId(),
                studentEntity.getName(),
                studentEntity.getAge()
        )).toList();
    }
}
