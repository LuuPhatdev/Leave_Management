package dao;

import common.ConnectDBProperty;
import entity.Account;
import entity.Role;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public void createAccount(Account account) {
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareCall("{call insertAccount(?,?,?,?)}");
        ) {
            cs.setInt(1, account.getEmloyeeId());
            cs.setInt(2, account.getRoleId());
            cs.setString(3, account.getUserName());
            cs.setString(4, account.getPassword());
            cs.executeUpdate();
            JOptionPane.showMessageDialog(null, "success insert");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void updateAccount(Account account){
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareCall("{call updateAccount(?,?,?)}");
        ) {
            cs.setInt(1, account.getEmloyeeId());
            cs.setString(2, account.getUserName());
            cs.setString(3, account.getPassword());
            cs.executeUpdate();
            JOptionPane.showMessageDialog(null, "success update");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public List<Account> getListAccounts() {
        List<Account> listAccounts = new ArrayList<>();

        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareCall("{call seAllAccount}");
             var rs = cs.executeQuery();) {
            LoginDao.fetchAccounts(listAccounts, rs);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listAccounts;
    }

    public Role getRole(int roleId) {
        List<Role> listRole = new ArrayList<>();

        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from role where role_id=?");
        ) {
            cs.setInt(1, roleId);
            var rs = cs.executeQuery();
            while (rs.next()) {
                var role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleTitle(rs.getString("role_title"));
                role.setRoleDescription(rs.getString("role_description"));
                listRole.add(role);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return listRole.get(0);
    }

    public int getAdminID(){
        var adminID = 0;
        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from account where role_id = 1");
        ) {
            var rs = cs.executeQuery();
            while (rs.next()){
                adminID = rs.getInt("employee_id");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return adminID;
    }
}
