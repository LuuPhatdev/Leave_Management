package gui;

import dao.AccountDao;
import dao.EmployeeDao;
import dao.LoginDao;
import entity.Account;
import entity.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiAdmin extends JFrame {

    private final String userName;
    private JPanel contentPane;
    private JPanel sidePanel;
    private JPanel centerPanel;
    private JPanel p1;
    private JPanel btn1;
    private JPanel p2;
    private JPanel btn2;
    private JPanel btnLogOut;
    private JPanel btnAddUser;
    private JPanel btnUpdate;
    private JPanel btnDelete;
    private JPanel btnShowListAccount;
    private JTable accountTable;
    private JPanel btnShowListEmployee;
    private JLabel employeeName;
    private JLabel employeeRole;

    public GuiAdmin(String userName) {
        this.userName = userName;
        Account acc = Account.getAccountFromUserName(userName);
        Role role = Role.getListRole(acc.getRoleId()) ;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 637);
        setTitle("User Management");
        setVisible(true);

        employeeName.setText(acc.getUserName());
        employeeRole.setText(role.getRoleTitle());

        btn1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btn1ActionPerformed(e);
            }
        });

        btn2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btn2ActionPerformed(e);
            }
        });

        btnLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnLogOutActionPerformed(e);
            }
        });

        btnShowListAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnShowListAccount(e);
            }
        });

        btnShowListEmployee.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnShowListEmployee(e);
            }
        });
    }

    private void btnShowListAccount(MouseEvent e) {
        AccountDao usersDao = new AccountDao();
        var model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                return switch (column) {
                    case 0 -> String.class;
                    case 1 -> String.class;
                    case 2 -> String.class;
                    case 3 -> String.class;
                    default -> String.class;
                };
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3 -> false;
                    default -> true;
                };
            }
        };

        model.addColumn("EMPLOYEE ID");
        model.addColumn("ROLE ID");
        model.addColumn("USER NAME");
        model.addColumn("PASSWORD");

        usersDao.getListAccounts().forEach(user -> model.addRow(new Object[]{
                user.getEmloyeeId(),
                user.getRoleId(),
                user.getUserName(),
                user.getPassword(),}));
        accountTable.setModel(model);
    }

    private void btnShowListEmployee(MouseEvent e) {
        EmployeeDao employeeDao = new EmployeeDao();
        var model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                return switch (column) {
                    case 0 -> String.class;
                    case 1 -> String.class;
                    case 2 -> String.class;
                    case 3 -> String.class;
                    case 4 -> String.class;
                    case 5 -> String.class;
                    case 6 -> String.class;
                    case 7 -> String.class;
                    case 8 -> String.class;
                    case 9 -> String.class;

                    default -> String.class;
                };
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> false;
                    default -> true;
                };
            }
        };

        model.addColumn("EMPLOYEE ID");
        model.addColumn("DEPARTMENT ID");
        model.addColumn("FULL NAME");
        model.addColumn("GENDER");
        model.addColumn("DATE OF BIRTH");
        model.addColumn("PHONE");
        model.addColumn("EMAIL");
        model.addColumn("DATE START");
        model.addColumn("ANNUAL LEAVE");
        model.addColumn("MANAGER ID");

        employeeDao.getListEmployee().forEach(user -> model.addRow(new Object[]{
                user.getEmployeeId(),
                user.getDepartmentId(),
                user.getFullName(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getPhone(),
                user.getEmail(),
                user.getDateStart(),
                user.getAnnualLeave(),
                user.getManagerId(),
        }));
        accountTable.setModel(model);
    }


    private void btn1ActionPerformed(MouseEvent e) {
        p1.setVisible(true);
        p2.setVisible(false);
    }

    private void btn2ActionPerformed(MouseEvent e) {
        p1.setVisible(false);
        p2.setVisible(true);
    }

    private void btnLogOutActionPerformed(MouseEvent e) {
        int q = JOptionPane.showOptionDialog(null, "Are you sure exit?", "Exit", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (q == JOptionPane.YES_OPTION) {
            this.dispose();
            GuiLogin mainPane = new GuiLogin();
            mainPane.setVisible(true);
        }
    }
}
