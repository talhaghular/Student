package com.Project.StudentProject.Repository;

import com.Project.StudentProject.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment,Integer> {


    boolean existsByCourse_courseIdAndStudent_Id(int courseId,int studentId);
}
