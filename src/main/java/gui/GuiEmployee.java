package gui;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import common.SendMail;
import dao.*;
import entity.AnnualLeave;
import entity.LeaveType;
import entity.RequestLeave;
import helper.Validation;

import javax.ejb.Schedule;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class GuiEmployee extends JFrame {
    private final int employeeID;
    private JPanel contentPane;
    private JTabbedPane request;
    private JPanel homePage;
    private JPanel info;
    private JLabel lbPhoneNumber;
    private JLabel lbEmail;
    private JLabel lbFillHiredDate;
    private JLabel lbEmployeeID;
    private JLabel lbManagerName;
    private JLabel lbFullName;
    private JTextField tfFullName;
    private JTextField tfDepartment;
    private JLabel lbDepartment;
    private JLabel lbGender;
    private JTextField tfGender;
    private JLabel lbBirthday;
    private JTextField tfBirthday;
    private JLabel lbAnnualLeave;
    private JLabel lbRequestLeave;
    private JComboBox cBLeaveType;
    private JLabel lbLeaveType;
    private JLabel lbdateStart;
    private JPanel requestLeave;
    private JDateChooser jDateStartChooser;
    private JLabel lbDateEnd;
    private JDateChooser jDateEndChooser;
    private JButton btnSendRequest;
    private JTextArea txtARequestDescription;
    private JLabel lbRequestDescription;
    private JPanel annualLeaveHistory;
    private JTable tbAnnualLeave;
    private JComboBox cbGroupByYear;
    private JButton btnLogOut;
    private EmployeeDao employeeDao = new EmployeeDao();
    private DepartmentDao departmentDao = new DepartmentDao();
    private AnnualLeaveDao annualLeaveDao = new AnnualLeaveDao();
    private LeaveTypeDao lTypeDao = new LeaveTypeDao();
    private RequestLeaveDao rLeaveDao = new RequestLeaveDao();
    private AccountDao accountDao = new AccountDao();

    public GuiEmployee(int empID) {
        this.employeeID = empID;
        var employee = employeeDao.getEmployeeByEmployeeId(employeeID);
        var department = departmentDao.getDepartmentInfo(employee.getDepartmentId());
        var allYears = annualLeaveDao.groupByYear();

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 637);
        setLocationRelativeTo(null);
        setTitle("User Management");
        setVisible(true);

//        Employee Info
        lbPhoneNumber.setText(String.valueOf(employee.getPhone()));
        lbEmail.setText(employee.getEmail());
        lbFillHiredDate.setText("Hired Date:" + employee.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM" +
                "-dd")));
        lbEmployeeID.setText("Employee ID:" + String.valueOf(employee.getEmployeeId()));
        lbAnnualLeave.setText("Annual Leave:" + String.valueOf(employee.getAnnualLeave()));

        if (employee.getManagerId() != 0) {
            var manager = employeeDao.getEmployeeByEmployeeId(employee.getManagerId());
            lbManagerName.setText("Manager:" + manager.getFullName());
        } else {
            lbManagerName.setText("Manager: none");
        }

        tfFullName.setText(employee.getFullName());
        tfDepartment.setText(department.getDepTitle());
        switch (employee.getGender()) {
            case 1 -> tfGender.setText("male");
            case 0 -> tfGender.setText("female");
            case -1 -> tfGender.setText("undisclosed");
            default -> tfGender.setText("error");
        }
        tfBirthday.setText(employee.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        btnSendRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSendRequestActionPerformed(e);
            }
        });

//        sendRequest
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

//        historyAnnualLeave
        var count = 0;
        cbGroupByYear.addItem("...");
        while (allYears.size() > count) {
            cbGroupByYear.addItem(allYears.get(count));
            count++;
        }


        String[] collumnNames = {"Date Annual Leave", "Description", "Used(-)", "Accrued(+)", "Balance"};
        var tableModel = new DefaultTableModel(collumnNames, 0){
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

        cbGroupByYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cbGroupByYearActionPerformed(e);
            }
        });

//        log out

        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BtnLogOutActionPerformed(e);
            }
        });
    }

//    log out action listener(s)

    private void BtnLogOutActionPerformed(ActionEvent e) {
        var login = new GuiLogin();
        dispose();
    }

//    send request action listener(s)

    private void btnSendRequestActionPerformed(ActionEvent e) {

        if(rLeaveDao.pendingCheckingByEmployeeID(employeeID)){

            JOptionPane.showMessageDialog(null, "you already sent a request, please wait still your request is checked.");

        }else{
            var lType = new LeaveType();
            lType = lTypeDao.getLeaveTypeInfoByName(cBLeaveType.getSelectedItem().toString());
            var employee = employeeDao.getEmployeeByEmployeeId(employeeID);
            var manager = employeeDao.getEmployeeByEmployeeId(employee.getManagerId());
            var admin = employeeDao.getEmployeeByEmployeeId(accountDao.getAdminID());
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
                    requestForm.setRequestTo(admin.getEmail());
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
                SendMail.sendMailForRequestLeave(requestForm);
                check = 0;
            }
        }
    }

    private void jDateEndChooserPropertyChange(PropertyChangeEvent evt) {
        jDateStartChooser.getJCalendar().setMaxSelectableDate(jDateEndChooser.getDate());
    }

    private void jDateStartChooserPropertyChange(PropertyChangeEvent evt) {
        jDateEndChooser.setEnabled(true);
        jDateEndChooser.getJCalendar().setMinSelectableDate(jDateStartChooser.getDate());
    }

//    annual leave history listener(s)

    private void cbGroupByYearActionPerformed(ActionEvent e) {
        List<AnnualLeave> listSelectedByYear = new ArrayList<>();
        if (Objects.equals(cbGroupByYear.getSelectedItem().toString(), "...")) {
            listSelectedByYear = annualLeaveDao.getListAnnualLeaveByEmployeeID(employeeID);
        } else {
            var yearSelected = (int) cbGroupByYear.getSelectedItem();
            listSelectedByYear = annualLeaveDao.getListAnnualLeaveByYear(employeeID, yearSelected);
        }

        String[] collumnNames = {"Date Annual Leave", "Description", "Used(-)", "Accrued(+)", "Balance"};
        DefaultTableModel tableModel = new DefaultTableModel(collumnNames, 0){
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
        tbAnnualLeave.repaint();
    }

//    custom ui compoments

    private void createUIComponents() {
        // TODO: place custom component creation code here
        jDateStartChooser = new JDateChooser();
        jDateEndChooser = new JDateChooser();
        JTextFieldDateEditor editorStart = (JTextFieldDateEditor) jDateStartChooser.getDateEditor();
        JTextFieldDateEditor editorEnd = (JTextFieldDateEditor) jDateEndChooser.getDateEditor();

        editorStart.setEditable(false);
        editorEnd.setEditable(false);
        jDateEndChooser.setEnabled(false);
        jDateStartChooser.getJCalendar().setMinSelectableDate(new Date());
        jDateEndChooser.getJCalendar().setMinSelectableDate(new Date());
    }
}
