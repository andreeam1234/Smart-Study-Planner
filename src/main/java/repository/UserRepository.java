package repository;

import model.Instructor;
import model.Student;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends GenericRepository<User> {
    private static UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
    public void create(User user) {
        String sql = """
                INSERT INTO users(id, name, role)
                VALUES (?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user instanceof Student ? "STUDENT" : "INSTRUCTOR");

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create user error: " + e.getMessage());
        }
    }

    @Override
    public User read(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");

                if ("STUDENT".equalsIgnoreCase(role)) {
                    return new Student(resultSet.getInt("id"), resultSet.getString("name"));
                }

                return new Instructor(resultSet.getInt("id"), resultSet.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Read user error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String role = resultSet.getString("role");

                if ("STUDENT".equalsIgnoreCase(role)) {
                    users.add(new Student(resultSet.getInt("id"), resultSet.getString("name")));
                } else {
                    users.add(new Instructor(resultSet.getInt("id"), resultSet.getString("name")));
                }
            }

        } catch (SQLException e) {
            System.out.println("Read all users error: " + e.getMessage());
        }

        return users;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, role = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user instanceof Student ? "STUDENT" : "INSTRUCTOR");
            statement.setInt(3, user.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update user error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Delete user error: " + e.getMessage());
        }
    }
}