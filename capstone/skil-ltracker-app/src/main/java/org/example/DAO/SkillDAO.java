package org.example.DAO;

import org.example.model.Skills;

import java.util.List;

public interface SkillDAO {

    void addSkill(Skills skill);

    List<Skills> listSkills();

    Skills findSkillById(int skillId);

    void updateSkill(Skills skill);

    boolean deleteSkill(int skillId);
}
