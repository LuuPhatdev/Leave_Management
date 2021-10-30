package gui;

import dao.*;
import entity.Account;
import entity.Role;
import gui.Form.GuiCreateAccountForm;
import gui.Form.GuiCreateEmployeeForm;
import gui.Form.GuiUpdateAccountForm;
import gui.Form.GuiUpdateEmloyee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private JPanel btnAddEmployee;
    private JPanel btnUpdate;
    private JPanel btnShowListAccount;
    private JTable accountTable;
    private JPanel btnShowListEmployee;
    private JLabel employeeName;
    private JLabel employeeRole;
    private JPanel btnCreateAccount;
    private JTable tableShowAccount;
    private JTextField txtSearchByName;
    private JButton btnSearch;
    private JPanel panelSearch;
    private JPanel btnDelete;
    private JTabbedPane tabbedPaneShow;
    public JTable tableShowEmployee;

    public GuiAdmin(String userName) {
        this.userName = userName;
        Account acc = Account.getAccountFromUserName(userName);
        Role role = Role.getListRole(acc.getRoleId());

        //Innit Layout
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 637);
        setLocationRelativeTo(null);
        setTitle("User Management");
        setVisible(true);

        this.showListAccount();
        this.showListEmployee();
        btnUpdate.setEnabled(false);

        //Tale styling
        tableShowEmployee.setRowHeight(30);
        tableShowAccount.setRowHeight(30);

        employeeName.setText(acc.getUserName());
        employeeRole.setText(role.getRoleTitle());
        tableShowAccount.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableShowEmployee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //Hover Cursor Pointer
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

        //Event
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

        btnCreateAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnCreateAccountActionPerformed(e);
            }
        });

        btnAddEmployee.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnAddEmployeeActionPerformed(e);
            }
        });

        btnUpdate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnUpdateActionPerformed(e);
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSearchActionPerformed(e);
            }
        });

        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnDeleteActionPerformed(e);
            }
        });
        contentPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ContentPaneMouseClicked(e);
            }
        });
    }

    //Unselect Tables
    private void ContentPaneMouseClicked(MouseEvent e) {
        tableShowAccount.clearSelection();
        tableShowEmployee.clearSelection();
    }

    //Show List Account
    public void showListAccount() {
        AccountDao usersDao = new AccountDao();
        String[] columnTitle = {"EMPLOYEE ID", "ROLE ID", "USER NAME", "PASSWORD"};
        DefaultTableModel model = new DefaultTableModel(columnTitle, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3 -> false;
                    default -> true;
                };
            }
        };

        usersDao.getListAccounts().forEach(user -> model.addRow(new Object[]{
                user.getEmloyeeId(),
                user.getRoleId(),
                user.getUserName(),
                user.getPassword(),}));
        tableShowAccount.setModel(model);
    }

    //-- Show list Employee
    public void showListEmployee() {
        EmployeeDao employeeDao = new EmployeeDao();
        String[] columnTitle = {"EMPLOYEE ID", "DEPARTMENT ID", "FULL NAME", "GENDER", "DATE OF BIRTH", "PHONE",
                "EMAIL", "DATE START", "ANNUAL LEAVE", "MANAGER ID"};
        DefaultTableModel model = new DefaultTableModel(columnTitle, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> false;
                    default -> true;
                };
            }
        };

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
        tableShowEmployee.setModel(model);
    }

    //-- Search
    private void btnSearchActionPerformed(ActionEvent e) {
        if (txtSearchByName.getText().isEmpty()) {
            if (tabbedPaneShow.getSelectedIndex() == 0) {
                showListAccount();
            } else {
                showListEmployee();
            }
        } else {
            if (tabbedPaneShow.getSelectedIndex() == 0) {
                var accountDao = new AccountDao();
                try {
                    var listAcc = accountDao.getSearchedUserName(txtSearchByName.getText().trim().replaceAll(("\\s"),
                            ""));
                    String[] columnTitle = {"EMPLOYEE ID", "ROLE ID", "USER NAME", "PASSWORD"};
                    DefaultTableModel model = new DefaultTableModel(columnTitle, 0);

                    listAcc.forEach(user -> model.addRow(new Object[]{
                            user.getEmloyeeId(),
                            user.getRoleId(),
                            user.getUserName(),
                            user.getPassword()
                    }));

                    tableShowAccount.setModel(model);
                } catch (Exception em) {
                    JOptionPane.showMessageDialog(null, "Account does not exists!");
                    txtSearchByName.setText("");
                }
            } else {
                var employeeDao = new EmployeeDao();
                try {
                    var listEmployee = employeeDao.getSearchedEmployee(txtSearchByName.getText().trim().replaceAll((
                            "\\s"), ""));
                    String[] columnTitle = {"EMPLOYEE ID", "DEPARTMENT ID", "FULL NAME", "GENDER", "DATE OF BIRTH",
                            "PHONE",
                            "EMAIL", "DATE START", "ANNUAL LEAVE", "MANAGER ID"};
                    DefaultTableModel model = new DefaultTableModel(columnTitle, 0);

                    listEmployee.forEach(employee -> model.addRow(new Object[]{
                            employee.getEmployeeId(),
                            employee.getDepartmentId(),
                            employee.getFullName(),
                            employee.getGender(),
                            employee.getDateOfBirth(),
                            employee.getPhone(),
                            employee.getEmail(),
                            employee.getDateStart(),
                            employee.getAnnualLeave(),
                            employee.getManagerId()
                    }));

                    tableShowEmployee.setModel(model);
                } catch (Exception em) {
                    JOptionPane.showMessageDialog(null, "Account does not exists!");
                    txtSearchByName.setText("");
                }
            }
        }
    }

    //-- Update Account
    private void btnUpdateActionPerformed(MouseEvent e) {
        if (tabbedPaneShow.getSelectedIndex() == 0) {
            var rowindex = tableShowAccount.getSelectedRow();
            if (rowindex == -1) {
                JOptionPane.showMessageDialog(null, "Please select row you want to update.");
            } else {
                if (tabbedPaneShow.getSelectedIndex() == 0) {
                    var employeeId = tableShowAccount.getValueAt(rowindex, 0).toString();
                    var roleId = tableShowAccount.getValueAt(rowindex, 1).toString();
                    var userName = tableShowAccount.getValueAt(rowindex, 2).toString();
                    var password = tableShowAccount.getValueAt(rowindex, 3).toString();

                    var dialog = new GuiUpdateAccountForm(this);
                    dialog.txtEmployeeId.setText(employeeId);
                    dialog.txtRoleId.setText(roleId);
                    dialog.txtUserName.setText(userName);
                    dialog.txtPassword.setText(password);
                    btnUpdate.setEnabled(true);
                    dialog.setVisible(true);
                    this.setEnabled(false);
                }
            }
        } else {
            var rowindex = tableShowEmployee.getSelectedRow();
            if (rowindex == -1) {
                JOptionPane.showMessageDialog(null, "Please select row you want to update.");
            } else {
                try {
                    var employeeId = tableShowEmployee.getValueAt(rowindex, 0).toString();
                    var departmentid = tableShowEmployee.getValueAt(rowindex, 1).toString();
                    var fullName = tableShowEmployee.getValueAt(rowindex, 2).toString();
                    var gender = tableShowEmployee.getValueAt(rowindex, 3).toString();
                    var dateOfBirth =
                            new SimpleDateFormat("MM-dd-yyyy").parse(tableShowEmployee.getValueAt(rowindex,
                                    4).toString());
                    var phone = tableShowEmployee.getValueAt(rowindex, 5).toString();
                    var email = tableShowEmployee.getValueAt(rowindex, 6).toString();
                    var dateStart =
                            new SimpleDateFormat("yyyy-MM-dd").parse(tableShowEmployee.getValueAt(rowindex,
                                    7).toString());
                    var annualLeave = tableShowEmployee.getValueAt(rowindex, 8).toString();
                    var managerId = tableShowEmployee.getValueAt(rowindex, 9).toString();

                    var newEmployee = new GuiUpdateEmloyee(this);
                    newEmployee.txtEmployeeId.setText(employeeId);
                    newEmployee.txtDepartmentId.setText(departmentid);
                    newEmployee.txtFullname.setText(fullName);
                    newEmployee.JcomboboxGender.getEditor().setItem(gender);
                    newEmployee.JDateDateOfBirth.setDate(dateOfBirth);
                    newEmployee.txtPhone.setText(phone);
                    newEmployee.txtEmail.setText(email);
                    newEmployee.JDateStart.setDate(dateStart);
                    newEmployee.txtAnnualLeave.setText(annualLeave);
                    newEmployee.txtManagerId.setText(managerId);
                    newEmployee.setVisible(true);
                } catch (Exception em) {
                    JOptionPane.showMessageDialog(null, "wrong");
                }
            }
        }
    }

    //-- Show List Account
    private void btnShowListAccount(MouseEvent e) {
        this.showListAccount();
    }

    //-- Delete Account and Employee
    private void btnDeleteActionPerformed(MouseEvent e) {
        int result = JOptionPane.showOptionDialog(null, "Are you sure you want to delete?", "Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.YES_OPTION) {
            var dao = new AccountDao();
            var account = new Account();
            var requestLeaveDao = new RequestLeaveDao();
            var annualLeaveDao = new AnnualLeaveDao();
            var departmentDao = new DepartmentDao();
            var employeeDao = new EmployeeDao();

            if (tableShowAccount.getSelectedRow() != -1) {

                var rowindex = tableShowAccount.getSelectedRow();
                var employeeId = tableShowAccount.getValueAt(rowindex, 0).toString();
                if (dao.getAdminID() == Integer.parseInt(employeeId)) {
                    JOptionPane.showMessageDialog(null, "you cant delete admin's account.");
                } else {
                    account.setEmloyeeId(Integer.parseInt(employeeId));
                    dao.deleteAccount(account);
                }
                showListAccount();
            } else if (tableShowEmployee.getSelectedRow() != -1) {
                var rowindex = tableShowEmployee.getSelectedRow();
                var employeeID = tableShowEmployee.getValueAt(rowindex, 0).toString();

                if (dao.getAdminID() == Integer.parseInt(employeeID)) {
                    JOptionPane.showMessageDialog(null, "you cant delete admin's employee info.");
                } else if (!departmentDao.checkIfIsChiefDepartment(Integer.parseInt(employeeID))) {
                    requestLeaveDao.deleteRequestLeaveByEmployeeID(Integer.parseInt(employeeID));
                    annualLeaveDao.deleteAnnualLeaveByEmployeeID(Integer.parseInt(employeeID));
                    account.setEmloyeeId(Integer.parseInt(employeeID));
                    dao.deleteAccount(account);
                    employeeDao.deleteEmployeeByEmployeeID(Integer.parseInt(employeeID));
                    showListEmployee();
                    showListAccount();
                } else {
                    JOptionPane.showMessageDialog(null, "you cant delete a chief of department.");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Please select row you want to delete.");
            }
        }
    }

    //-- Create Account
    private void btnCreateAccountActionPerformed(MouseEvent e) {
        var form = new GuiCreateAccountForm(this);
        form.setVisible(true);
        this.setEnabled(false);
    }

    //--Create Employee
    private void btnAddEmployeeActionPerformed(MouseEvent e) {
        var form = new GuiCreateEmployeeForm(this);
        form.setVisible(true);
        this.setEnabled(false);
    }

    //--Switch Tabbled Pane
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
