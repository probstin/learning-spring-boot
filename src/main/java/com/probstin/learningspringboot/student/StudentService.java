package com.probstin.learningspringboot.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Optional<Student> studentByEmail =
                studentRepository.findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("This email is already taken");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);

        if (!studentExists) {
            throw new IllegalStateException("Could not find student with ID: " + studentId);
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Could not find student with ID: " + studentId));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);

            if (studentByEmail.isPresent()) {
                throw new IllegalStateException("This email is already taken");
            }

            student.setEmail(email);
        }
    }
}
