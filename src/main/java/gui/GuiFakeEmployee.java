package gui;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import common.SendMail;
import dao.*;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;


public class GuiFakeEmployee extends JFrame {

    private final int employeeID;
    private final String userName;
    private JPanel contentPane;
    private JLabel btnPersonal;
    private JPanel topPanel;
    private JPanel bottom;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JTextField txtFullName;
    private JTextField txtDepartment;
    private JTextField txtGender;
    private JTextField txtBirthdate;
    private JLabel lbLeaveType;
    private JComboBox cBLeaveType;
    private JLabel btnHistory;
    private JLabel lbdateStart;
    private JDateChooser jDateStartChooser;
    private JDateChooser jDateEndChooser;
    private JLabel lbDateEnd;
    private JTextArea txtARequestDescription;
    private JLabel lbRequestDescription;
    private JButton btnSendRequest;
    private JComboBox cbGroupByYear;
    private JTable tbAnnualLeave;
    private JLabel lbPhoneNumber;
    private JLabel lbEmail;
    private JLabel lbFillHiredDate;
    private JLabel lbEmployeeID;
    private JLabel lbManagerName;
    private JLabel lbAnnualLeave;
    private JLabel h1;
    private JLabel h2;
    private JLabel h3;
    private JLabel hrBasic;
    private JPanel btnLogOut;
    private JLabel employeeName;
    private JLabel employeeRole;
    public JLabel btnInbox;
    private JPanel p4;
    private JButton btnRequest;
    private JTable tbInbox;
    private EmployeeDao employeeDao = new EmployeeDao();
    private DepartmentDao departmentDao = new DepartmentDao();
    private AnnualLeaveDao annualLeaveDao = new AnnualLeaveDao();
    private LeaveTypeDao lTypeDao = new LeaveTypeDao();
    private RequestLeaveDao rLeaveDao = new RequestLeaveDao();
    private AccountDao accountDao = new AccountDao();
    private RequestLeaveDao requestDao = new RequestLeaveDao();
    private Role role = new Role();

    public GuiFakeEmployee(int empID, String userName) {
        this.userName =userName;
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
        setBounds(200, 200, 700, 480);
        setLocationRelativeTo(null);
        setTitle("Employee Management");
        setVisible(true);

        txtFullName.setBorder(null);
        txtGender.setBorder(null);
        txtDepartment.setBorder(null);
        txtBirthdate.setBorder(null);

        tbAnnualLeave.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbInbox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        //Table Styling
        tbAnnualLeave.setRowHeight(30);
        tbAnnualLeave.setShowGrid(false);
        DefaultTableCellRenderer renderer =
                (DefaultTableCellRenderer) tbAnnualLeave.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);

        h1.setText("<html><div style=\"width:150px;text-align:left;border-bottom: 1px solid #e0e0e0;;overflow: " +
                "hidden;\n" +
                "    white-space: nowrap;\"></div></html>");
        h2.setText("<html><div style=\"width:150px;text-align:left;border-bottom: 1px solid #e0e0e0;;overflow: " +
                "hidden;\n" +
                "    white-space: nowrap;\"></div></html>");
        h3.setText("<html><div style=\"width:150px;text-align:left;border-bottom: 1px solid #e0e0e0;;overflow: " +
                "hidden;\n" +
                "    white-space: nowrap;\"></div></html>");
        hrBasic.setText("<html><div style=\"width:150px;text-align:left;border-bottom: 1px solid #565656;;overflow: " +
                "hidden;\n" +
                "    white-space: nowrap;\"></div></html>");

        //        Employee Info
        showEmployeeInfo(employee, department);

        // History
        filterHistory(allYears);
        this.showHistory();

        jDateStartChooser.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                jDateStartChooserPropertyChange(evt);
            }
        });
        jDateEndChooser.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                jDateEndChooserPropertyChange(evt);
            }
        });

        cbGroupByYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cbGroupByYearActionPerformed(e);
            }
        });

        //Send Request
        btnSendRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSendRequestActionPerformed(e);
            }
        });

        //Send Request Tag
        btnRequest.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRequestActionPerformed(e);
            }
        });

        //Personal Tag
        btnPersonal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPersonal.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnPersonalActionPerformed(e);
            }
        });

        //History Tag
        btnHistory.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHistory.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnHistoryActionPerformed(e);
            }
        });

        btnLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnLogOutActionPerformed(e);
            }
        });
        btnInbox.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnInboxActionPerformed(e);
            }
        });

//        inbox tag
        var tableModel2 = this.showInbox();
        tbInbox.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TbInboxMouseClicked(e, tableModel2);
            }
        });
        cBLeaveType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(employee.getAnnualLeave() == 0){
                    if(cBLeaveType.getSelectedItem().equals("Annual leave") || cBLeaveType.getSelectedItem().equals("Sick leave")){
                        JOptionPane.showMessageDialog(null, "can not choose this when annual leave is 0.");
                        cBLeaveType.setSelectedIndex(0);
                    }
                }
            }
        });
    }

    private void TbInboxMouseClicked(MouseEvent e, DefaultTableModel tableModel2) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (tbInbox.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select row first.");
            } else {
                var status = tbInbox.getModel().getValueAt(tbInbox.getSelectedRow(), 0).toString();
                if(status.equals("cancelled")){
                    JOptionPane.showMessageDialog(null, "Can not cancel a cancelled request.");
                } else if (status.equals("denied")){
                    JOptionPane.showMessageDialog(null, "Can not cancel a denied request.");
                } else if (LocalDate.now().compareTo(
                        LocalDate.parse( tbInbox.getModel().getValueAt(tbInbox.getSelectedRow(), 2).toString() ) ) >= 0){
                    JOptionPane.showMessageDialog(null, "Can not cancel an accepted request already in effect.");
                } else{
                    var cancelling = new GuiCancellingRequest(this, tbInbox, tableModel2);
                }
            }
        }
    }

    private DefaultTableModel showInbox(){
        String[] collumnNames ={"Status","Request ID", "From", "To", "Leave Type", "Description"};
        var tableModel = new DefaultTableModel(collumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3, 4, 5 -> false;
                    default -> true;
                };
            }
        };
        var listRequestLeaveOfEmployeeID = requestDao.getListRequestLeaveByEmployeeID(employeeID);
        listRequestLeaveOfEmployeeID.forEach(emp -> tableModel.addRow( new Object[]{
                emp.getRequestStatus(),
                emp.getRequestID(),
                emp.getDateStart(),
                emp.getDateEnd(),
                lTypeDao.getLeaveTypeInfoByID(emp.getLeaveID()).getLeaveType(),
                emp.getRequestDescription()
        }));
        tbInbox.setModel(tableModel);
        return tableModel;
    }

    private void showHistory() {
        int count;
        String[] collumnNames = {"Date Annual Leave", "Description", "Used(-)", "Accrued(+)", "Balance"};
        var tableModel = new DefaultTableModel(collumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3, 4 -> false;
                    default -> true;
                };
            }
        };
        var employeeAnnualLeaveList = annualLeaveDao.getListAnnualLeaveByEmployeeID(employeeID);
        count = 0;

        while (employeeAnnualLeaveList.size() > count) {
            var dateAnnualLeave = employeeAnnualLeaveList.get(count).getDateTimeOff();
            var descriptionAnnualLeave = employeeAnnualLeaveList.get(count).getDescriptionTimeOff();
            var usedAnnualLeave = employeeAnnualLeaveList.get(count).getUsed();
            var accruedAnnualLeave = employeeAnnualLeaveList.get(count).getAccrued();
            var balanceAnnualLeave = employeeAnnualLeaveList.get(count).getBalance();
            Object[] data = {dateAnnualLeave, descriptionAnnualLeave, usedAnnualLeave, accruedAnnualLeave,
                    balanceAnnualLeave};
            tableModel.addRow(data);
            count++;
        }
        tbAnnualLeave.setModel(tableModel);
    }

    private void showEmployeeInfo(Employee employee, Department department) {
        lbPhoneNumber.setText(String.valueOf(employee.getPhone()));
        lbEmail.setText(employee.getEmail());
        lbFillHiredDate.setText("Hired Date: " + employee.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM" +
                "-dd")));
        lbEmployeeID.setText("Employee ID: " + String.valueOf(employee.getEmployeeId()));
        lbAnnualLeave.setText("Annual Leave: " + String.valueOf(employee.getAnnualLeave()));

        if (employee.getManagerId() != 0) {
            var manager = employeeDao.getEmployeeByEmployeeId(employee.getManagerId());
            lbManagerName.setText("Manager: " + manager.getFullName());
        } else {
            lbManagerName.setText("Manager: none");
        }

        txtFullName.setText(employee.getFullName());
        txtDepartment.setText(department.getDepTitle());
        switch (employee.getGender()) {
            case 1 -> txtGender.setText("male");
            case 0 -> txtGender.setText("female");
            case -1 -> txtGender.setText("undisclosed");
            default -> txtGender.setText("error");
        }
        txtBirthdate.setText(employee.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private void filterHistory(List<Integer> allYears) {
        var centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        var count = 0;
        cbGroupByYear.addItem("...");
        while (allYears.size() > count) {
            cbGroupByYear.addItem(allYears.get(count));
            count++;
        }
    }

    private void cbGroupByYearActionPerformed(ActionEvent e) {
        List<AnnualLeave> listSelectedByYear = new ArrayList<>();
        if (Objects.equals(cbGroupByYear.getSelectedItem().toString(), "...")) {
            listSelectedByYear = annualLeaveDao.getListAnnualLeaveByEmployeeID(employeeID);
        } else {
            var yearSelected = (int) cbGroupByYear.getSelectedItem();
            listSelectedByYear = annualLeaveDao.getListAnnualLeaveByYear(employeeID, yearSelected);
        }
        var centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        String[] collumnNames = {"Date Annual Leave", "Description", "Used(-)", "Accrued(+)", "Balance"};
        DefaultTableModel tableModel = new DefaultTableModel(collumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3, 4 -> false;
                    default -> true;
                };
            }
        };
        var count = 0;

        while (listSelectedByYear.size() > count) {
            var dateAnnualLeave = listSelectedByYear.get(count).getDateTimeOff();
            var descriptionAnnualLeave = listSelectedByYear.get(count).getDescriptionTimeOff();
            var usedAnnualLeave = listSelectedByYear.get(count).getUsed();
            var accruedAnnualLeave = listSelectedByYear.get(count).getAccrued();
            var balanceAnnualLeave = listSelectedByYear.get(count).getBalance();
            Object[] data = {dateAnnualLeave, descriptionAnnualLeave, usedAnnualLeave, accruedAnnualLeave,
                    balanceAnnualLeave};
            tableModel.addRow(data);
            count++;
        }
        tbAnnualLeave.setModel(tableModel);

        count = 0;
        while (count < tbAnnualLeave.getColumnCount()) {
            tbAnnualLeave.getColumnModel().getColumn(count).setCellRenderer(centerRenderer);
            count++;
        }
        tbAnnualLeave.repaint();
    }

    private void btnSendRequestActionPerformed(ActionEvent e) {

        var recentlyAcceptedRequest = requestDao.getRecentlyAcceptedRequestFromEmployeeID(employeeID);

            if (rLeaveDao.pendingCheckingByEmployeeID(employeeID)) {

                JOptionPane.showMessageDialog(null, "you already sent a request, please wait still your request is " +
                        "checked.");

            } else if(recentlyAcceptedRequest.getDateEnd() == null || LocalDate.now().compareTo( recentlyAcceptedRequest.getDateEnd() ) >= 0) {

                var lType = new LeaveType();
                lType = lTypeDao.getLeaveTypeInfoByName(cBLeaveType.getSelectedItem().toString());
                var employee = employeeDao.getEmployeeByEmployeeId(employeeID);
                var manager = employeeDao.getEmployeeByEmployeeId(employee.getManagerId());
                var admin = employeeDao.getEmployeeByEmployeeId(accountDao.getAdminID());
                var managerForSpecialLeaveTypes = employeeDao.getEmployeeByEmployeeId(departmentDao.getDepartmentChiefID(1));
                var requestForm = new RequestLeave();
                var check = 1;

                while (check > 0) {
                    requestForm.setEmployeeID(employeeID);
                    requestForm.setLeaveID(lType.getLeaveID());
                    if (jDateStartChooser.getDate() == null || jDateEndChooser.getDate() == null) {
                        JOptionPane.showMessageDialog(null, "please select days for Day start and Day end.");
                        break;
                    }

                    requestForm.setDateStart(jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
                    requestForm.setDateEnd(jDateEndChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
                    requestForm.setRequestStatus("pending");

                    if (lType.getLeaveID() == 1 || lType.getLeaveID() == 2) {
                        requestForm.setRequestTo(manager.getEmail());
                    } else {
                        if(departmentDao.getDepartmentChiefID(1) != employeeID){
                            requestForm.setRequestTo(managerForSpecialLeaveTypes.getEmail());
                        }else{
                            requestForm.setRequestTo(admin.getEmail());
                        }

                    }

                    var amount = 0;
                    if (jDateStartChooser.getDate().compareTo(jDateEndChooser.getDate()) != 0) {
                        if (jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate().getDayOfWeek() != DayOfWeek.SATURDAY ||
                                jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate().getDayOfWeek() != DayOfWeek.SUNDAY) {
                            amount = 1;
                        }
                        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
                        long diffDate =
                                jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate().datesUntil(
                                                jDateEndChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate())
                                        .filter(d -> !weekend.contains(d.getDayOfWeek()))
                                        .count();
                        amount += Math.toIntExact(diffDate);
                    }

                    if ((double) amount <= employee.getAnnualLeave()) {
                        requestForm.setAmount(amount);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "exceeded value of annual leave: " + Math.round(employee.getAnnualLeave()));
                        break;
                    }

                    if (txtARequestDescription.getText().trim().length() == 0 ||
                            txtARequestDescription.getText() == null) {
                        JOptionPane.showMessageDialog(null, "please do not leave this description empty.");
                        break;
                    }

                    if (txtARequestDescription.getText().trim().length() > 200) {
                        JOptionPane.showMessageDialog(null, "maximum 200 letters is allowed in description.");
                        break;
                    }

                    requestForm.setRequestDescription(txtARequestDescription.getText().trim());
                    rLeaveDao.insertRequestLeave(requestForm);
                    this.showInbox();
                    SendMail.sendMailForRequestLeave(requestForm);
                    check = 0;
                }
            }else{
                JOptionPane.showMessageDialog(null,"your recently accepted request still not over.");
            }
    }

    //Switch Tag
    private void btnPersonalActionPerformed(MouseEvent e) {
        p1.setVisible(true);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);
    }

    private void btnRequestActionPerformed(ActionEvent e) {
        p1.setVisible(false);
        p2.setVisible(true);
        p3.setVisible(false);
        p4.setVisible(false);
    }

    private void btnHistoryActionPerformed(MouseEvent e) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(true);
        p4.setVisible(false);
    }

    private void btnInboxActionPerformed(MouseEvent e) {
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(true);
    }

//    JDate chooser
    private void jDateEndChooserPropertyChange(PropertyChangeEvent evt) {
        jDateStartChooser.getJCalendar().setMaxSelectableDate(jDateEndChooser.getDate());
    }

    private void jDateStartChooserPropertyChange(PropertyChangeEvent evt) {
        jDateEndChooser.setEnabled(true);
        jDateEndChooser.getJCalendar().setMinSelectableDate(jDateStartChooser.getDate());
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
