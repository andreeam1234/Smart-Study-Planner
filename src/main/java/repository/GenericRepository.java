package repository;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class GenericRepository<T> implements CrudRepository<T> {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }
}