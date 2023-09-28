package org.example.service;

import org.example.model.Associate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AssociateService {
    void addAssociate(Associate associate);
    void editAssociate(int associateId, Associate updatedAssociate);
    boolean deleteAssociate(int associateId);
    List<Associate> listAssociates(SkillService skillService);
    Associate findAssociateById(int associateId, SkillService skillService);
     List<Associate> searchAssociates(String criteria, SkillService skillService);
    void importAccounts();

    void exportAccounts(SkillService skillService);

    int getTotalAssociates(SkillService skillService);

    // Total No of Associates who has more than N skills
    int getTotalAssociatesWithSkills(int n, SkillService skillService);

    // List of Associate IDs who has more than N skills
    List<Integer> getAssociateIdsWithSkills(int n, SkillService skillService);

    // Total No of Associates who has given set of skills
    int getTotalAssociatesWithSkills(Set<String> skills, SkillService skillService);

    // Associate wise Skill count - AssociateName, Primary, Secondary
    Map<String, Map<String, Integer>> getAssociateWiseSkillCount(SkillService skillService);

    // BU wise Associate count
    Map<String, Integer> getBuWiseAssociateCount(SkillService skillService);

    // Location wise Skill count
    Map<String, Map<String, Integer>> getLocationWiseSkillCount(SkillService skillService);

    // Skill wise Associate count
    Map<String, Integer> getSkillWiseAssociateCount(SkillService skillService);
    // Top N skills (Primary vs Secondary)
    List<String> getTopNSkills(int n, SkillService skillService);

    // Skill wise Avg Associate Experience
    Map<String, Double> getSkillWiseAvgAssociateExperience(SkillService skillService);
}
