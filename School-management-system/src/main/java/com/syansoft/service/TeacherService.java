package com.syansoft.service;

import com.syansoft.entity.Student;
import com.syansoft.entity.Teacher;

import java.util.List;

public interface TeacherService {

    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Long id);
    Teacher createTeacher(Teacher teacher);
    Teacher updateTeacher(Long id, Teacher teacher);
    boolean deleteTeacher(Long id);
  //  Student createStudent(Student student, Long teacherId);
}
