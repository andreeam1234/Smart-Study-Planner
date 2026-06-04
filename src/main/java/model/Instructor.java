package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Instructor extends User {
    private final List<Course> teachingCourses = new ArrayList<>();

    public Instructor(int id, String name) {
        super(id, name);
    }

    public void addTeachingCourse(Course course) {
        if (course != null && !teachingCourses.contains(course)) {
            teachingCourses.add(course);
        }
    }

    public List<Course> getTeachingCourses() {
        return Collections.unmodifiableList(teachingCourses);
    }
}