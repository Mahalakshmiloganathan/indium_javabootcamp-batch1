package org.example.model;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

public class Associate {
    private int associateid;
    private String name;
    private int age;
    private String businessUnit;
    private String email;
    private String location;
    @ManyToMany(mappedBy = "skills", cascade = CascadeType.ALL)
    private List<Skills> skills;
    private Date createTime;
    private Date updateTime;

    public Associate(int associateId, String name, List<Skills> skills) {
        this.associateid = associateId;
        this.name = name;
        this.skills = skills;
    }


    public int getId() {
        return associateid;
    }

    public void setId(int associateid) {
        this.associateid = associateid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Associate(int associateid, String name, int age, String businessUnit, String email, String location, List<Skills> skills) {
        this.associateid = associateid;
        this.name = name;
        this.age = age;
        this.businessUnit = businessUnit;
        this.email = email;
        this.location = location;
        this.skills = skills;
    }




    @Override
    public String toString() {
        return "Associate ID: " + associateid +
                "\nAssociate Name: " + name +
                "\nAssociate Age: " + age +
                "\nAssociate Bussiness Unit: " + businessUnit +
                "\nAssociate Email: " + email +
                "\nAssociate Location: " + location +
                "\nAssociate Skills: " + skills;
    }

}
