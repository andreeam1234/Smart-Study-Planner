package repository;

import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenericDatabaseService {
    private static GenericDatabaseService instance;

    private GenericDatabaseService() {}

    public static GenericDatabaseService getInstance() {
        if (instance == null) {
            instance = new GenericDatabaseService();
        }
        return instance;
    }

    public int executeWrite(String sql, Object... parameters) {
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, parameters);
            return statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Generic write error: " + e.getMessage());
            return 0;
        }
    }

    public List<List<Object>> executeRead(String sql, Object... parameters) {
        List<List<Object>> rows = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(statement, parameters);

            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>();

                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getObject(i));
                    }

                    rows.add(row);
                }
            }

        } catch (SQLException e) {
            System.out.println("Generic read error: " + e.getMessage());
        }

        return rows;
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }
}