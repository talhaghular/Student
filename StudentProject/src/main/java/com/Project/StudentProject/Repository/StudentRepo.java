package com.Project.StudentProject.Repository;


import com.Project.StudentProject.Dto.StudentDto;
import com.Project.StudentProject.Dto.SubjectDto;
import com.Project.StudentProject.Entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<StudentEntity,Integer> {

    List<StudentEntity> findByEmail(String email);


    @Query(value = "Select id,name,email from student_entity limit ?1 offSet ?2",nativeQuery = true)
    List<StudentDto> findByPagination(int pageSize, int offset);

//    List<StudentEntity> findById(int stud_id);

    @Query("Select s from StudentEntity s where age>:age")
    List<StudentEntity> getStudentsAge(@Param("age")int age);

    @Query("Select s from StudentEntity s where s.name like %:name% and s.status=ACTIVE" )
    List<StudentEntity> searchStudentByName(@Param("name")String name);

    @Query("Select s from StudentEntity s where s.status=ACTIVE")
    List<StudentEntity> findByStatusActive();
}
