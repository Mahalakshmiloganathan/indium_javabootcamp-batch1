package org.example.service;

import org.example.DAO.AssociateDAOImpl;
import org.example.enums.SkillCategory;
import org.example.model.Associate;
import org.example.model.Skills;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AssociateServiceImpl implements AssociateService{

    private Connection connection;
    private AssociateDAOImpl associateDAO;




    public AssociateServiceImpl(AssociateDAOImpl associateDAO) {
        this.associateDAO = associateDAO;
    }

    @Override
    public void addAssociate(Associate associate) {
        associateDAO.addAssociate(associate);
    }

    @Override
    public void editAssociate(int associateId, Associate updatedAssociate) {
        associateDAO.editAssociate(associateId, updatedAssociate);
    }

    @Override
    public boolean deleteAssociate(int associateId) {
        associateDAO.deleteAssociate(associateId);
        return true;
    }


    @Override
    public List<Associate> listAssociates(SkillService skillService) {
        return associateDAO.listAssociates(skillService);
    }

    @Override
    public Associate findAssociateById(int associateId, SkillService skillService) {
        return associateDAO.findAssociateById(associateId,skillService);
    }

    @Override
    public List<Associate> searchAssociates(String criteria, SkillService skillService) {
        return associateDAO.searchAssociates(criteria,skillService);
    }


    @Override
    public int getTotalAssociates(SkillService skillService) {
        List<Associate> associates = listAssociates(skillService);
        return associates.size();
    }

    @Override
    // Total No of Associates who has more than N skills
    public int getTotalAssociatesWithSkills(int n, SkillService skillService) {
        List<Associate> associates = listAssociates(skillService);
        return (int) associates.stream()
                .filter(associate -> associate.getSkills().size() >= n) // Use >= instead of >
                .count();
    }


    @Override
    // List of Associate IDs who has more than N skills
    public List<Integer> getAssociateIdsWithSkills(int n, SkillService skillService) {
        List<Associate> associates = listAssociates(skillService);
        return associates.stream()
                .filter(associate -> associate.getSkills().size() == n)
                .map(Associate::getId)
                .collect(Collectors.toList());
    }

    @Override
    // Total No of Associates who has given set of skills
    public int getTotalAssociatesWithSkills(Set<String> skills, SkillService skillService) {
        List<Associate> associates = listAssociates(skillService);
        return (int) associates.stream()
                .filter(associate -> associate.getSkills().stream()
                        .anyMatch(skill -> skills.contains(skill.getName().toLowerCase())))
                .count();
    }

    @Override
// Associate wise Skill count - AssociateName, Primary, Secondary
    public Map<String, Map<String, Integer>> getAssociateWiseSkillCount(SkillService skillService) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        List<Associate> associates = listAssociates(skillService);

        for (Associate associate : associates) {
            Map<String, Integer> skillCount = new HashMap<>();

            int primarySkillCount = (int) associate.getSkills().stream()
                    .filter(skill -> skill.getCategory() == SkillCategory.PRIMARY)
                    .count();

            int secondarySkillCount = (int) associate.getSkills().stream()
                    .filter(skill -> skill.getCategory() == SkillCategory.SECONDARY)
                    .count();


            System.out.println("Associate: " + associate.getName());
            System.out.println("Primary Skill Count: " + primarySkillCount);
            System.out.println("Secondary Skill Count: " + secondarySkillCount);

            skillCount.put("Primary", primarySkillCount);
            skillCount.put("Secondary", secondarySkillCount);

            result.put(associate.getName(), skillCount);
        }

        return result;
    }


    @Override
    // BU wise Associate count
    public Map<String, Integer> getBuWiseAssociateCount(SkillService skillService) {
        Map<String, Integer> result = new HashMap<>();
        List<Associate> associates = listAssociates(skillService);
        for (Associate associate : associates) {
            String businessUnit = associate.getBusinessUnit();
            result.put(businessUnit, result.getOrDefault(businessUnit, 0) + 1);
        }

        return result;
    }

    @Override
    // Location wise Skill count
    public Map<String, Map<String, Integer>> getLocationWiseSkillCount(SkillService skillService) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        List<Associate> associates = listAssociates(skillService);

        for (Associate associate : associates) {
            Map<String, Integer> skillCount = new HashMap<>();

            String location = associate.getLocation(); // Get the location from Associate

            for (Skills skill : associate.getSkills()) {
                skillCount.put(skill.getName(), skillCount.getOrDefault(skill.getName(), 0) + 1);
            }

            result.put(location, skillCount); // Use location as the key in the result map
        }

        return result;
    }


    @Override
    // Skill wise Associate count
    public Map<String, Integer> getSkillWiseAssociateCount(SkillService skillService) {
        Map<String, Integer> result = new HashMap<>();
        List<Associate> associates = listAssociates(skillService);
        for (Associate associate : associates) {
            for (Skills skill : associate.getSkills()) {
                String skillName = skill.getName();
                result.put(skillName, result.getOrDefault(skillName, 0) + 1);
            }
        }

        return result;
    }
    @Override
// Top N skills (Primary vs Secondary)
    public List<String> getTopNSkills(int n, SkillService skillService) {
        Map<String, Integer> skillCount = getSkillWiseAssociateCount(skillService); // Pass your SkillService here

        return skillCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
// Skill wise Avg Associate Experience
    public Map<String, Double> getSkillWiseAvgAssociateExperience(SkillService skillService) {
        Map<String, List<Integer>> skillExperiences = new HashMap<>();
        List<Associate> associates = listAssociates(skillService);

        for (Associate associate : associates) {
            for (Skills skill : associate.getSkills()) {
                String skillName = skill.getName();
                skillExperiences.computeIfAbsent(skillName, k -> new ArrayList<>()).add(skill.getExperienceInMonths());
            }
        }

        Map<String, Double> avgExperiences = new HashMap<>();

        for (Map.Entry<String, List<Integer>> entry : skillExperiences.entrySet()) {
            String skillName = entry.getKey();
            List<Integer> experiences = entry.getValue();
            double avgExperience = experiences.stream().mapToInt(Integer::intValue).average().orElse(0);
            avgExperiences.put(skillName, avgExperience);
        }

        return avgExperiences;
    }



}

