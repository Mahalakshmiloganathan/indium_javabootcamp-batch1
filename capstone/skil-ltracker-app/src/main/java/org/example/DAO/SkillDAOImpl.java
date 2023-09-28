package org.example.DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.enums.SkillCategory;
import org.example.model.Associate;
import org.example.model.Skills;
import org.example.service.SkillService;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SkillDAOImpl implements SkillDAO {
    private SkillService skillService;

    private Connection connection;

    public SkillDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addSkill(Skills skill) {
        String sql = "INSERT INTO skills (name, description, category, experience_in_months) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setString(2, skill.getDescription());
            preparedStatement.setString(3, skill.getCategory().toString()); // Convert enum to string
            preparedStatement.setInt(4, skill.getExperienceInMonths());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                skill.setSkillid(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Skills> listSkills() {
        List<Skills> skills = new ArrayList<>();
        String sql = "SELECT * FROM skills";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category"); // Retrieve as a string
                int experienceInMonths = resultSet.getInt("experience_in_months");
                SkillCategory skillCategory = SkillCategory.valueOf(category); // Convert string to enum
                skills.add(new Skills(id, name, description, skillCategory, experienceInMonths));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public Skills findSkillById(int skillId) {
        String sql = "SELECT * FROM skills WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, skillId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String category = resultSet.getString("category"); // Retrieve as a string
                    int experienceInMonths = resultSet.getInt("experience_in_months");
                    SkillCategory skillCategory = SkillCategory.valueOf(category); // Convert string to enum
                    return new Skills(id, name, description, skillCategory, experienceInMonths);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateSkill(Skills skill) {
        String sql = "UPDATE skills SET name = ?, description = ?, category = ?, experience_in_months = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.setString(2, skill.getDescription());
            preparedStatement.setString(3, skill.getCategory().toString());
            preparedStatement.setInt(4, skill.getExperienceInMonths());
            preparedStatement.setInt(5, skill.getSkillid()); // Assuming skillId is the primary key
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteSkill(int skillId) {
        String sql = "DELETE FROM skills WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, skillId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


}


