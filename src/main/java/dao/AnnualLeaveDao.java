package dao;

import common.ConnectDBProperty;
import entity.AnnualLeave;


import javax.swing.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnualLeaveDao {
    public List<AnnualLeave> getListAnnualLeaveByEmployeeID(int employeeID){
        List<AnnualLeave> listAnnualLeave = new ArrayList<>();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from time_off where employee_id = ?");) {
            cs.setInt(1, employeeID);
            createAnnualLeaveModel(listAnnualLeave, cs);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listAnnualLeave;
    }

    public List<Integer> groupByYear(){
        List<Integer> gbYear = new ArrayList<>();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select year(date_time_off) as year, count(date_time_off) as count " +
                     "from time_off " +
                     "group by year(date_time_off) "+
                     "order by year(date_time_off) DESC");) {
            var rs = cs.executeQuery();
            while(rs.next()){
                gbYear.add(rs.getInt("year"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return gbYear;
    }

    public List<AnnualLeave> getListAnnualLeaveByYear(int employeeID, int year){
        List<AnnualLeave> listAnnualLeave = new ArrayList<>();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from time_off where employee_id = ? AND " +
                     "year(date_time_off) = ?");) {
            cs.setInt(1, employeeID);
            cs.setInt(2, year);
            createAnnualLeaveModel(listAnnualLeave, cs);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listAnnualLeave;
    }

    private void createAnnualLeaveModel(List<AnnualLeave> listAnnualLeave, PreparedStatement cs) throws SQLException {
        var rs = cs.executeQuery();
        while(rs.next()){
            var annualLeave = new AnnualLeave();
            annualLeave.setTimeOffID(rs.getInt("time_off_id"));
            annualLeave.setEmployeeID(rs.getInt("employee_id"));
            annualLeave.setDateTimeOff(rs.getDate("date_time_off").toLocalDate());
            annualLeave.setDescriptionTimeOff(rs.getString("description_time_off"));
            annualLeave.setUsed(rs.getInt("used"));
            annualLeave.setAccrued(rs.getDouble("accrued"));
            annualLeave.setBalance(rs.getDouble("balance"));
            listAnnualLeave.add(annualLeave);
        }
    }

    public void insertAnnualLeave(AnnualLeave annLeave){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)\n" +
                     "values (?, ?, ?, ?, ?, ?)");) {
            cs.setInt(1, annLeave.getEmployeeID());
            cs.setDate(2, Date.valueOf(annLeave.getDateTimeOff()));
            cs.setString(3, annLeave.getDescriptionTimeOff());
            cs.setInt(4, annLeave.getUsed());
            cs.setDouble(5, annLeave.getAccrued());
            cs.setDouble(6, annLeave.getBalance());
            cs.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
