package service;

import audit.AuditService;
import exception.CourseNotFoundException;
import model.*;

import java.util.*;

public class ELearningService {

    private final List<Course> courses = new ArrayList<>();
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> courseCategories = new HashSet<>();

    private final Set<Course> sortedCourses = new TreeSet<>(
            Comparator.comparing(Course::getTitle)
                    .thenComparingInt(Course::getId)
    );

    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final Map<Integer, Progress> studentProgress = new HashMap<>();

    private final AuditService auditService = AuditService.getInstance();

    // 1. Add user
    public void addUser(User user) {
        users.put(user.getId(), user);
        auditService.logAction("add_user");
    }

    // 2. Remove user
    public void removeUser(int id) {
        users.remove(id);
        auditService.logAction("remove_user");
    }

    // 3. Add course + category
    public void addCourse(Course course, String category) {
        courses.add(course);
        sortedCourses.add(course);
        courseCategories.add(category);

        if (course.getInstructor() != null) {
            course.getInstructor().addTeachingCourse(course);
        }

        auditService.logAction("add_course");
    }

    // 4. Remove course
    public void removeCourse(String title) {
        courses.removeIf(c -> c.getTitle().equalsIgnoreCase(title));
        sortedCourses.removeIf(c -> c.getTitle().equalsIgnoreCase(title));
        auditService.logAction("remove_course");
    }

    // 5. Find course or throw custom exception
    public Course findCourse(String title) {
        auditService.logAction("find_course");

        for (Course course : courses) {
            if (course.getTitle().equalsIgnoreCase(title)) {
                return course;
            }
        }

        auditService.logAction("course_not_found");
        throw new CourseNotFoundException("Course '" + title + "' was not found in the system!");
    }

    // 6. Enroll student in course
    public void enrollStudent(int studentId, String courseTitle) {
        User user = users.get(studentId);

        if (!(user instanceof Student student)) {
            auditService.logAction("enroll_student_failed");
            System.out.println("User with ID " + studentId + " is not a student or does not exist.");
            return;
        }

        Course course = findCourse(courseTitle);

        enrollments.add(new Enrollment(student, course));
        studentProgress.putIfAbsent(user.getId(), new Progress(student));

        student.enrollInCourse(course); // ADAUGA ASTA

        auditService.logAction("enroll_student");
    }

    // 7. Add lesson
    public void addLesson(String courseTitle, Lesson lesson) {
        Course course = findCourse(courseTitle);
        course.addLesson(lesson);
        auditService.logAction("add_lesson");
    }

    // 8. Remove lesson
    public void removeLesson(String courseTitle, Lesson lesson) {
        Course course = findCourse(courseTitle);
        course.removeLesson(lesson);
        auditService.logAction("remove_lesson");
    }

    // 9. Create quiz
    public void createQuiz(String courseTitle, Quiz quiz) {
        Course course = findCourse(courseTitle);
        course.addQuiz(quiz);
        auditService.logAction("create_quiz");
    }

    // 10. Solve quiz
    public void solveQuiz(int studentId, String courseTitle, String quizTitle) {
        System.out.println("Student with ID " + studentId +
                " submitted the solution for " + quizTitle +
                " in course " + courseTitle);

        auditService.logAction("solve_quiz");
    }

    // 11. Calculate quiz score
    public void calculateQuizScore(int studentId, int points) {
        Progress progress = studentProgress.get(studentId);

        if (progress != null) {
            progress.addScore(points);
            auditService.logAction("calculate_quiz_score");
        } else {
            auditService.logAction("calculate_quiz_score_failed");
            System.out.println("No progress found for student ID " + studentId);
        }
    }

    // 12. Add task
    public void addTask(Task task) {
        tasks.add(task);
        auditService.logAction("add_task");
    }

    // 13. Remove task
    public void removeTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
        auditService.logAction("remove_task");
    }

    // 14. View courses
    public void viewCourses() {
        auditService.logAction("view_courses");

        for (Course course : courses) {
            System.out.println("- " + course.getTitle());
        }
    }

    // 15. View student progress
    public void viewStudentProgress(int studentId) {
        auditService.logAction("view_student_progress");

        Progress progress = studentProgress.get(studentId);

        if (progress != null) {
            progress.showProgress();
        } else {
            System.out.println("No progress found for student ID " + studentId);
        }
    }

    // 16. Sort courses by title
    public void sortCourses() {
        courses.sort(Comparator.comparing(Course::getTitle));
        sortedCourses.clear();
        sortedCourses.addAll(courses);

        System.out.println("Courses have been sorted alphabetically.");
        auditService.logAction("sort_courses");
    }

    // 17. View categories
    public void showCategories() {
        System.out.println("Categories: " + courseCategories);
        auditService.logAction("show_categories");
    }

    // Extra methods useful for GUI / testing

    public List<Course> getCourses() {
        return courses;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Set<String> getCourseCategories() {
        return courseCategories;
    }

    public Set<Course> getSortedCourses() {
        return sortedCourses;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Map<Integer, Progress> getStudentProgress() {
        return studentProgress;
    }
}