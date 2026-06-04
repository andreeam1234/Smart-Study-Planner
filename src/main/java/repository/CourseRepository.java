package repository;

import model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository extends GenericRepository<Course> {
    private static CourseRepository instance;

    private CourseRepository() {}

    public static CourseRepository getInstance() {
        if (instance == null) {
            instance = new CourseRepository();
        }
        return instance;
    }

    @Override
    public void create(Course course) {
        String sql = """
                INSERT INTO courses(id, title, category, instructor_id)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, course.getId());
            statement.setString(2, course.getTitle());
            statement.setString(3, course.getCategory());
            statement.setNull(4, Types.INTEGER);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Create course error: " + e.getMessage());
        }
    }

    @Override
    public Course read(int id) {
        String sql = "SELECT * FROM courses WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Course.Builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .category(resultSet.getString("category")) // adaugat
                        .build();
            }

        } catch (SQLException e) {
            System.out.println("Read course error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Course> readAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                courses.add(new Course.Builder()
                        .id(resultSet.getInt("id"))
                        .title(resultSet.getString("title"))
                        .category(resultSet.getString("category")) // adaugat
                        .build());
            }

        } catch (SQLException e) {
            System.out.println("Read all courses error: " + e.getMessage());
        }

        return courses;
    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE courses SET title = ?, category = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getTitle());
            statement.setString(2, course.getCategory()); // adaugat
            statement.setInt(3, course.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update course error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Delete course error: " + e.getMessage());
        }
    }
}
