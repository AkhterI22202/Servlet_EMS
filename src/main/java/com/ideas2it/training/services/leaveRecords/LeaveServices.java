package com.ideas2it.training.services.leaveRecords;

import com.ideas2it.training.model.LeaveRecords;

import java.util.List;


public interface LeaveServices {

    public boolean addLeaveRecords(String employeeId , LeaveRecords leaveRecords);

    public List<LeaveRecords> getLeaveRecords() ;

    public List<LeaveRecords> getLeaveRecord(String employeeId);

    public boolean updateLeaveRecord(LeaveRecords leaveRecord);

    public LeaveRecords getLeaveByLeaveId(int leaveId);

    public int getLeaveCount(String employeeId) ;

    public boolean deleteLeaveRecord(LeaveRecords leaveRecord);


}