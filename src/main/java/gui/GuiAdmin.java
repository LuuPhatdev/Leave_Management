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

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 637);
        setLocationRelativeTo(null);
        setTitle("User Management");
        setVisible(true);

        this.showListAccount();
        this.showListEmployee();
        btnUpdate.setEnabled(false);

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
    }


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
    } //-- Show list Account

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
    } //-- Show list Employee

    private void btnSearchActionPerformed(ActionEvent e) {
        if (txtSearchByName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please input User Name");
            tableShowAccount.repaint();
        } else {
            try {
                Account acc = Account.getAccountFromUserName(txtSearchByName.getText().trim().replaceAll(("\\s"), ""));
                String[] columnTitle = {"EMPLOYEE ID", "ROLE ID", "USER NAME", "PASSWORD"};
                DefaultTableModel model = new DefaultTableModel(columnTitle, 0);

                model.addRow(new Object[]{
                        acc.getEmloyeeId(),
                        acc.getRoleId(),
                        acc.getUserName(),
                        acc.getPassword()
                });

                tableShowAccount.setModel(model);
            } catch (Exception em) {
                JOptionPane.showMessageDialog(null, "Account does not exists!");
                txtSearchByName.setText("");
            }
        }
    }

    private void btnUpdateActionPerformed(MouseEvent e) {
        if (tabbedPaneShow.getSelectedIndex() == 0) {
            var rowindex = tableShowAccount.getSelectedRow();
            if (rowindex == -1) {
                JOptionPane.showMessageDialog(null, "Please select row you want to update.");
            } else {
                if(tabbedPaneShow.getSelectedIndex()==0){
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

    private void btnShowListAccount(MouseEvent e) {
        this.showListAccount();
    }

    private void btnDeleteActionPerformed(MouseEvent e) {
        int result = JOptionPane.showOptionDialog(null, "Are you sure you want to delete?", "Delete", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.YES_OPTION) {
            var dao = new AccountDao();
            var account = new Account();
            var requestLeaveDao = new RequestLeaveDao();
            var annualLeaveDao = new AnnualLeaveDao();
            var departmentDao = new DepartmentDao();
            var employeeDao = new EmployeeDao();

            if(tableShowAccount.getSelectedRow() != -1){

                var rowindex = tableShowAccount.getSelectedRow();
                var employeeId = tableShowAccount.getValueAt(rowindex, 0).toString();

                account.setEmloyeeId(Integer.parseInt(employeeId));
                dao.deleteAccount(account);
                showListAccount();
            }else{
                var rowindex = tableShowEmployee.getSelectedRow();
                var employeeID = tableShowEmployee.getValueAt(rowindex, 0).toString();

                if(!departmentDao.checkIfIsChiefDepartment(Integer.parseInt(employeeID))){
                    requestLeaveDao.deleteRequestLeaveByEmployeeID(Integer.parseInt(employeeID));
                    annualLeaveDao.deleteAnnualLeaveByEmployeeID(Integer.parseInt(employeeID));
                    account.setEmloyeeId(Integer.parseInt(employeeID));
                    dao.deleteAccount(account);
                    employeeDao.deleteEmployeeByEmployeeID(Integer.parseInt(employeeID));
                    showListEmployee();
                    showListAccount();
                }else{
                    JOptionPane.showMessageDialog(null, "you cant delete a chief of department.");
                }
            }
        }
    }

    private void btnCreateAccountActionPerformed(MouseEvent e) {
        var form = new GuiCreateAccountForm(this);
        form.setVisible(true);
        this.setEnabled(false);
    }

    private void btnAddEmployeeActionPerformed(MouseEvent e) {
        var form = new GuiCreateEmployeeForm(this);
        form.setVisible(true);
        this.setEnabled(false);
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
