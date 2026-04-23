import exception.CourseNotFoundException;
import model.*;
import service.ELearningService;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== E-Learning Platform Demo ===");

        ELearningService service = new ELearningService();

        // Users
        service.addUser(new Student(1, "Ana Popescu"));
        service.addUser(new Instructor(2, "Prof. Ionescu"));

        // Courses
        service.addCourse(new Course(102, "Web Development"), "IT");
        service.addCourse(new Course(101, "Java OOP"), "IT");

        // Lessons & Quiz
        service.addLesson("Java OOP", new Lesson(1, "Classes and Objects"));
        service.createQuiz("Java OOP", new Quiz("Recap Test", 10));

        // Enrollment + logic
        service.enrollStudent(1, "Java OOP");
        service.enrollStudent(2, "Java OOP"); // test invalid case

        service.solveQuiz(1, "Java OOP", "Recap Test");
        service.calculateQuizScore(1, 9);

        // Task test (IMPORTANT)
        service.addTask(new StudyTask(1, "Learn Streams"));
        service.addTask(new StudyTask(2, "Practice OOP"));
        service.removeTask(2);

        // Exception test
        System.out.println("\n--- Testing Exception ---");
        try {
            service.findCourse("Cooking Course");
        } catch (CourseNotFoundException e) {
            System.out.println("Error caught: " + e.getMessage());
        }

        // Sorting
        System.out.println("\n--- Courses before sorting ---");
        service.viewCourses();

        service.sortCourses();

        System.out.println("\n--- Courses after sorting ---");
        service.viewCourses();

        // Categories (Set)
        System.out.println("\n--- Categories ---");
        service.showCategories();

        // Progress
        System.out.println("\n--- Progress ---");
        service.viewStudentProgress(1);
    }
}