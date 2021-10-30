package dao;

import common.ConnectDBProperty;
import common.SendMail;
import entity.Employee;
import entity.RequestLeave;

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
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
                requestForm.setRequestDescription(rs.getString("request_description"));
                requestForm.setAmount(rs.getInt("amount"));
                listRequest.add(requestForm);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listRequest;
    }

    public List<RequestLeave> getListRequestLeaveByEmail(String email){
        List<RequestLeave> listRequest = new ArrayList<>();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from request where request_to = ?");) {
            cs.setString(1, email);
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
                requestForm.setRequestDescription(rs.getString("request_description"));
                requestForm.setAmount(rs.getInt("amount"));
                listRequest.add(requestForm);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listRequest;
    }

    public RequestLeave getRequestLeaveByRequestID(int requestLeaveID){
        var requestLeave = new RequestLeave();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from request where request_id = ?");) {
            cs.setInt(1, requestLeaveID);
            var rs = cs.executeQuery();
            while(rs.next()){
                requestLeave.setRequestID(rs.getInt("request_id"));
                requestLeave.setEmployeeID(rs.getInt("employee_id"));
                requestLeave.setLeaveID(rs.getInt("leave_id"));
                requestLeave.setDateStart(rs.getDate("date_start").toLocalDate());
                requestLeave.setDateEnd(rs.getDate("date_end").toLocalDate());
                requestLeave.setRequestStatus(rs.getString("request_status"));
                requestLeave.setRequestTo(rs.getString("request_status"));
                requestLeave.setRequestDescription(rs.getString("request_description"));
                requestLeave.setAmount(rs.getInt("amount"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return requestLeave;
    }

    public boolean pendingCheckingByEmployeeID(int employeeID){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from request where employee_id = ? and request_status = 'pending'");) {
            cs.setInt(1, employeeID);
            var rs = cs.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }

    public RequestLeave getRecentlyAcceptedRequestFromEmployeeID(int employeeID){
        var requestLeave = new RequestLeave();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select TOP 1 * from request where employee_id = ? and request_status = 'accepted' " +
                     "order by date_end DESC");) {
            cs.setInt(1, employeeID);
            var rs = cs.executeQuery();
            while(rs.next()){
                requestLeave.setRequestID(rs.getInt("request_id"));
                requestLeave.setEmployeeID(rs.getInt("employee_id"));
                requestLeave.setLeaveID(rs.getInt("leave_id"));
                requestLeave.setDateStart(rs.getDate("date_start").toLocalDate());
                requestLeave.setDateEnd(rs.getDate("date_end").toLocalDate());
                requestLeave.setRequestStatus(rs.getString("request_status"));
                requestLeave.setRequestTo(rs.getString("request_status"));
                requestLeave.setRequestDescription(rs.getString("request_description"));
                requestLeave.setAmount(rs.getInt("amount"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return requestLeave;
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

    public void updateRequestLeaveByIDToStatus(int requestID, String status){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("update request set request_status = ? where request_id = ?");) {
            cs.setString(1, status);
            cs.setInt(2, requestID);
            cs.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
