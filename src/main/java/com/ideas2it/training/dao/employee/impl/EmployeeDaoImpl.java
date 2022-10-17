package com.ideas2it.training.dao.employee.impl;

import com.ideas2it.training.dao.employee.EmployeeDao;
import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Project;
import com.ideas2it.training.utils.UtilDateTime;
import com.ideas2it.training.utils.hibernateUtil.HibernateSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {



    private UtilDateTime utilDate = new UtilDateTime();

    @Override
    public boolean addEmployee(Employee employee)  {
    	Session session = null;
	    boolean isAdded = false;
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
	        session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            employee.setCreatedAt(utilDate.getCurDateTime());
	        employee.setModifiedAt(utilDate.getCurDateTime());
	        session.save(employee);
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
	    } finally {
            if(session!=null)
	            session.close();
        }
	return isAdded;
    }
			
    @Override
    public List<Employee> getEmployees() {
    	Session session = null;
        List<Employee> allEmployees= new ArrayList<>();
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
	        session = factory.openSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Employee",Employee.class);
            allEmployees = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if(session!=null)
	            session.close();
        }  
        System.out.println(allEmployees);
            return allEmployees;
    
    }

    @Override
    public Employee getEmployee(String employeeId) {
    	Session session = null;
        Employee employee = null;
        try {
            session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
	        Query query = session.createQuery("FROM Employee where isDeleted = false AND employeeId =:employeeId",Employee.class);
	        query.setParameter("employeeId",employeeId);
	        employee = (Employee) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if(session!=null)
	            session.close();
        }
        return employee;
    }

    public Employee getEmployeeLeaves(String employeeId) {
    	Session session = null;
        Employee employee = null;
	    List<Object[]> listResult = new ArrayList<>();
        List<LeaveRecords> leaves = new ArrayList<>();
        try {
	        session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
	        String employeeInfo =  "from Employee e "
                                  +"inner join LeaveRecords l ON l.employee.employeeId = :employeeId "
                                  +"where e.employeeId = :employeeId";
            Query query = session.createQuery(employeeInfo);
            query.setParameter("employeeId",employeeId);
            listResult = query.getResultList();
	        if (listResult.size()>0);
                Object[] arr1 = (Object[]) listResult.get(0);
                employee = (Employee) arr1[0];
                for(int i=0; i<listResult.size();i++){
                    Object[] arr = (Object[]) listResult.get(i);
                    LeaveRecords leave = null;
                    if(arr[1] != null){
                        leave = (LeaveRecords)arr[1];
                        if (!leaves.contains(leave))
                            leaves.add(leave);
                    }
                }
	            employee.setLeaves(leaves);
            System.out.println(employee.getLeaves());
                transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if(session!=null)
	            session.close();
        }
        return employee;
    }
    
    public Employee getEmployeeProjects(String employeeId) {
    	Session session = null;
        Employee employee = null;
        List<Object[]> listResult = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        try {
	        session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
	        String employeeInfo = "from Employee e "
				  +"left join Project p ON p.projectId IN " 
				  +"(select projectId from EmployeeProject where employeeId = :employeeId) "
                                  +"where e.employeeId = :employeeId";
            Query query = session.createQuery(employeeInfo);
            query.setParameter("employeeId",employeeId);
            listResult = query.getResultList();
	        System.out.println(listResult.size());
	        if (listResult.size()>0);
            	Object[] arr1 = (Object[]) listResult.get(0);
	    	    employee = (Employee) arr1[0];
		        for(int i=0; i<listResult.size();i++){
		            Object[] arr = (Object[]) listResult.get(i);
		            Project project= null;
		            LeaveRecords leave = null;
		            if(arr[1] !=null ){
		                project = (Project) arr[1];
		                if (!projects.contains(project))
			                projects.add(project);
                    }
                } 
	
	        employee.setProjects(projects);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
	    return employee;
        } 
    }


    @Override
    public String getLastEmployee() {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        String lastId = null;
	Employee employee = null;
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Employee ORDER BY employeeId DESC",Employee.class).setMaxResults(1);
	    employee = (Employee) query.uniqueResult();
	    //List<Employee> employee = query.getResultList();
	    
            lastId = employee.getEmployeeId();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        } 
        return lastId; 
    }


    @Override
    public boolean updateEmployee(Employee employee) {
    	Session session = null;
	    boolean isAdded = false;
        try {
	        session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
            employee.setModifiedAt(utilDate.getCurDateTime());
            session.update(employee);
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return isAdded;
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
    	Session session = null;
	    boolean isAdded = false;
        try {
	        session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
	        employee.setIsDeleted(true);
	        employee.setModifiedAt(utilDate.getCurDateTime());
	        session.update(employee);
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
	return isAdded;
    }   

    public boolean assignProject(String employeeId,int projectId) {
    	Session session = null;
	boolean isAdded = false;
	Employee employee = null;
	Project project = null;
        try {
            session = HibernateSession.getSession();
            Transaction transaction = session.beginTransaction();
	        employee = (Employee) session.get(Employee.class, employeeId);
            //Query query = session.createQuery("FROM Project where P_IS_DELETED = false AND P_ID = "+projectId,Project.class);
            List<Project> projects = employee.getProjects();
            //List<Project> projects =  session.get(Project.class, projectId);
            project = (Project) session.get(Project.class, projectId);
            projects.add(project);
            employee.setProjects(projects);
            session.saveOrUpdate(employee);
            isAdded = true;
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    
    }

}

     

