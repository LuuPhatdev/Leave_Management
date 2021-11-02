package gui.Form;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import dao.AccountDao;
import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Employee;
import gui.GuiFakeAdmin;
import helper.RegexConst;
import helper.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class GuiCreateEmployeeForm extends JFrame {

    private final GuiFakeAdmin guiFakeAdmin;
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
    private JLabel lbEmailError;
    private JLabel lbFullNameError;
    private JLabel lbDepartmentIDError;
    private JLabel lbManagerIDError;

    public GuiCreateEmployeeForm(GuiFakeAdmin guiFakeAdmin) {
        this.guiFakeAdmin = guiFakeAdmin;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 300);
        setLocationRelativeTo(null);
        setTitle("Create Employee");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GuiCreateEmployeeForm.this.guiFakeAdmin.setEnabled(true);
                GuiCreateEmployeeForm.this.guiFakeAdmin.showListEmployee();
            }
        });

        btnCreateEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCreateEmployeeActionPerformed(e);
            }
        });

//        focus switching

        formGroup.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                formGroup.requestFocus();
            }
        });
    }

    private void btnCreateEmployeeActionPerformed(ActionEvent e) {
        var check = 1;
        while (check > 0){
            var dao = new EmployeeDao();
            var departmentDao = new DepartmentDao();

            var departmentid = 0;
            if( Validation.checkInput(txtDepartmentId.getText(), RegexConst.INTERGER) && !txtDepartmentId.getText().trim().isEmpty() ){
                if( departmentDao.checkIfExistDepartmentByID( Integer.parseInt( txtDepartmentId.getText().trim() ) ) ){
                    departmentid = Integer.parseInt(txtDepartmentId.getText());
                }else{
                    JOptionPane.showMessageDialog(null, "Department id not exists.");
                    break;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Department id field must be filled and only in numbers.");
                break;
            }
            var fullName = "";
            if(Validation.checkInput(txtFullname.getText(), RegexConst.NAME, 1, 25)){
                fullName = txtFullname.getText();
            }else{
                JOptionPane.showMessageDialog(null, "Name must be filled and under 25 letters, no special letters.");
                break;
            }
            var gender = Objects.requireNonNull(JcomboboxGender.getSelectedItem()).toString();
            var dateOfBirth = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(JDateDateOfBirth.getDate() != null){
                dateOfBirth = JDateDateOfBirth.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }else {
                JOptionPane.showMessageDialog(null, "Please input date of birth.");
                break;
            }
            var email = "";
            var phone = "";
            if(!Validation.checkInput(txtEmail.getText(), RegexConst.EMAIL, 1, 50)){
                JOptionPane.showMessageDialog(null, "Email must be in correct format and under 50 letters.");
                break;
            }else if( !Validation.checkInput(txtPhone.getText(), RegexConst.INTERGER, 1, 15) ){
                JOptionPane.showMessageDialog(null, "Phone must only in number and under 15 letters.");
                break;
            }else{
                if(dao.checkIfExistsEmployeeEmailOrPhone(txtEmail.getText().trim(), txtPhone.getText().trim()) == 1){
                    JOptionPane.showMessageDialog(null, "Email is already exists");
                    break;
                }else if(dao.checkIfExistsEmployeeEmailOrPhone(txtEmail.getText().trim(), txtPhone.getText().trim()) == 2){
                    JOptionPane.showMessageDialog(null, "Phone is already exists");
                    break;
                }else {
                    email = txtEmail.getText();
                    phone = txtPhone.getText();
                }
            }
            var dateStart = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(JDateStart.getDate() != null){
                dateStart = JDateStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }else{
                JOptionPane.showMessageDialog(null, "Please input date start.");
                break;
            }
            var annualLeave = 0;
            var managerId = 0;
            if( !txtManagerId.getText().trim().isEmpty() ){
                if(Validation.checkInput(txtManagerId.getText(), RegexConst.INTERGER) ){
                    var accountDao = new AccountDao();
                    var acc = accountDao.getAccByEmployeeID(Integer.parseInt(txtManagerId.getText().trim()) );
                    if(dao.checkIfExistsEmployeeByID( Integer.parseInt(txtManagerId.getText().trim()) ) && acc.getRoleId() == 3){
                        managerId = Integer.parseInt(txtManagerId.getText());
                    }else if(acc.getRoleId() == 2 || acc.getRoleId() == 1){
                        JOptionPane.showMessageDialog(null, "This employee is not manager.");
                        break;
                    }else{
                        JOptionPane.showMessageDialog(null, "Employee id in manager field not exists.");
                        break;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Employee id in manager field must be in numbers.");
                    break;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Manager ID must be filled");
                break;
            }

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

            check --;
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
    //phat implement solved conflict
}
