package gui;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import dao.DepartmentDao;
import dao.EmployeeDao;
import dao.LeaveTypeDao;
import dao.RequestLeaveDao;
import entity.LeaveType;
import entity.RequestLeave;
import helper.Validation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

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

    public GuiEmployee(int empID) {
        this.employeeID = empID;
        var employeeDao = new EmployeeDao();
        var employee = employeeDao.getEmployeeByEmployeeId(employeeID);
        var departmentDao = new DepartmentDao();
        var department = departmentDao.getDepartmentInfo(employee.getDepartmentId());

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 637);
        setLocationRelativeTo(null);
        setTitle("User Management");
        setVisible(true);

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
    }

    private void jDateEndChooserPropertyChange(PropertyChangeEvent evt) {
        jDateStartChooser.getJCalendar().setMaxSelectableDate(jDateEndChooser.getDate());
    }

    private void jDateStartChooserPropertyChange(PropertyChangeEvent evt) {
        jDateEndChooser.setEnabled(true);
        jDateEndChooser.getJCalendar().setMinSelectableDate(jDateStartChooser.getDate());
    }

    private void btnSendRequestActionPerformed(ActionEvent e){
        var lType = new LeaveType();
        var lTypeDao = new LeaveTypeDao();
        lType = lTypeDao.getLeaveTypeInfoByName(cBLeaveType.getSelectedItem().toString());
        var employeeDao = new EmployeeDao();
        var employee = employeeDao.getEmployeeByEmployeeId(employeeID);
        var manager = employeeDao.getEmployeeByEmployeeId(employee.getManagerId());
        var requestForm = new RequestLeave();
        var rLeaveDao = new RequestLeaveDao();
        var check = 1;

        while(check > 0){
            requestForm.setEmployeeID(employeeID);
            requestForm.setLeaveID(lType.getLeaveID());
            if(jDateStartChooser.getDate()==null||jDateEndChooser.getDate()==null){
                JOptionPane.showMessageDialog(null, "please select days for Day start and Day end.");
                break;
            }
            requestForm.setDateStart(jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
            requestForm.setDateEnd(jDateEndChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
            requestForm.setRequestStatus("pending");
            requestForm.setRequestTo(manager.getEmail());

            var amount = 0;
            if(jDateStartChooser.getDate().compareTo(jDateEndChooser.getDate())!=0){
//                getDayOfWeek()
                if(jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate().getDayOfWeek()!=DayOfWeek.SATURDAY||
                        jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate().getDayOfWeek()!=DayOfWeek.SUNDAY){
                    amount = 1;
                }
                Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
                long diffDate = jDateStartChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate().datesUntil(
                                jDateEndChooser.getDate().toInstant().atZone(ZoneId.of("UTC")).toLocalDate())
                        .filter(d -> !weekend.contains(d.getDayOfWeek()))
                        .count();
                amount += Math.toIntExact(diffDate);
            }

            if(Double.valueOf(amount)<=employee.getAnnualLeave()){
                requestForm.setAmount(amount);
                employee.setAnnualLeave(employee.getAnnualLeave()-Double.valueOf(amount));
            }else{
                JOptionPane.showMessageDialog(null, "exceeded value of annual leave: "+Math.round(employee.getAnnualLeave()));
                break;
            }
            if(txtARequestDescription.getText().trim().length()==0||
                    txtARequestDescription.getText()==null){
                JOptionPane.showMessageDialog(null, "please do not leave this description empty.");
                break;
            }
            if(txtARequestDescription.getText().trim().length() > 200){
                JOptionPane.showMessageDialog(null, "maximum 200 letters is allowed in description.");
                break;
            }
            requestForm.setRequestDescription(txtARequestDescription.getText());
            rLeaveDao.insertRequestLeave(requestForm);
            employeeDao.updateSingleEmployee(employee);
            check = 0;
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
        jDateStartChooser.getJCalendar().setMinSelectableDate(new Date());
        jDateEndChooser.getJCalendar().setMinSelectableDate(new Date());
    }
}
