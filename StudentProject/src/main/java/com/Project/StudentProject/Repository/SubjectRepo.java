package com.Project.StudentProject.Repository;

import com.Project.StudentProject.Dto.SubjectDto;
import com.Project.StudentProject.Entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity,Integer> {

   List<SubjectDto> findByStudentEntity_Id(int id);
}
