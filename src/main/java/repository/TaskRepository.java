package repository;

import model.StudyTask;
import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends GenericRepository<Task> {
    private static TaskRepository instance;

    private TaskRepository() {}

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    @Override
    public void create(Task task) {
        String sql = """
                INSERT INTO tasks(id, title, task_type, student_id, course_id)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, task.getId());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getTaskType());
            statement.setNull(4, Types.INTEGER);
            statement.setNull(5, Types.INTEGER);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create task error: " + e.getMessage());
        }
    }

    @Override
    public Task read(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new StudyTask(resultSet.getInt("id"), resultSet.getString("title"));
            }

        } catch (SQLException e) {
            System.out.println("Read task error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Task> readAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                tasks.add(new StudyTask(resultSet.getInt("id"), resultSet.getString("title")));
            }

        } catch (SQLException e) {
            System.out.println("Read all tasks error: " + e.getMessage());
        }

        return tasks;
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE tasks SET title = ?, task_type = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getTaskType());
            statement.setInt(3, task.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update task error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Delete task error: " + e.getMessage());
        }
    }
}