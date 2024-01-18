package com.ensa.tests.services;

import com.ensa.tests.dtos.StudentDto;
import com.ensa.tests.entities.Student;
import com.ensa.tests.repos.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;

    // Method to add a student
    public Student addStudent(StudentDto studentDto) throws BadRequestException {
        // Add any validation or business logic here if needed
        Student student= Student.builder()
                .isActive(studentDto.getIsActive())
                .age(studentDto.getAge())
                .username(studentDto.getUsername())
                .email(studentDto.getEmail())
                .build();
        if(this.doesEmailAlreadyExist(studentDto.getEmail())) throw new BadRequestException("This " +
                "email has already been taken");
        studentRepo.saveAndFlush(student);
        return student;
    }

    // Method to get a student by ID
    public Optional<Student> getStudentById(String studentId) {
        // Add any additional logic if needed
        return studentRepo.findById(studentId);
    }

    public void deleteById(String studentId) {
        studentRepo.deleteById(studentId);
    }

    // Method to get all students
    public List<Student> getAllStudents() {
        // Add any additional logic if needed
        return studentRepo.findAll();
    }

    public Boolean doesEmailAlreadyExist(String email) {
        return studentRepo.selectExistsEmail(email);
    }
}
