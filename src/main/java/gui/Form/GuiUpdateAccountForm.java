package gui.Form;

import dao.AccountDao;
import entity.Account;
import gui.GuiFakeAdmin;

import javax.swing.*;
import java.awt.event.*;

public class GuiUpdateAccountForm extends JFrame {
    private final GuiFakeAdmin guiFakeAdmin;
    private JPanel contentPane;
    private JPanel topPanel;
    public JTextField txtEmployeeId;
    public JTextField txtRoleId;
    public JTextField txtUserName;
    public JTextField txtPassword;
    private JButton btnDiscard;
    private JButton btnSave;

    public GuiUpdateAccountForm(GuiFakeAdmin guiFakeAdmin) {
        this.guiFakeAdmin = guiFakeAdmin;

        txtEmployeeId.setEditable(false);
        txtRoleId.setEditable(false);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 300, 250);
        setLocationRelativeTo(null);
        setTitle("Update Account");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GuiUpdateAccountForm.this.guiFakeAdmin.setEnabled(true);
            }
        });

        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateAccountActionPerformed(e);
            }
        });

        btnDiscard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDiscardActionPerformed(e);
            }
        });
    }

    private void btnDiscardActionPerformed(ActionEvent e) {
        txtRoleId.setText("");
        txtEmployeeId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        btnSave.setEnabled(false);
    }

    private void updateAccountActionPerformed(ActionEvent e) {
        var dao = new AccountDao();
        var employeeId = txtEmployeeId.getText();
        var userName = txtUserName.getText();
        var password = txtPassword.getText();

        var account = new Account();
        account.setEmloyeeId(Integer.parseInt(employeeId));
        account.setUserName(userName);
        account.setPassword(password);
        dao.updateAccount(account);
        guiFakeAdmin.showListAccount();
    }
}
