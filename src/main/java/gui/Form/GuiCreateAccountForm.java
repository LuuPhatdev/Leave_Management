package gui.Form;

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
    private JTextField txtFullName;
    private JButton btnCreate;
    private JLabel errFullName;
    private JTextField txtUserName;
    private JLabel errUserName;
    private JPasswordField txtConfirmPassword;
    private JTextField txtPassword;
    private JLabel errPassword;
    private JLabel errConfirmPassword;
    private JTextField txtRole;
    private JLabel errRole;

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
        var dao = new AccountDao();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();

        var account = new Account();
        account.setRoleId(Integer.parseInt(role));
        account.setUserName(userName);
        account.setPassword(password);
        dao.createAccount(account);
    }

}
