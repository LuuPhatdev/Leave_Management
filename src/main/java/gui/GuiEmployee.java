package gui;

import dao.DepartmentDao;
import dao.EmployeeDao;

import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class GuiEmployee extends JFrame {
    private final int employeeID;
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
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
    }
}
