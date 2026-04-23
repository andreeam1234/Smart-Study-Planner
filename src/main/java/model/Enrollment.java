package model;

import java.time.LocalDate;

public class Enrollment {
    private Student student;
    private Course course;
    private LocalDate date;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.date = LocalDate.now();
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student.getName() +
                ", course=" + course.getTitle() +
                ", date=" + date +
                '}';
    }
}