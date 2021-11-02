package dao;

import common.ConnectDBProperty;
import entity.AnnualLeave;
import org.decimal4j.util.DoubleRounder;


import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class AnnualLeaveDao {
    public List<AnnualLeave> getListAnnualLeaveByEmployeeID(int employeeID){
        List<AnnualLeave> listAnnualLeave = new ArrayList<>();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from time_off where employee_id = ?");) {
            cs.setInt(1, employeeID);
            var rs = cs.executeQuery();
            while(rs.next()){
                var annualLeave = new AnnualLeave();
                annualLeave.setTimeOffID(rs.getInt("time_off_id"));
                annualLeave.setEmployeeID(rs.getInt("employee_id"));
                annualLeave.setDateTimeOff(rs.getDate("date_time_off").toLocalDate());
                annualLeave.setDescriptionTimeOff(rs.getString("description_time_off"));
                annualLeave.setUsed(rs.getInt("used"));
                annualLeave.setAccrued(DoubleRounder.round(rs.getDouble("accrued"), 2));
                annualLeave.setBalance(DoubleRounder.round(rs.getDouble("balance"), 2));
                listAnnualLeave.add(annualLeave);

            }
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
            var rs = cs.executeQuery();
            while(rs.next()){
                var annualLeave = new AnnualLeave();
                annualLeave.setTimeOffID(rs.getInt("time_off_id"));
                annualLeave.setEmployeeID(rs.getInt("employee_id"));
                annualLeave.setDateTimeOff(rs.getDate("date_time_off").toLocalDate());
                annualLeave.setDescriptionTimeOff(rs.getString("description_time_off"));
                annualLeave.setUsed(rs.getInt("used"));
                annualLeave.setAccrued(DoubleRounder.round(rs.getDouble("accrued"), 2));
                annualLeave.setBalance(DoubleRounder.round(rs.getDouble("balance"), 2));
                listAnnualLeave.add(annualLeave);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listAnnualLeave;
    }

    public void insertAnnualLeave(AnnualLeave annLeave){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)\n" +
                     "values (?, ?, ?, ?, ?, ?)");) {
            cs.setInt(1, annLeave.getEmployeeID());
            cs.setDate(2, Date.valueOf(annLeave.getDateTimeOff()));
            cs.setString(3, annLeave.getDescriptionTimeOff());
            cs.setInt(4, annLeave.getUsed());
            cs.setDouble(5, DoubleRounder.round(annLeave.getAccrued(), 2));
            cs.setDouble(6, DoubleRounder.round(annLeave.getBalance(), 2));
            cs.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void deleteAnnualLeaveByEmployeeID(int employeeID){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("delete from time_off where employee_id = ?");) {
            cs.setInt(1, employeeID);
            cs.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
