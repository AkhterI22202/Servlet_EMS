package com.ideas2it.training.dao.project;

import com.ideas2it.training.model.Project;

import java.util.List;

public interface ProjectDao {

    public boolean addProject(Project project);
    public List<Project> getProjects();
    public Project getProjectEmployees(int projectId);
    public Project getProject(int projectId);
    public boolean updateProject(Project project);
    public boolean deleteProject(Project projects);
}