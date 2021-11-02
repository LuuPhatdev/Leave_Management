package dao;

import common.ConnectDBProperty;
import entity.Account;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDao {
    static void fetchAccounts(List<Account> users, ResultSet rs) throws SQLException {
        while (rs.next()) {
            var account = new Account();
            account.setEmloyeeId(rs.getInt("employee_id"));
            account.setRoleId(rs.getInt("role_id"));
            account.setUserName(rs.getString("username"));
            account.setPassword(rs.getString("pass"));
            users.add(account);
        }
    }

    public Account getAccountByUserName(String userName) {
        List<Account> users = new ArrayList<>();

        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from account where userName = ?");) {
            cs.setString(1, userName);
            var rs = cs.executeQuery();
            fetchAccounts(users, rs);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return users.get(0);
    }

    public static boolean checkIfExistsAcc(String userName) {
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from account where userName = ?");) {
            cs.setString(1, userName);
            var rs = cs.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;
    }
}
