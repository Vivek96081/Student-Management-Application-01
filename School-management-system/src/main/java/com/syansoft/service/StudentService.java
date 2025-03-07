package com.syansoft.service;

import com.syansoft.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);
    boolean deleteStudent(Long id);
}
