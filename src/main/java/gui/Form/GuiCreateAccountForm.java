package gui.Form;

import common.SendMail;
import dao.AccountDao;
import dao.EmployeeDao;
import entity.Account;
import gui.GuiFakeAdmin;
import helper.RegexConst;
import helper.Validation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.stream.IntStream;

public class GuiCreateAccountForm extends JFrame {

    private final GuiFakeAdmin guiFakeAdmin;
    private JPanel contentPane;
    private JButton btnCreate;
    private JTextField txtUserName;
    private JLabel errUserName;
    private JTextField txtPassword;
    private JLabel errPassword;
    private JTextField txtRole;
    private JLabel errRole;
    private JTextField txtEmployeeId;
    private JLabel errEmployeeID;

    public GuiCreateAccountForm(GuiFakeAdmin guiFakeAdmin) {
        this.guiFakeAdmin = guiFakeAdmin;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        setTitle("Create User");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guiFakeAdmin.setEnabled(true);
                guiFakeAdmin.showListAccount();
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCreateActionPerformed(e);
            }
        });
    }

    private void btnCreateActionPerformed(ActionEvent e) {
        var check = 1;
        while (check > 0){
            var dao = new AccountDao();
            var employeeDao = new EmployeeDao();

            var employeeId = "";
            if(Validation.checkInput(txtEmployeeId.getText(), RegexConst.INTERGER) && !txtEmployeeId.getText().trim().isEmpty()){
                if(employeeDao.checkIfExistsEmployeeByID(Integer.parseInt(txtEmployeeId.getText().trim()))){
                    if(dao.checkIfExistsAccountByEmployeeID( Integer.parseInt( txtEmployeeId.getText().trim() ) ) ){
                        JOptionPane.showMessageDialog(null, "This Employee already have an account.");
                        break;
                    }else{
                        employeeId = txtEmployeeId.getText();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "This Employee id is not exists.");
                    break;
                }

            }else{
                JOptionPane.showMessageDialog(null, "Employee ID must be filled and only in numbers.");
                break;
            }
            var userName = "";
            if(Validation.checkInput(txtUserName.getText(), RegexConst.USER, 5, 15) && !txtUserName.getText().trim().isEmpty()){
                if(dao.checkIfExistsAccountByUserName(txtUserName.getText().trim())){
                    JOptionPane.showMessageDialog(null, "Username is already exists.");
                    break;
                }else{
                    userName = txtUserName.getText();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Username must not have special letters and must be filled.");
                break;
            }
            var password = txtPassword.getText();
            var role = "";
            var roleList = new int[] {1, 2, 3};
            if(Validation.checkInput(txtRole.getText(), RegexConst.INTERGER) && !txtRole.getText().trim().isEmpty()){
                boolean contains = IntStream.of(roleList).anyMatch(x -> x == Integer.parseInt(txtRole.getText().trim()));
                if(contains){
                    role = txtRole.getText().trim();
                }else{
                    JOptionPane.showMessageDialog(null, "role id not exists.");
                    break;
                }
            }else{
                JOptionPane.showMessageDialog(null, "role id must be filled and only in numbers.");
                break;
            }

            var account = new Account();
            account.setEmloyeeId(Integer.parseInt(employeeId));
            account.setRoleId(Integer.parseInt(role));
            account.setUserName(userName);
            account.setPassword(password);
            SendMail.sendClientInfo(account);
            dao.createAccount(account);
            check--;
        }
    }
    //commit here
}
