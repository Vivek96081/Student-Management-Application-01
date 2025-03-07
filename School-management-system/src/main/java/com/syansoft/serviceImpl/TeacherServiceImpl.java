package com.syansoft.serviceImpl;

import com.syansoft.entity.Student;
import com.syansoft.entity.Teacher;
import com.syansoft.entity.User;
import com.syansoft.repository.StudentRepository;
import com.syansoft.repository.TeacherRepository;
import com.syansoft.repository.UserRepository;
import com.syansoft.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        // Encode the password before saving
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

        // Create a user account for the teacher
        User user = new User();
        user.setUsername(teacher.getEmail());
        user.setPassword(teacher.getPassword());
        user.setRole("TEACHER");
        userRepository.save(user);

        // Save the teacher
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        teacher.setName(teacherDetails.getName());
        teacher.setEmail(teacherDetails.getEmail());
        teacher.setPassword(passwordEncoder.encode(teacherDetails.getPassword()));
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean deleteTeacher(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isPresent()) {
            teacherRepository.delete(teacher.get());
            return true;
        } else {
            return false;
        }
    }
}
