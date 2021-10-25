package gui.Form;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import dao.EmployeeDao;
import entity.Employee;
import gui.GuiAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.ZoneId;
import java.util.Objects;

public class GuiUpdateEmloyee extends JFrame {

    private final GuiAdmin guiAdmin;
    private JPanel contentPane;
    private JPanel formGroup;
    public JTextField txtDepartmentId;
    public JTextField txtFullname;
    public JTextField txtDateOfBirth;
    public JTextField txtPhone;
    public JTextField txtEmail;
    public JTextField txtManagerId;
    private JButton btnUpdateEmployee;
    public JDateChooser jDateStartChooser;
    public JDateChooser JDateStart;
    public JDateChooser JDateDateOfBirth;
    public JTextField txtEmployeeId;
    public JTextField txtAnnualLeave;
    public JComboBox JcomboboxGender;

    public GuiUpdateEmloyee(GuiAdmin guiAdmin) {
        this.guiAdmin = guiAdmin;

        txtEmployeeId.setEditable(false);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 250);
        setLocationRelativeTo(null);
        setTitle("Update Employee");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GuiUpdateEmloyee.this.guiAdmin.setEnabled(true);
            }
        });

//        btnUpdateEmployee.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                btnCreateEmployeeActionPerformed(e);
//            }
//        });

        btnUpdateEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnUpdateEmployeeActionPerfomerd(e);
            }
        });
    }

    private void btnUpdateEmployeeActionPerfomerd(ActionEvent e) {
            var dao = new EmployeeDao();
            var employeeId = Integer.parseInt(txtEmployeeId.getText());
            var departmentid = Integer.parseInt(txtDepartmentId.getText());
            var fullName = txtFullname.getText();
            var gender = Objects.requireNonNull(JcomboboxGender.getSelectedItem()).toString();
            var dateOfBirth = JDateDateOfBirth.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            var phone = txtPhone.getText();
            var email = txtEmail.getText();
            var dateStart = JDateStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            var annualLeave = Double.parseDouble(txtAnnualLeave.getText());
            var managerId = Integer.parseInt(txtManagerId.getText());
            System.out.println(employeeId);
            switch (gender) {
                case "Male" -> gender = String.valueOf(1);
                case "Female" -> gender = String.valueOf(0);
                case "Other" -> gender = String.valueOf(-1);
                default -> gender = ("Err");
            }

            Employee employee = new Employee();
            employee.setEmployeeId(employeeId);
            employee.setDepartmentId(departmentid);
            employee.setFullName(fullName);
            employee.setGender(Integer.parseInt(gender));
            employee.setDateOfBirth(dateOfBirth);
            employee.setPhone(phone);
            employee.setEmail(email);
            employee.setDateStart(dateStart);
            employee.setAnnualLeave(annualLeave);
            employee.setManagerId(managerId);
            dao.updateSingleEmployee(employee);
            guiAdmin.showListEmployee();
            this.dispose();
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        JDateStart = new JDateChooser();
        JDateDateOfBirth = new JDateChooser();

        JTextFieldDateEditor editorStart = (JTextFieldDateEditor) JDateStart.getDateEditor();
        JTextFieldDateEditor editorDateOfBirth = (JTextFieldDateEditor) JDateDateOfBirth.getDateEditor();

        editorDateOfBirth.setEditable(false);
        editorStart.setEditable(false);
    }
}
