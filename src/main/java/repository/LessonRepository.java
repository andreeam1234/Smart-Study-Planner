package repository;

import model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonRepository extends GenericRepository<Lesson> {
    private static LessonRepository instance;

    private LessonRepository() {}

    public static LessonRepository getInstance() {
        if (instance == null) {
            instance = new LessonRepository();
        }
        return instance;
    }

    @Override
    public void create(Lesson lesson) {
        create(lesson, 101);
    }

    public void create(Lesson lesson, int courseId) {
        String sql = """
                INSERT INTO lessons(id, title, lesson_type, course_id)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, lesson.getId());
            statement.setString(2, lesson.getTitle());
            statement.setString(3, lesson.getLessonType());
            statement.setInt(4, courseId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create lesson error: " + e.getMessage());
        }
    }

    @Override
    public Lesson read(int id) {
        String sql = "SELECT * FROM lessons WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Lesson(resultSet.getInt("id"), resultSet.getString("title"));
            }

        } catch (SQLException e) {
            System.out.println("Read lesson error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Lesson> readAll() {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                lessons.add(new Lesson(resultSet.getInt("id"), resultSet.getString("title")));
            }

        } catch (SQLException e) {
            System.out.println("Read all lessons error: " + e.getMessage());
        }

        return lessons;
    }

    @Override
    public void update(Lesson lesson) {
        String sql = "UPDATE lessons SET title = ?, lesson_type = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, lesson.getTitle());
            statement.setString(2, lesson.getLessonType());
            statement.setInt(3, lesson.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update lesson error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM lessons WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Delete lesson error: " + e.getMessage());
        }
    }
}