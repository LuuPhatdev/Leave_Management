package dao;

import common.ConnectDBProperty;
import entity.Department;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao {
    public Department getDepartmentInfo(int departmentID) {
        List<Department> departments = new ArrayList<>();

        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from department where dep_id = ?");) {
            cs.setInt(1, departmentID);
            var rs = cs.executeQuery();
            while (rs.next()) {
                var department = new Department();
                department.setDepID(rs.getInt("dep_id"));
                department.setDepTitle(rs.getString("dep_title"));
                department.setChiefID(rs.getInt("chief_id"));
                departments.add(department);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return departments.get(0);
    }
}
