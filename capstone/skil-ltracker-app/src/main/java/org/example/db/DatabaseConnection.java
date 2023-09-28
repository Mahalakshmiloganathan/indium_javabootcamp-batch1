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
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/capstone", "root", "Root@123");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void initializeDatabase() {
        createAssociateTable();
        createSkillTable();
    }

    private void createAssociateTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS associates (" +
                    "associateid SERIAL PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "age INT, " +
                    "businessunit VARCHAR(255), " +
                    "email VARCHAR(255) UNIQUE, " +
                    "location VARCHAR(255), " +
                    "skills JSONB, " + // JSONB column for storing skills
                    "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createSkillTable() {
        try {
            String createTableSQL ="CREATE TABLE IF NOT EXISTS skills (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "description TEXT, " +
                    "category VARCHAR(255), " +
                    "experience_in_months INT" +
                    ")";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
