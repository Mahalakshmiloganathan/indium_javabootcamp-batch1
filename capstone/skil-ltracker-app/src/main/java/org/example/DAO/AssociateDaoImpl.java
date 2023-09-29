package org.example.DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Associate;
import org.example.model.Skills;
import org.example.service.SkillService;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class AssociateDaoImpl implements AssociateDao {
    private Connection connection;

    public AssociateDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addAssociate(Associate associate) {
        String sql = "INSERT INTO associates (name, age, businessunit, email, location, skills) VALUES (?, ?, ?, ?, ?, ?::jsonb)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, associate.getName());
            preparedStatement.setInt(2, associate.getAge());
            preparedStatement.setString(3, associate.getBusinessUnit());
            preparedStatement.setString(4, associate.getEmail());
            preparedStatement.setString(5, associate.getLocation());

            List<Skills> skillsList = associate.getSkills();
            String skillsAsJsonArray = new ObjectMapper().writeValueAsString(skillsList);

            preparedStatement.setString(6, skillsAsJsonArray);

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                associate.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Error Occured While Create Associate");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void editAssociate(int associateId, Associate updatedAssociate) {
        String updateAssociateSQL = "UPDATE associates SET name = ?, age = ?, businessUnit = ?, email = ?, location = ?, skills = ?, update_time = ? WHERE associateid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateAssociateSQL)) {
            preparedStatement.setString(1, updatedAssociate.getName());
            preparedStatement.setInt(2, updatedAssociate.getAge());
            preparedStatement.setString(3, updatedAssociate.getBusinessUnit());
            preparedStatement.setString(4, updatedAssociate.getEmail());
            preparedStatement.setString(5, updatedAssociate.getLocation());
            List<Skills> skills = updatedAssociate.getSkills();

            ObjectMapper objectMapper = new ObjectMapper();
            String skillsJson = objectMapper.writeValueAsString(skills);

            preparedStatement.setObject(6, skillsJson, Types.OTHER);
            Date utilDate = updatedAssociate.getUpdateTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            preparedStatement.setDate(7, sqlDate);
            preparedStatement.setInt(8, associateId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Occured While Update Associate");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteAssociate(int associateId) {
        String sql = "DELETE FROM associates WHERE associateid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, associateId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Occured While Delete Associate");
        }
    }

    @Override
    public List<Associate> listAssociates(SkillService skillService) {
        List<Associate> associates = new ArrayList<>();
        String sql = "SELECT * FROM associates";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int associateid = resultSet.getInt("associateid");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String businessUnit = resultSet.getString("businessUnit");
                String email = resultSet.getString("email");
                String location = resultSet.getString("location");


                String skillsAsJsonArray = resultSet.getString("skills");

                ObjectMapper objectMapper = new ObjectMapper();
                List<Skills> selectedSkills = new ArrayList<>();

                JsonNode skillsJson = objectMapper.readTree(skillsAsJsonArray);
                if (skillsJson.isArray()) {
                    for (JsonNode skillNode : skillsJson) {
                        int skillId = skillNode.get("skillid").asInt();
                        Skills skill = skillService.findSkillById(skillId);
                        if (skill != null) {
                            selectedSkills.add(skill);
                        }
                    }
                }

                associates.add(new Associate(associateid, name, age, businessUnit, email, location, selectedSkills));
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error Occured While Get All Associate");
        }
        return associates;
    }

    @Override
    public Associate findAssociateById(int associateId, SkillService skillService) {
        String sql = "SELECT * FROM associates WHERE associateid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, associateId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int associateid = resultSet.getInt("associateid");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String businessUnit = resultSet.getString("businessUnit");
                    String email = resultSet.getString("email");
                    String location = resultSet.getString("location");
                    String skillsAsJsonArray = resultSet.getString("skills");

                    // Parse the JSON array string to a list of Skills objects
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Skills> selectedSkills = new ArrayList<>();

                    JsonNode skillsJson = objectMapper.readTree(skillsAsJsonArray);
                    if (skillsJson.isArray()) {
                        for (JsonNode skillNode : skillsJson) {
                            int skillId = skillNode.get("skillid").asInt();
                            Skills skill = skillService.findSkillById(skillId);
                            if (skill != null) {
                                selectedSkills.add(skill);
                            }
                        }
                    }

                    return new Associate(associateid, name, age, businessUnit, email, location, selectedSkills);
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error Occured While Get Associate By Id");
        }
        return null;
    }


    @Override

    public List<Associate> searchAssociates(String criteria, SkillService skillService) {
        List<Associate> associates = new ArrayList<>();
        String sql = "SELECT * FROM associates WHERE (associateid = ? OR name LIKE ? OR email LIKE ? OR location LIKE ? OR EXISTS (SELECT 1 FROM jsonb_array_elements_text(skills) skill WHERE skill ILIKE ?))";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int associateId = 0; // Default value in case criteria is not a valid integer
            boolean isNumeric = false;

            try {

                associateId = Integer.parseInt(criteria);
                isNumeric = true;
            } catch (NumberFormatException e) {
                System.out.println("Error Occured While Search Associate");
            }

            if (isNumeric) {
                preparedStatement.setInt(1, associateId);
                preparedStatement.setString(2, ""); // Empty string for non-text criteria
            } else {
                preparedStatement.setInt(1, 0); // Set ID to 0 for text criteria
                preparedStatement.setString(2, "%" + criteria + "%");
            }

            preparedStatement.setString(3, "%" + criteria + "%");
            preparedStatement.setString(4, "%" + criteria + "%");
            preparedStatement.setString(5, "%" + criteria + "%");


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int associateid = resultSet.getInt("associateid"); // Update to "associateid"
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String businessUnit = resultSet.getString("businessUnit");
                    String email = resultSet.getString("email");
                    String location = resultSet.getString("location");

                    String skillsAsJsonArray = resultSet.getString("skills");

                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Skills> selectedSkills = new ArrayList<>();

                    JsonNode skillsJson = objectMapper.readTree(skillsAsJsonArray);
                    if (skillsJson.isArray()) {
                        for (JsonNode skillNode : skillsJson) {
                            int skillId = skillNode.get("skillid").asInt();
                            Skills skill = skillService.findSkillById(skillId);
                            if (skill != null) {
                                selectedSkills.add(skill);
                            }
                        }
                    }

                    associates.add(new Associate(associateid, name, age, businessUnit, email, location, selectedSkills));
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error Occured While Search Associate");
        }
        return associates;
    }


}
