package dao;

import common.ConnectDBProperty;
import entity.Account;
import entity.Role;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    /**
     * Get List Account
     * @return
     */
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

    public Role getRole(int roleId){
        List<Role> listRole = new ArrayList<>();

        try (var connect = ConnectDBProperty.getConnectionFromClassPath();
             var cs = connect.prepareStatement("select * from role where role_id=?");
             ) {
            cs.setInt(1, roleId);
            var rs = cs.executeQuery();
          while (rs.next()){
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
}
