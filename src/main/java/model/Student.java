package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends User {
    private final List<Course> enrolledCourses = new ArrayList<>();

    public Student(int id, String name) {
        super(id, name);
    }

    public void enrollInCourse(Course course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }
}