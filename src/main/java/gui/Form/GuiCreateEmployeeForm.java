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

public class GuiCreateEmployeeForm extends JFrame {

    private final GuiAdmin guiAdmin;
    private JPanel contentPane;
    private JPanel formGroup;
    private JTextField txtDepartmentId;
    private JTextField txtFullname;
    private JTextField txtDateOfBirth;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtManagerId;
    private JButton btnCreateEmployee;
    private JDateChooser jDateStartChooser;
    private JDateChooser JDateStart;
    private JDateChooser JDateDateOfBirth;
    private JComboBox JcomboboxGender;

    public GuiCreateEmployeeForm(GuiAdmin guiAdmin) {
        this.guiAdmin = guiAdmin;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 250);
        setLocationRelativeTo(null);
        setTitle("Create Employee");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GuiCreateEmployeeForm.this.guiAdmin.setEnabled(true);
            }
        });

        btnCreateEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCreateEmployeeActionPerformed(e);
            }
        });
    }

    private void btnCreateEmployeeActionPerformed(ActionEvent e) {
        try{
            var dao = new EmployeeDao();

            var departmentid = Integer.parseInt(txtDepartmentId.getText());
            var fullName = txtFullname.getText();
            var gender = Objects.requireNonNull(JcomboboxGender.getSelectedItem()).toString();
            var dateOfBirth = JDateDateOfBirth.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            var phone = txtPhone.getText();
            var email = txtEmail.getText();
            var dateStart = JDateStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            var annualLeave = 0;
            var managerId = Integer.parseInt(txtManagerId.getText());

            switch (gender) {
                case "Male" -> gender = String.valueOf(1);
                case "Female" -> gender = String.valueOf(0);
                case "Other" -> gender = String.valueOf(-1);
                default -> gender = ("Err");
            }

            Employee employee = new Employee();
            employee.setDepartmentId(departmentid);
            employee.setFullName(fullName);
            employee.setGender(Integer.parseInt(gender));
            employee.setDateOfBirth(dateOfBirth);
            employee.setPhone(phone);
            employee.setEmail(email);
            employee.setDateStart(dateStart);
            employee.setAnnualLeave(annualLeave);
            employee.setManagerId(managerId);
            dao.crudEmployee(employee);
            guiAdmin.tableShowEmployee.repaint();
        }catch (Exception em){
            JOptionPane.showMessageDialog(null,"Please enter Employee info!");
        }
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
