package entity;

import java.lang.reflect.Constructor;

public class Account {
    private String userName;
    private String password;
    private int emloyeeId;
    private int roleId;

    public Account(String userName, String password, int emloyeeId, int roleId) {
        this.userName = userName;
        this.password = password;
        this.emloyeeId = emloyeeId;
        this.roleId = roleId;
    }

    public Account() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmloyeeId() {
        return emloyeeId;
    }

    public void setEmloyeeId(int emloyeeId) {
        this.emloyeeId = emloyeeId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


}
