package entity;


import dao.AccountDao;

public class Account {
    private int accountId;
    private String userName;
    private String password;
    private int emloyeeId;
    private int roleId;

    public Account(int accountId, String userName, String password, int emloyeeId, int roleId) {
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        this.emloyeeId = emloyeeId;
        this.roleId = roleId;
    }

    public Account() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public static Account getAccountFromUserName(String username) {
        AccountDao accountDao = new AccountDao();
        return accountDao.getAccountInfo(username);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", emloyeeId=" + emloyeeId +
                ", roleId=" + roleId +
                '}';
    }
}
