package com.syansoft.serviceImpl;

import com.syansoft.entity.Student;
import com.syansoft.entity.User;
import com.syansoft.repository.StudentRepository;
import com.syansoft.repository.UserRepository;
import com.syansoft.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

      @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
   @Cacheable("students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public Student createStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        User user = new User();
        user.setUsername(student.getEmail());
        user.setPassword(student.getPassword());
        user.setRole("STUDENT");
        userRepository.save(user);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setPassword(passwordEncoder.encode(studentDetails.getPassword()));
        student.setTeacher(studentDetails.getTeacher());

        return studentRepository.save(student);
    }

    @Override
    public boolean deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return true; // Successfully deleted
        } else {
            return false; // Student not found
        }
    }
}
