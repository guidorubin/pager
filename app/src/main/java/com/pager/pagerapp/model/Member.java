package com.pager.pagerapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("github")
    @Expose
    private String github;
    @SerializedName("role")
    @Expose
    private Integer roleId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("languages")
    @Expose
    private List<String> languages = null;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("location")
    @Expose
    private String location;

    private String roleName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}