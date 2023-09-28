package org.example.service;


import org.example.DAO.SkillDAOImpl;
import org.example.model.Skills;

import java.util.List;

public class SkillServiceImpl implements SkillService {
    private SkillDAOImpl skillDAO;

    public SkillServiceImpl(SkillDAOImpl skillDAO) {
        this.skillDAO = skillDAO;
    }

    @Override
    public void addSkill(Skills skill) {
        skillDAO.addSkill(skill);
    }

    @Override
    public List<Skills> listSkills() {
        return skillDAO.listSkills();
    }

    @Override
    public Skills findSkillById(int skillId) {
        return skillDAO.findSkillById(skillId);
    }


    @Override
    public void updateSkill(int skillId, Skills skill) {
        skillDAO.updateSkill(skill);

    }

    @Override
    public boolean deleteSkill(int skillId) {
        return skillDAO.deleteSkill(skillId);

    }




}
