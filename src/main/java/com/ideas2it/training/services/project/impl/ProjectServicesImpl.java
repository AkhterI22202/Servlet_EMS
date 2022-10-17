package com.ideas2it.training.services.project.impl;

import com.ideas2it.training.dao.project.ProjectDao;
import com.ideas2it.training.dao.project.impl.ProjectDaoImpl;
import com.ideas2it.training.model.Project;
import com.ideas2it.training.services.project.ProjectServices;

import java.util.List;

 

public class ProjectServicesImpl implements ProjectServices {

    private ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public boolean addProject(Project project) {
        return projectDao.addProject(project);
    }

    public List<Project> getProjects() {
	return projectDao.getProjects();
    }

    public Project getProjectEmployees(int projectId) {

	return projectDao.getProjectEmployees(projectId);
    }


    public Project getProject(int projectId) {

	return projectDao.getProject(projectId);
    }

    @Override
    public boolean updateProject(Project project) {
        return projectDao.updateProject(project);
    }

    @Override
    public boolean deleteProject(Project project) {
	return projectDao.deleteProject(project);
    }

}



