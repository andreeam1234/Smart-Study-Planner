package repository;

import model.Progress;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgressRepository extends GenericRepository<Progress> {
    private static ProgressRepository instance;

    private ProgressRepository() {}

    public static ProgressRepository getInstance() {
        if (instance == null) {
            instance = new ProgressRepository();
        }
        return instance;
    }

    @Override
    public void create(Progress progress) {
        throw new UnsupportedOperationException("Use create(Progress, int courseId) instead.");
    }

    public void create(Progress progress, int courseId) {
        String sql = """
                INSERT INTO progress(student_id, course_id, score)
                VALUES (?, ?, ?)
                ON CONFLICT (student_id, course_id) DO NOTHING
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, progress.getStudent().getId());
            statement.setInt(2, courseId);
            statement.setInt(3, progress.getScore());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create progress error: " + e.getMessage());
        }
    }

    @Override
    public Progress read(int id) {
        String sql = "SELECT * FROM progress WHERE student_id = ? LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Progress progress = new Progress(new Student(resultSet.getInt("student_id"), "Student DB"));
                progress.addScore(resultSet.getInt("score"));
                return progress;
            }

        } catch (SQLException e) {
            System.out.println("Read progress error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Progress> readAll() {
        List<Progress> progressList = new ArrayList<>();
        String sql = "SELECT * FROM progress";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Progress progress = new Progress(new Student(resultSet.getInt("student_id"), "Student DB"));
                progress.addScore(resultSet.getInt("score"));
                progressList.add(progress);
            }

        } catch (SQLException e) {
            System.out.println("Read all progress error: " + e.getMessage());
        }

        return progressList;
    }

    @Override
    public void update(Progress progress) {
        String sql = "UPDATE progress SET score = ? WHERE student_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, progress.getScore());
            statement.setInt(2, progress.getStudent().getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update progress error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM progress WHERE student_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Delete progress error: " + e.getMessage());
        }
    }
}