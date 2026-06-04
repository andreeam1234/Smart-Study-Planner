package db;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() {
        String[] sqlStatements = {
                """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    role VARCHAR(30) NOT NULL
                );
                """,

                """
                CREATE TABLE IF NOT EXISTS courses (
                    id INTEGER PRIMARY KEY,
                    title VARCHAR(100) NOT NULL,
                    category VARCHAR(50) NOT NULL,
                    instructor_id INTEGER,
                    FOREIGN KEY (instructor_id) REFERENCES users(id)
                );
                """,

                """
                CREATE TABLE IF NOT EXISTS lessons (
                    id INTEGER PRIMARY KEY,
                    title VARCHAR(100) NOT NULL,
                    lesson_type VARCHAR(50) NOT NULL,
                    course_id INTEGER NOT NULL,
                    FOREIGN KEY (course_id) REFERENCES courses(id)
                );
                """,

                """
                CREATE TABLE IF NOT EXISTS quizzes (
                    id INTEGER PRIMARY KEY,
                    title VARCHAR(100) NOT NULL,
                    quiz_type VARCHAR(50) NOT NULL,
                    max_score INTEGER NOT NULL,
                    course_id INTEGER NOT NULL,
                    FOREIGN KEY (course_id) REFERENCES courses(id)
                );
                """,

                """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY,
                    title VARCHAR(100) NOT NULL,
                    task_type VARCHAR(50) NOT NULL,
                    student_id INTEGER,
                    course_id INTEGER,
                    FOREIGN KEY (student_id) REFERENCES users(id),
                    FOREIGN KEY (course_id) REFERENCES courses(id)
                );
                """,

                """
                CREATE TABLE IF NOT EXISTS enrollments (
                    id SERIAL PRIMARY KEY,
                    student_id INTEGER NOT NULL,
                    course_id INTEGER NOT NULL,
                    enrollment_date DATE NOT NULL,
                    FOREIGN KEY (student_id) REFERENCES users(id),
                    FOREIGN KEY (course_id) REFERENCES courses(id),
                    UNIQUE(student_id, course_id)
                );
                """,

                """
                CREATE TABLE IF NOT EXISTS progress (
                    id SERIAL PRIMARY KEY,
                    student_id INTEGER NOT NULL,
                    course_id INTEGER NOT NULL,
                    score INTEGER NOT NULL,
                    FOREIGN KEY (student_id) REFERENCES users(id),
                    FOREIGN KEY (course_id) REFERENCES courses(id),
                    UNIQUE(student_id, course_id)
                );
                """
        };

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            for (String sql : sqlStatements) {
                statement.execute(sql);
            }

            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.out.println("Database initialization error: " + e.getMessage());
        }
    }
}