package org.example.model;
import org.example.enums.SkillCategory;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class Skills {
    private int skillid;
    private String name;
    private String description;
    private SkillCategory category;
    private int experienceInMonths;

    public int getSkillid() {
        return skillid;
    }

    public void setSkillid(int skillid) {
        this.skillid = skillid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    public int getExperienceInMonths() {
        return experienceInMonths;
    }

    public void setExperienceInMonths(int experienceInMonths) {
        this.experienceInMonths = experienceInMonths;
    }

    public Skills(int skillid, String name, String description, SkillCategory category, int experienceInMonths) {
        this.skillid = skillid;
        this.name = name;
        this.description = description;
        this.category = category;
        this.experienceInMonths = experienceInMonths;
    }


    @Override
    public String toString() {
        return "Skill ID: " + skillid +
                "\nSkill Name: " + name +
                "\nSkill Description: " + description +
                "\nSkill Category: " + category +
                "\nSkill Experience In Months: " + experienceInMonths;
    }
}
