package controller;

import entity.Enrollment;
import entity.Student;
import service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public Enrollment enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId, @RequestParam int score) {
        return studentService.enrollStudent(studentId, courseId, score);
    }

    @GetMapping("/course/{courseName}")
    public List<Student> getStudentsByCourse(@PathVariable String courseName) {
        return studentService.getStudentsByCourseName(courseName);
    }

    @GetMapping("/not-registered/{courseName}")
    public List<Student> getStudentsNotRegisteredForCourse(@PathVariable String courseName) {
        return studentService.getStudentsNotRegisteredForCourse(courseName);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Application is running";
    }
}
