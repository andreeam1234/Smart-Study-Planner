package repository;

import model.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizRepository extends GenericRepository<Quiz> {
    private static QuizRepository instance;

    private QuizRepository() {}

    public static QuizRepository getInstance() {
        if (instance == null) {
            instance = new QuizRepository();
        }
        return instance;
    }

    @Override
    public void create(Quiz quiz) {
        create(quiz, 101);
    }

    public void create(Quiz quiz, int courseId) {
        String sql = """
                INSERT INTO quizzes(id, title, quiz_type, max_score, course_id)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quiz.getId());
            statement.setString(2, quiz.getTitle());
            statement.setString(3, quiz.getQuizType());
            statement.setInt(4, quiz.getMaxScore());
            statement.setInt(5, courseId);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create quiz error: " + e.getMessage());
        }
    }

    @Override
    public Quiz read(int id) {
        String sql = "SELECT * FROM quizzes WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Quiz(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("max_score")
                );
            }

        } catch (SQLException e) {
            System.out.println("Read quiz error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Quiz> readAll() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                quizzes.add(new Quiz(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("max_score")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Read all quizzes error: " + e.getMessage());
        }

        return quizzes;
    }

    @Override
    public void update(Quiz quiz) {
        String sql = "UPDATE quizzes SET title = ?, quiz_type = ?, max_score = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getQuizType());
            statement.setInt(3, quiz.getMaxScore());
            statement.setInt(4, quiz.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update quiz error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM quizzes WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Delete quiz error: " + e.getMessage());
        }
    }
}