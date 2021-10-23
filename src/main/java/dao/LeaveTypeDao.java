package dao;

import common.ConnectDBProperty;
import entity.LeaveType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveTypeDao {
    public LeaveType getLeaveTypeInfoByName(String leaveTypeName) {
        LeaveType lType = new LeaveType();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from leave_type where leave_type = ?");) {
            cs.setString(1, leaveTypeName);
            var rs = cs.executeQuery();
            while (rs.next()) {
                lType.setLeaveID(rs.getInt("leave_id"));
                lType.setLeaveType(rs.getString("leave_type"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return lType;
    }

    public LeaveType getLeaveTypeInfoByID(int leaveTypeID) {
        LeaveType lType = new LeaveType();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from leave_type where leave_id = ?");) {
            cs.setInt(1, leaveTypeID);
            var rs = cs.executeQuery();
            while (rs.next()) {
                lType.setLeaveID(rs.getInt("leave_id"));
                lType.setLeaveType(rs.getString("leave_type"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return lType;
    }
}
