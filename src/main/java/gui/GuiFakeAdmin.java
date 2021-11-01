package gui;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import common.SendMail;
import dao.*;
import entity.*;
import gui.Form.GuiCreateAccountForm;
import gui.Form.GuiCreateEmployeeForm;
import gui.Form.GuiUpdateAccountForm;
import gui.Form.GuiUpdateEmloyee;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;


public class GuiFakeAdmin extends JFrame {

    private final int employeeID;
    private final String userName;
    private JPanel contentPane;
     private JDateChooser jDateStartChooser;
    private JDateChooser jDateEndChooser;
       private JPanel btnLogOut;
    private JLabel employeeName;
    private JLabel employeeRole;
    public JLabel btnInbox;
    private JPanel btnAddEmployee;
    private JPanel btnCreateAccount;
    private JPanel btnUpdate;
    private JPanel panelSearch;
    private JButton btnSearch;
    private JTextField txtSearchByName;
    private JPanel btnDelete;
    private JTabbedPane tabbedPaneShow;
    private JTable tableShowAccount;
    private JTable tableShowEmployee;
    private JPanel topPanel;
    private JPanel bottom;
    private JPanel p5;
    private EmployeeDao employeeDao = new EmployeeDao();
    private DepartmentDao departmentDao = new DepartmentDao();
    private AnnualLeaveDao annualLeaveDao = new AnnualLeaveDao();

    public GuiFakeAdmin(int empID, String userName) {
        this.userName = userName;
        this.employeeID = empID;
        var employee = employeeDao.getEmployeeByEmployeeId(employeeID);
        var department = departmentDao.getDepartmentInfo(employee.getDepartmentId());
        Account acc = Account.getAccountFromUserName(userName);
        var allYears = annualLeaveDao.groupByYear();
        var role = Role.getListRole(acc.getRoleId());

        employeeName.setText(employee.getFullName());
        employeeRole.setText(role.getRoleTitle());

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 800, 640);
        setLocationRelativeTo(null);
        setTitle("Employee Management");
        setVisible(true);

        tableShowEmployee.setRowHeight(30);
        tableShowAccount.setRowHeight(30);

        employeeName.setText(acc.getUserName());
        employeeRole.setText(role.getRoleTitle());
        tableShowAccount.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableShowEmployee.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.showListAccount();
        this.showListEmployee();
        btnUpdate.setEnabled(false);

        //-- Cursor Poiter
        btnLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //-- CRUD EVENT
        btnAddEmployee.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnAddEmployeeActionPerformed(e);
            }
        });
        btnCreateAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnCreateAccountActionPerformed(e);
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

        btnLogOut.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnLogOutActionPerformed(e);
            }
        });
    }

    //-- Unselect Tables
    private void ContentPaneMouseClicked(MouseEvent e) {
        tableShowAccount.clearSelection();
        tableShowEmployee.clearSelection();
    }

    //-- CRUD EVENT --
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

    //-- Log Out
    private void btnLogOutActionPerformed(MouseEvent e) {
        int q = JOptionPane.showOptionDialog(null, "Are you sure exit?", "Exit", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (q == JOptionPane.YES_OPTION) {
            this.dispose();
            GuiLogin mainPane = new GuiLogin();
            mainPane.setVisible(true);
        }
    }

    //-- Show List Account
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
        jDateStartChooser = new JDateChooser();
        jDateEndChooser = new JDateChooser();
        JTextFieldDateEditor editorStart = (JTextFieldDateEditor) jDateStartChooser.getDateEditor();
        JTextFieldDateEditor editorEnd = (JTextFieldDateEditor) jDateEndChooser.getDateEditor();

        editorStart.setEditable(false);
        editorEnd.setEditable(false);
        jDateEndChooser.setEnabled(false);

        var calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        var tomorrow = calendar.getTime();

        jDateStartChooser.getJCalendar().setMinSelectableDate(tomorrow);
        jDateEndChooser.getJCalendar().setMinSelectableDate(tomorrow);
    }
}
