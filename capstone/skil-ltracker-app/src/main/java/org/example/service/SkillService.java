package org.example.service;

import org.example.model.Skills;

import java.util.List;


public interface SkillService {

    void addSkill(Skills skill);

    List<Skills> listSkills();

    Skills findSkillById(int skillId);

    void updateSkill(int skillId, Skills skill);

    boolean deleteSkill(int skillId);

}
