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
}
