package dao;

import common.ConnectDBProperty;
import entity.Account;
import entity.Employee;

import javax.swing.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private void fetchListEmployee(List<Employee> listEmployee, PreparedStatement cs) throws SQLException {
        var rs = cs.executeQuery();
        while (rs.next()) {
            var employee = new Employee();
            employee.setEmployeeId(rs.getInt("employee_id"));
            employee.setDepartmentId(rs.getInt("dep_id"));
            employee.setFullName(rs.getString("fullname"));
            employee.setGender(rs.getInt("gender"));
            employee.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
            employee.setPhone(rs.getString("phone"));
            employee.setEmail(rs.getString("email"));
            employee.setDateStart(rs.getDate("date_start").toLocalDate());
            employee.setAnnualLeave(rs.getDouble("annual_leave"));
            employee.setManagerId(rs.getInt("manager_id"));
            listEmployee.add(employee);
        }
    }

    public List<Employee> getListEmployee(){
        List<Employee> listEmployee = new ArrayList<>();
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from employee");) {
            fetchListEmployee(listEmployee, cs);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listEmployee;
    }

    public Employee getEmployeeByEmployeeId(int employeeID) {
        List<Employee> employees = new ArrayList<>();

        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from employee where employee_id = ?");) {
            cs.setInt(1, employeeID);
            fetchListEmployee(employees, cs);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return employees.get(0);
    }
    public void updateSingleEmployee(Employee employee){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("UPDATE employee SET dep_id = ?, fullname = ?, gender = ?, date_of_birth = ?, " +
                     "phone = ?, email = ?, date_start = ?, annual_leave = ?, manager_id = ? " +
                     "WHERE employee_id = ?");) {
            cs.setInt(1, employee.getDepartmentId());
            cs.setString(2, employee.getFullName());
            cs.setInt(3, employee.getGender());
            cs.setDate(4, Date.valueOf(employee.getDateOfBirth()));
            cs.setString(5, employee.getPhone());
            cs.setString(6, employee.getEmail());
            cs.setDate(7, Date.valueOf(employee.getDateStart()));
            cs.setDouble(8, employee.getAnnualLeave());
            cs.setInt(9, employee.getManagerId());
            cs.setInt(10, employee.getEmployeeId());
            cs.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
