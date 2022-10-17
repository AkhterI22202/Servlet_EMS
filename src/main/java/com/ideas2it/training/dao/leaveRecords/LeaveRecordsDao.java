package com.ideas2it.training.dao.leaveRecords;

import com.ideas2it.training.model.LeaveRecords;

import java.util.List;

public interface LeaveRecordsDao {

    public boolean addLeaveRecords(String employeeId , LeaveRecords leaveRecords);
    public List<LeaveRecords> getLeaveRecords() ;
    public List<LeaveRecords> getLeaveRecord(String employeeId) ;
    public boolean updateLeaveRecord(LeaveRecords leaveRecord) ;
    public LeaveRecords getLeaveByLeaveId(int leaveId);
    public boolean deleteLeaveRecord(LeaveRecords leaveRecords);
    
}