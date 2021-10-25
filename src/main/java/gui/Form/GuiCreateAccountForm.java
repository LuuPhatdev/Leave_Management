package gui.Form;

import common.SendMail;
import dao.AccountDao;
import entity.Account;
import gui.GuiAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GuiCreateAccountForm extends JFrame {

    private GuiAdmin guiAdmin;
    private JPanel contentPane;
    private JButton btnCreate;
    private JTextField txtUserName;
    private JLabel errUserName;
    private JTextField txtPassword;
    private JLabel errPassword;
    private JTextField txtRole;
    private JLabel errRole;
    private JTextField txtEmployeeId;

    public GuiCreateAccountForm(GuiAdmin guiAdmin) {
        this.guiAdmin = guiAdmin;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        setTitle("Create User");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guiAdmin.setEnabled(true);
            }
        });

        btnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCreateActionPerformed(e);
            }
        });
    }

    private void btnCreateActionPerformed(ActionEvent e) {
        try {
            var dao = new AccountDao();
            var employeeId = txtEmployeeId.getText();
            var userName = txtUserName.getText();
            var password = txtPassword.getText();
            var role = txtRole.getText();

            var account = new Account();
            account.setEmloyeeId(Integer.parseInt(employeeId));
            account.setRoleId(Integer.parseInt(role));
            account.setUserName(userName);
            account.setPassword(password);
            dao.createAccount(account);

            SendMail.sendClientInfo(account);
        } catch (Exception em) {
            JOptionPane.showMessageDialog(null, "Please enter Account info!");
        }
    }
}
