import db.DatabaseInitializer;
import exception.CourseNotFoundException;
import factory.UserFactory;
import model.*;
import service.ELearningService;
import repository.*;

public class Main {

    public static void main(String[] args) {

        // Initialize database and create tables
        DatabaseInitializer.initialize();

        ELearningService service = new ELearningService();

        // Factory pattern — create users based on role
        User student = UserFactory.createUser("student", 1, "Ana Popescu");
        User instructor = UserFactory.createUser("instructor", 2, "Prof. Ionescu");

        service.addUser(student);
        service.addUser(instructor);

        // Builder pattern — build courses
        Course javaCourse = new Course.Builder()
                .id(101)
                .title("Java OOP")
                .category("IT")
                .instructor((Instructor) instructor)
                .addLesson(new Lesson(1, "Classes and Objects"))
                .addQuiz(new Quiz(1, "OOP Basics Test", 10))
                .build();

        Course webCourse = new Course.Builder()
                .id(102)
                .title("Web Development")
                .category("IT")
                .build();

        service.addCourse(javaCourse, "IT");
        service.addCourse(webCourse, "IT");

        // Add different lesson types
        service.addLesson("Java OOP", new Lesson(2, "Inheritance"));
        service.addLesson("Java OOP", new VideoLesson(3, "OOP Video Lesson", 45));
        service.addLesson("Java OOP", new ReadingLesson(4, "OOP Reading Material", 12));

        // Add different quiz types
        service.createQuiz("Java OOP", new Quiz(2, "Advanced OOP Quiz", 10));
        service.createQuiz("Java OOP", new MultipleChoiceQuiz("Inheritance Test", 10, 5));
        service.createQuiz("Java OOP", new WrittenQuiz("OOP Essay", 20, 500));

        // Enroll student in courses
        service.enrollStudent(student.getId(), javaCourse.getTitle());
        service.enrollStudent(student.getId(), webCourse.getTitle());

        // Solve quiz and calculate score
        service.solveQuiz(student.getId(), javaCourse.getTitle(), "OOP Basics Test");
        service.calculateQuizScore(student.getId(), 9);

        // Add different task types
        service.addTask(new StudyTask(1, "Learn inheritance"));
        service.addTask(new StudyTask(2, "Practice JDBC"));
        service.addTask(new QuizTask(3, "Solve inheritance quiz", 1));
        service.addTask(new RevisionTask(4, "Revise polymorphism", "Polymorphism"));

        // Display all tasks with their type
        System.out.println("All tasks:");
        for (Task task : service.getTasks()) {
            System.out.println("  - " + task.getTitle() + " [" + task.getTaskType() + "]");
        }

        // Display courses taught by instructor
        Instructor prof = (Instructor) instructor;
        System.out.println("\nCourses taught by " + prof.getName() + ":");
        for (Course c : prof.getTeachingCourses()) {
            System.out.println("  - " + c.getTitle());
        }

        // Display courses enrolled by student
        Student ana = (Student) student;
        System.out.println("\nCourses enrolled by " + ana.getName() + ":");
        for (Course c : ana.getEnrolledCourses()) {
            System.out.println("  - " + c.getTitle());
        }

        // Display sorted courses — TreeSet sorted alphabetically
        System.out.println("\nSorted courses:");
        for (Course c : service.getSortedCourses()) {
            System.out.println("  - " + c.getTitle());
        }

        // Display categories — HashSet, no duplicates
        service.showCategories();

        // View student progress — Trackable interface
        service.viewStudentProgress(student.getId());

        // JDBC — persist data to database

        UserRepository.getInstance().create(student);
        UserRepository.getInstance().create(instructor);

        CourseRepository.getInstance().create(javaCourse);
        CourseRepository.getInstance().create(webCourse);

        LessonRepository.getInstance().create(new Lesson(10, "Database Basics"));
        QuizRepository.getInstance().create(new Quiz(10, "JDBC Quiz", 10));
        TaskRepository.getInstance().create(new StudyTask(10, "Finish project"));

        Progress progress = new Progress((Student) student);
        progress.addScore(95);
        ProgressRepository.getInstance().create(progress, 101);

        // Read from database
        System.out.println("Reading from DB:");
        Course fromDb = CourseRepository.getInstance().read(101);
        if (fromDb != null) {
            System.out.println("Found: " + fromDb.getTitle() + " [" + fromDb.getCategory() + "]");
        }

        // Update course in database
        Course updated = new Course.Builder()
                .id(102)
                .title("Web Development Advanced")
                .category("IT")
                .build();
        CourseRepository.getInstance().update(updated);
        System.out.println("Course updated.");

        // Delete course from database
        CourseRepository.getInstance().delete(102);
        System.out.println("Course deleted.");

        // Custom exception — course not found
        try {
            service.findCourse("Cooking Course");
        } catch (CourseNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}