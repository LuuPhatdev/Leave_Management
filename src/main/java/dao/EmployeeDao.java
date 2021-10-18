package dao;

import common.ConnectDBProperty;
import entity.Account;
import entity.Employee;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public Employee getEmployeeInfo(int employeeID) {
        List<Employee> employees = new ArrayList<>();

        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from employee where employee_id = ?");) {
            cs.setInt(1, employeeID);
            var rs = cs.executeQuery();
            while (rs.next()) {
                var employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setDepartmentId(rs.getInt("dep_id"));
                employee.setFullName(rs.getString("fullname"));
                employee.setGender(rs.getInt("gender"));
                employee.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                employee.setPhone(rs.getInt("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setDateStart(rs.getDate("date_start").toLocalDate());
                employee.setAnnualLeave(rs.getDouble("annual_leave"));
                employee.setManagerId(rs.getInt("manager_id"));
                employees.add(employee);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return employees.get(0);
    }
}
