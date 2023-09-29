package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection(Connection connection) {
        this.connection = connection;
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/bankingapp", "root", "Root@123");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }
    public void createTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS BankAccount (" +
                    "account_id SERIAL PRIMARY KEY," +
                    "account_number VARCHAR(255) UNIQUE," +
                    "account_name VARCHAR(255)," +
                    "account_type VARCHAR(255)," +
                    "balance DOUBLE PRECISION)";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}