package com.ideas2it.training.dao.leaveRecords.impl;

import com.ideas2it.training.dao.leaveRecords.LeaveRecordsDao;
import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.utils.UtilDateTime;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class LeaveRecordsDaoImpl implements LeaveRecordsDao {

    private Connection con;
    private UtilDateTime utilDate = new UtilDateTime();

    private List<LeaveRecords> employees;



    @Override
    public boolean addLeaveRecords(String employeeId , LeaveRecords leaveRecord)  {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        boolean isAdded = false;
	leaveRecord.setCreatedAt(utilDate.getCurDateTime());
	leaveRecord.setModifiedAt(utilDate.getCurDateTime());
        try {
            Transaction transaction = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, employeeId);
	        leaveRecord.setEmployee(employee);
            
            session.persist(leaveRecord); 
	
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e); 
        } finally {
	    session.close();
	}
        return isAdded;  
    }


    public List<LeaveRecords> getLeaveRecords() {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();

        List<LeaveRecords> leaveRecords = new ArrayList<>();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM LeaveRecords where isDeleted = false",LeaveRecords.class);
            leaveRecords = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        }
	    
        return leaveRecords;  
    
    }

    public List<LeaveRecords> getLeaveRecord(String employeeId) {
	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
        List<LeaveRecords> leaveRecords = new ArrayList<>();
        try {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM LeaveRecords WHERE employee.employeeId ='"+employeeId+"' AND isDeleted= false ",LeaveRecords.class);
            leaveRecords =query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
	    session.close();
        } 
        return leaveRecords; 
    }

    @Override
    public boolean updateLeaveRecord(LeaveRecords leaveRecord) {
        //SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        boolean isAdded = false;
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            leaveRecord.setModifiedAt(utilDate.getCurDateTime());
            session.update(leaveRecord);
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
            session.close();
        }
        return isAdded;
    }

    public LeaveRecords getLeaveByLeaveId(int leaveId) {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        LeaveRecords leave = null;
        boolean isAdded = false;
        try {
            Query query = session.createQuery("FROM LeaveRecords WHERE leaveId ='"+leaveId+"' AND isDeleted= false ",LeaveRecords.class);
            Transaction transaction = session.beginTransaction();
            leave  = (LeaveRecords) query.uniqueResult();
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }
        return leave;
    }

    public boolean updateLeaveRecord(String employeeId,LeaveRecords leaveRecord) {

	    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	    boolean isAdded = false;
        try {
            Transaction transaction = session.beginTransaction();
	        leaveRecord.setModifiedAt(utilDate.getCurDateTime());
	        session.update(leaveRecord);
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    }

/*
	boolean status = false;
	String query = "UPDATE leaveRecords SET leave_type = '"+leaveRecord.getLeaveType()+"', modified_at = '"+utilDate.getCurDateTime()+"' where leave_id = "+leaveRecord.getLeaveId()+" AND deleted = "+NOT_DELETED;//   "SELECT * FROM leaverecords where employee_id ='"+employeeId+"'";
	try {  
	    con = DBConnection.getConnection();
	    Statement statement = con.createStatement();
	    statement.execute(query);
	    status = true;
	} catch(Exception e) {
            status = false;
        } finally {
	    DBConnection.closeConnection(con);
	}
	return status;

*/    
    @Override
    public boolean deleteLeaveRecord(LeaveRecords leaveRecord) {

	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	Session session = sessionFactory.openSession();
	boolean isAdded = false;
        try {
            Transaction transaction = session.beginTransaction();
            leaveRecord.setIsDeleted(true);
            leaveRecord.setModifiedAt(utilDate.getCurDateTime());
            session.update(leaveRecord);
            transaction.commit();
            isAdded = true;
        } catch (Exception e) {
            System.out.println(e);
        }
	return isAdded;
    } 

/*
	boolean status = false;

	String query = "UPDATE leaverecords SET deleted = "+DELETED+" WHERE project_id = '"+leaveRecord.getLeaveId()+"'";
				   				  						
	try {
		con = DBConnection.getConnection();  
	        Statement statement = con.createStatement();
		statement.execute(query);
		status = true;
	} catch(Exception e) {
            System.out.println(e);
        } finally {
	    DBConnection.closeConnection(con);
	}
	return status;
*/  

}
