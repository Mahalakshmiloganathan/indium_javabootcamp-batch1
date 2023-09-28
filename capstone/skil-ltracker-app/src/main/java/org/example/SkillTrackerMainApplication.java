package org.example;

import org.example.DAO.AssociateDAOImpl;
import org.example.db.DatabaseConnection;
import org.example.DAO.SkillDAOImpl;
import org.example.enums.SkillCategory;
import org.example.model.Associate;
import org.example.model.Skills;
import org.example.service.AssociateService;
import org.example.service.AssociateServiceImpl;
import org.example.service.SkillService;
import org.example.service.SkillServiceImpl;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class SkillTrackerMainApplication {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.out.println("Failed to connect to the database. Exiting...");
            return;
        }else{
            DatabaseConnection dbConnection = new DatabaseConnection(connection);
            dbConnection.initializeDatabase();
        }

        // Initialize your DAOs here
        AssociateDAOImpl associateDAO = new AssociateDAOImpl(connection);
        SkillDAOImpl skillDAO = new SkillDAOImpl(connection);

        // Initialize your services with the DAOs
        AssociateService associateService = new AssociateServiceImpl(associateDAO);
        SkillService skillService = new SkillServiceImpl(skillDAO);

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("Main Menu:");
            System.out.println("1] Add Associate");
            System.out.println("2] View All Associates");
            System.out.println("3] View Associate");
            System.out.println("4] Update Associate");
            System.out.println("5] Delete Associate");
            System.out.println("6] Search Associates");
            System.out.println("7] Add Skill");
            System.out.println("8] View All Skills");
            System.out.println("9] View Skill");
            System.out.println("10] Update Skill");
            System.out.println("11] Delete Skill");
            System.out.println("12] Key Metrics");
            System.out.println("13] Import Account");
            System.out.println("14] Export Account");
            System.out.println("15] Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createAssociate(associateService, skillService, scanner);
                    break;
                case 2:
                    viewAllAssociates(associateService,skillService);
                    break;
                case 3:
                    viewAssociate(associateService, skillService, scanner);
                    break;
                case 4:
                    updateAssociate(associateService, skillService, scanner);
                    break;
                case 5:
                    deleteAssociate(associateService, scanner);
                    break;
                case 6:
                    searchAssociates(associateService, skillService, scanner);
                    break;

                case 7:
                    createSkill(skillService, scanner);
                    break;
                case 8:
                    viewAllSkills(skillService);
                    break;
                case 9:
                    viewSkill(skillService, scanner);
                    break;
                case 10:
                    updateSkill(skillService,scanner);
                    break;
                case 11:
                    deleteSkill(skillService, scanner);
                    break;
                case 12:
                    keymetrics(associateService,skillService);
                    break;
                case 13:
                    importAccounts(associateService);
                    break;
                case 14:
                    exportAccounts(associateService,skillService);
                    break;
                case 15:
                    System.out.println("Exiting the application.");
                    exit = true;
                    scanner.close();
                    closeConnection(connection);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAssociate(AssociateService associateService, SkillService skillService, Scanner scanner) {
        System.out.print("Enter associate name: ");
        String name = scanner.nextLine();
        System.out.print("Enter associate age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter associate business unit: ");
        String businessUnit = scanner.nextLine();
        System.out.print("Enter associate email: ");
        String email = scanner.nextLine();
        System.out.print("Enter associate location: ");
        String location = scanner.nextLine();

        System.out.println("List of skills:");
        List<Skills> allSkills = skillService.listSkills();
        for (Skills skill : allSkills) {
            System.out.println("Skill ID: " + skill.getSkillid());
            System.out.println("Skill Name: " + skill.getName());
        }

        System.out.println("How many skills do you want to choose?");
        int numSkillsToChoose = scanner.nextInt();

        List<Skills> selectedSkills = new ArrayList<>();
        for (int i = 0; i < numSkillsToChoose; i++) {
            System.out.print("Enter skill ID for skill " + (i + 1) + ": ");
            int skillId = scanner.nextInt();

            // Fetch the Skills object by ID
            Skills skill = skillService.findSkillById(skillId);
            if (skill != null) {
                int Id = skill.getSkillid();
                String skillName = skill.getName();
                String description = skill.getDescription();
                SkillCategory category = skill.getCategory();
                int experienceInMonths = skill.getExperienceInMonths();

                // Create a new Skills object with only the desired attributes
                Skills selectedSkill = new Skills(Id, skillName, description, category, experienceInMonths);

                selectedSkills.add(selectedSkill);
            }

        }

        Associate associate = new Associate(0, name, age, businessUnit, email, location, selectedSkills);

        associateService.addAssociate(associate);

        System.out.println("Associate added successfully!");
    }



    private static void viewAllAssociates(AssociateService associateService, SkillService skillService) {
        List<Associate> associates = associateService.listAssociates(skillService);
        if (associates.isEmpty()) {
            System.out.println("No associates found.");
        } else {
            System.out.println("List of Associates:");
            for (Associate associate : associates) {
                System.out.println(associate);
            }
        }
    }

    private static void viewAssociate(AssociateService associateService, SkillService skillService,Scanner scanner) {
        System.out.print("Enter associate ID: ");
        int associateId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Associate associate = associateService.findAssociateById(associateId,skillService);

        if (associate != null) {
            System.out.println("Associate Details:");
            System.out.println(associate);
        } else {
            System.out.println("Associate not found.");
        }
    }

    private static void updateAssociate(AssociateService associateService, SkillService skillService, Scanner scanner) {
        System.out.print("Enter associate ID to update: ");
        int associateId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Associate existingAssociate = associateService.findAssociateById(associateId, skillService);

        if (existingAssociate != null) {
            System.out.print("Enter new associate name (or press Enter to keep the current name): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingAssociate.setName(newName);
            }

            System.out.print("Enter new associate age (or 0 to keep the current age): ");
            int newAge = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            if (newAge > 0) {
                existingAssociate.setAge(newAge);
            }

            System.out.print("Enter new associate business unit (or press Enter to keep the current unit): ");
            String newBusinessUnit = scanner.nextLine();
            if (!newBusinessUnit.isEmpty()) {
                existingAssociate.setBusinessUnit(newBusinessUnit);
            }

            System.out.print("Enter new associate email (or press Enter to keep the current email): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                existingAssociate.setEmail(newEmail);
            }

            System.out.print("Enter new associate location (or press Enter to keep the current location): ");
            String newLocation = scanner.nextLine();
            if (!newLocation.isEmpty()) {
                existingAssociate.setLocation(newLocation);
            }

            System.out.println("List of skills:");
            List<Skills> allSkills = skillService.listSkills();
            for (Skills skill : allSkills) {
                System.out.println("Skill ID: " + skill.getSkillid());
                System.out.println("Skill Name: " + skill.getName());
            }

            System.out.println("How many skills do you want to choose?");
            int numSkillsToChoose = scanner.nextInt();

            List<Skills> selectedSkills = new ArrayList<>();
            for (int i = 0; i < numSkillsToChoose; i++) {
                System.out.print("Enter skill ID for skill " + (i + 1) + ": ");
                int skillId = scanner.nextInt();

                // Fetch the Skills object by ID
                Skills skill = skillService.findSkillById(skillId);
                if (skill != null) {
                    int Id = skill.getSkillid();
                    String skillName = skill.getName();
                    String description = skill.getDescription();
                    SkillCategory category = skill.getCategory();
                    int experienceInMonths = skill.getExperienceInMonths();

                    // Create a new Skills object with only the desired attributes
                    Skills selectedSkill = new Skills(Id, skillName, description, category, experienceInMonths);

                    selectedSkills.add(selectedSkill);
                }
            }

            // Set the updated skills for the existing associate
            existingAssociate.setSkills(selectedSkills);

            Date currentDate = new Date(); // Import java.util.Date if not already imported
            existingAssociate.setUpdateTime(currentDate);
            associateService.editAssociate(associateId, existingAssociate);
            System.out.println("Associate updated successfully!");
        } else {
            System.out.println("Associate not found.");
        }
    }


    public static boolean deleteAssociate(AssociateService associateService, Scanner scanner) {
        System.out.print("Enter associate ID to delete: ");
        int associateId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        boolean success = associateService.deleteAssociate(associateId);

        if (success) {
            System.out.println("Associate deleted successfully!");
        } else {
            System.out.println("Failed to delete associate or associate not found.");
        }

        return success; // Return the result for further processing if needed
    }

    private static void searchAssociates(AssociateService associateService, SkillService skillService, Scanner scanner) {
        System.out.print("Enter the name or ID to search: ");
        String searchCriteria = scanner.next();
        List<Associate> searchResults = associateService.searchAssociates(searchCriteria, skillService);

        if (searchResults.isEmpty()) {
            System.out.println("No associates found.");
        } else {
            System.out.println("Search Results:");
            for (Associate associate : searchResults) {
                System.out.println(associate);
            }
        }
    }


    private static void createSkill(SkillService skillService, Scanner scanner) {
        System.out.print("Enter skill name: ");
        String name = scanner.nextLine();
        System.out.print("Enter skill description: ");
        String description = scanner.nextLine();
        System.out.print("Enter skill category: ");
        String categoryStr = scanner.nextLine();
        SkillCategory category = SkillCategory.valueOf(categoryStr.toUpperCase());
        System.out.print("Enter experience in months: ");
        int experienceInMonths = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Skills skill = new Skills(0,name, description, category, experienceInMonths);

        skillService.addSkill(skill);

        System.out.println("Skill added successfully!");
    }

    private static void viewAllSkills(SkillService skillService) {
        List<Skills> skills = skillService.listSkills();
        if (skills.isEmpty()) {
            System.out.println("No skills found.");
        } else {
            System.out.println("List of Skills:");
            for (Skills skill : skills) {
                System.out.println(skill);
            }
        }
    }

    private static void viewSkill(SkillService skillService, Scanner scanner) {
        System.out.print("Enter skill ID: ");
        int skillId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Skills skill = skillService.findSkillById(skillId);

        if (skill != null) {
            System.out.println("Skill Details:");
            System.out.println(skill);
        } else {
            System.out.println("Skill not found.");
        }
    }

    private static void updateSkill(SkillService skillService, Scanner scanner) {
        System.out.print("Enter skill ID to update: ");
        int skillId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Skills existingSkill = skillService.findSkillById(skillId);

        if (existingSkill != null) {
            System.out.print("Enter new skill name (or press Enter to keep the current name): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                existingSkill.setName(newName);
            }

            System.out.print("Enter new skill description (or press Enter to keep the current description): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                existingSkill.setDescription(newDescription);
            }

            System.out.print("Enter new skill category (e.g., PROGRAMMING, DESIGN, DATABASE; or press Enter to keep the current category): ");
            String newCategoryStr = scanner.nextLine();
            if (!newCategoryStr.isEmpty()) {
                SkillCategory newCategory = SkillCategory.valueOf(newCategoryStr.toUpperCase());
                existingSkill.setCategory(newCategory);
            }

            System.out.print("Enter new experience in months (or 0 to keep the current experience): ");
            int newExperienceInMonths = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            if (newExperienceInMonths > 0) {
                existingSkill.setExperienceInMonths(newExperienceInMonths);
            }

            skillService.updateSkill(skillId, existingSkill);
            System.out.println("Skill updated successfully!");
        } else {
            System.out.println("Skill not found.");
        }
    }

    private static void deleteSkill(SkillService skillService, Scanner scanner) {
        System.out.print("Enter skill ID to delete: ");
        int skillId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        boolean success = skillService.deleteSkill(skillId);

        if (success) {
            System.out.println("Skill deleted successfully!");
        } else {
            System.out.println("Skill not found.");
        }
    }




    private static void keymetrics( AssociateService associateService, SkillService skillService) {
        System.out.println("Statistics Menu:");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Statistics Menu:");
            System.out.println("1. Total Number of Associates");
            System.out.println("2. Total Number of Associates with More than N Skills");
            System.out.println("3. List of Associate IDs with More than N Skills");
            System.out.println("4. Total Number of Associates with given Set of Skills");
            System.out.println("5. Associate Wise Skill Count");
            System.out.println("6. BU Wise Associate Count");
            System.out.println("7. Location Wise Skill Count");
            System.out.println("8. Skill Wise Associate Count");
            System.out.println("9. Top N Skills");
            System.out.println("10. Skill Wise Avg Associate Experience");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                case 1:
                    displayTotalAssociates(associateService,skillService);
                    break;
                case 2:
                    displayTotalAssociatesWithSkills(associateService,skillService);
                    break;
                case 3:
                    displayAssociateIdsWithSkills(associateService,skillService);
                    break;
                case 4:
                    displayTotalAssociatesWithGivenSkills(associateService,skillService);
                    break;
                case 5:
                    displayAssociateWiseSkillCount(associateService,skillService);
                    break;
                case 6:
                    displayBuWiseAssociateCount(associateService,skillService);
                    break;
                case 7:
                    displayLocationWiseSkillCount(associateService,skillService);
                    break;
                case 8:
                    displaySkillWiseAssociateCount(associateService,skillService);
                    break;
                case 9:
                    displayTopNSkills(associateService,skillService);
                    break;
                case 10:
                    displaySkillWiseAvgAssociateExperience(associateService,skillService);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Separate methods for CRUD operations
    private static void displayTotalAssociates(AssociateService associateService, SkillService skillService) {
        int totalAssociatesCount = associateService.getTotalAssociates(skillService);
        System.out.println("Total Number of Associates: " + totalAssociatesCount);
    }

    private static void displayTotalAssociatesWithSkills(AssociateService associateService,SkillService skillService) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum number of skills: ");
        int minSkills = scanner.nextInt();
        int count = associateService.getTotalAssociatesWithSkills(minSkills, skillService);
        System.out.println("Total Number Of Associates with More than " + minSkills + " Skills: " + count);
    }

    private static void displayAssociateIdsWithSkills(AssociateService associateService, SkillService skillService) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum number of skills for Associate IDs: ");
        int n = scanner.nextInt();
        List<Integer> associateIdsWithSkills = associateService.getAssociateIdsWithSkills(n, skillService);
        if (associateIdsWithSkills.isEmpty()) {
            System.out.println("No associates found with more than " + n + " skills.");
        } else {
            System.out.println("Total Number Of Associates with More than " + n + " Skills:");
            for (Integer associateId : associateIdsWithSkills) {
                System.out.println("Associate ID: " + associateId);
            }
        }
    }

    private static void displayTotalAssociatesWithGivenSkills(AssociateService associateService, SkillService skillService) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter skills (comma-separated) for total associates: ");
        String skillsInput = scanner.next();
        Set<String> skills = new HashSet<>(Arrays.asList(skillsInput.split(",")));
        int totalAssociatesWithSkills = associateService.getTotalAssociatesWithSkills(skills, skillService);
        System.out.println("Total Number of Associates with skills " + skills + ": " + totalAssociatesWithSkills);
    }


    private static void displayAssociateWiseSkillCount(AssociateService associateService, SkillService skillService) {
        Map<String, Map<String, Integer>> associateWiseSkillCount = associateService.getAssociateWiseSkillCount(skillService);
        System.out.println("Associate-wise skill count: " + associateWiseSkillCount);
    }

    private static void displayBuWiseAssociateCount(AssociateService associateService, SkillService skillService) {
        Map<String, Integer> buWiseAssociateCount = associateService.getBuWiseAssociateCount(skillService);
        System.out.println("BU-wise associate count: " + buWiseAssociateCount);
    }

    private static void displayLocationWiseSkillCount(AssociateService associateService, SkillService skillService) {
        Map<String, Map<String, Integer>> locationWiseSkillCount = associateService.getLocationWiseSkillCount(skillService);
        System.out.println("Location-wise skill count: " + locationWiseSkillCount);
    }

    private static void displaySkillWiseAssociateCount(AssociateService associateService, SkillService skillService) {
        Map<String, Integer> skillWiseAssociateCount = associateService.getSkillWiseAssociateCount(skillService);
        System.out.println("Skill-wise associate count: " + skillWiseAssociateCount);
    }

    private static void displayTopNSkills(AssociateService associateService, SkillService skillService) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of top skills to display: ");
        int topN = scanner.nextInt();
        List<String> topNSkills = associateService.getTopNSkills(topN, skillService);
        System.out.println("Top " + topN + " skills: " + topNSkills);
    }

    private static void displaySkillWiseAvgAssociateExperience(AssociateService associateService, SkillService skillService) {
        Map<String, Double> skillWiseAvgAssociateExperience = associateService.getSkillWiseAvgAssociateExperience(skillService);
        System.out.println("Skill-wise average associate experience: " + skillWiseAvgAssociateExperience);
    }




    private static void importAccounts(AssociateService associateService) {
        associateService.importAccounts();
        System.out.println("Accounts imported successfully!");
    }

    private static void exportAccounts(AssociateService associateService, SkillService skillService) {
        associateService.exportAccounts(skillService);
        System.out.println("Accounts exported successfully!");
    }




    private static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
