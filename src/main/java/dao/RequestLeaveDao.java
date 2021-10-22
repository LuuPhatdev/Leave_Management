package dao;

import common.ConnectDBProperty;
import common.SendMail;
import entity.RequestLeave;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RequestLeaveDao {
    public List<RequestLeave> getListRequestLeave(){
        List<RequestLeave> listRequest = new ArrayList<>();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from request");) {
            var rs = cs.executeQuery();
            while(rs.next()){
                var requestForm = new RequestLeave();
                requestForm.setRequestID(rs.getInt("request_id"));
                requestForm.setEmployeeID(rs.getInt("employee_id"));
                requestForm.setLeaveID(rs.getInt("leave_id"));
                requestForm.setDateStart(rs.getDate("date_start").toLocalDate());
                requestForm.setDateEnd(rs.getDate("date_end").toLocalDate());
                requestForm.setRequestStatus(rs.getString("request_status"));
                requestForm.setRequestTo(rs.getString("request_status"));
                requestForm.setAmount(rs.getInt("amount"));
                listRequest.add(requestForm);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listRequest;
    }
    public void insertRequestLeave(RequestLeave rLeave){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)\n" +
                     "values (?, ?, ?, ?, ?, ?, ?, ?)");) {
            cs.setInt(1, rLeave.getEmployeeID());
            cs.setInt(2, rLeave.getLeaveID());
            cs.setDate(3, Date.valueOf(rLeave.getDateStart()));
            cs.setDate(4, Date.valueOf(rLeave.getDateEnd()));
            cs.setString(5, rLeave.getRequestStatus());
            cs.setString(6, rLeave.getRequestTo());
            cs.setString(7, rLeave.getRequestDescription());
            cs.setInt(8, rLeave.getAmount());
            cs.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
