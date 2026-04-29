package service;

import exception.CourseNotFoundException;
import model.*;

import java.util.*;

public class ELearningService {

    private final List<Course> courses = new ArrayList<>();
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> courseCategories = new HashSet<>();

    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final Map<Integer, Progress> studentProgress = new HashMap<>();

    // 1. Add user
    public void addUser(User user) { users.put(user.getId(), user); }

    // 2. Remove user
    public void removeUser(int id) { users.remove(id); }

    // 3. Add course + category (using Set)
    public void addCourse(Course course, String category) {
        courses.add(course);
        courseCategories.add(category);
    }

    // 4. Remove course
    public void removeCourse(String title) {
        courses.removeIf(c -> c.getTitle().equals(title));
    }

    // Find course or throw custom exception
    public Course findCourse(String title) {
        for (Course c : courses) {
            if (c.getTitle().equals(title)) {
                return c;
            }
        }
        throw new CourseNotFoundException("Course '" + title + "' was not found in the system!");
    }

    // 5. Enroll student in course
    public void enrollStudent(int studentId, String courseTitle) {
        User user = users.get(studentId);
        if (user instanceof Student) {
            Course course = findCourse(courseTitle);
            enrollments.add(new Enrollment((Student) user, course));
            studentProgress.putIfAbsent(user.getId(), new Progress((Student) user));
        }
    }

    // 6. Add lesson
    public void addLesson(String courseTitle, Lesson lesson) { findCourse(courseTitle).addLesson(lesson); }

    // 7. Remove lesson
    public void removeLesson(String courseTitle, Lesson lesson) { findCourse(courseTitle).removeLesson(lesson); }

    // 8. Create quiz
    public void createQuiz(String courseTitle, Quiz quiz) { findCourse(courseTitle).addQuiz(quiz); }

    // 9. Solve quiz
    public void solveQuiz(int studentId, String courseTitle, String quizTitle) {
        System.out.println("Student with ID " + studentId + " submitted the solution for " + quizTitle + " in course " + courseTitle);
    }

    // 10. Calculate quiz score
    public void calculateQuizScore(int studentId, int points) {
        Progress p = studentProgress.get(studentId);
        if (p != null) p.addScore(points);
    }

    // 11. Add task
    public void addTask(Task task) {
        tasks.add(task);
    }

    // 12. Remove task
    public void removeTask(int id) {
        tasks.removeIf(t -> t.getId() == id);
    }

    // 13. View courses
    public void viewCourses() {
        for (Course c : courses) {
            System.out.println("- " + c.getTitle());
        }
    }

    // 14. View student progress
    public void viewStudentProgress(int studentId) {
        Progress p = studentProgress.get(studentId);
        if (p != null) p.showProgress(); // Here we use polymorphism through the interface
    }

    // 15. Sort courses by title
    public void sortCourses() {
        courses.sort(Comparator.comparing(Course::getTitle));
        System.out.println("Courses have been sorted alphabetically.");
    }
    public void showCategories() {
        System.out.println("Categories: " + courseCategories);
    }
}
