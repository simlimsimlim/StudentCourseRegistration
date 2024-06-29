package service;

import entity.Course;
import entity.Enrollment;
import entity.Student;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Transactional
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Enrollment enrollStudent(Long studentId, Long courseId, int score) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setScore(score);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourseName(String courseName) {
        return enrollmentRepository.findByCourseName(courseName).stream()
                .map(Enrollment::getStudent)
                .sorted((s1, s2) -> s1.getName().compareTo(s2.getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentsNotRegisteredForCourse(String courseName) {
        Course course = courseRepository.findByName(courseName);
        List<Student> allStudents = studentRepository.findAll();
        List<Student> registeredStudents = course.getEnrollments().stream()
                .map(Enrollment::getStudent)
                .collect(Collectors.toList());
        return allStudents.stream()
                .filter(student -> !registeredStudents.contains(student))
                .collect(Collectors.toList());
    }
}
