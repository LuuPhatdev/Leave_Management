package gui;

import dao.AccountDao;
import entity.Account;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class GuiUpdate extends JFrame {
    private final GuiAdmin guiAdmin;
    private JPanel contentPane;
    private JPanel topPanel;
    private JTextField txtEmployeeId;
    private JTextField txtRoleId;
    private JTextField txtUserName;
    private JTextField txtPassword;
    private JTable tableShowlistAccount;
    private JButton btnSearch;
    private JTextField txtSearchByName;
    private JButton btnDiscard;
    private JButton btnSave;

    public GuiUpdate(GuiAdmin guiAdmin) {
        this.guiAdmin = guiAdmin;
        this.showListAccount();

        txtEmployeeId.setEditable(false);
        txtRoleId.setEditable(false);
        btnSave.setEnabled(false);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 400);
        setLocationRelativeTo(null);
        setTitle("Update Employee");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GuiUpdate.this.guiAdmin.setEnabled(true);
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

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSearchActionPerformed(e);
            }
        });

        tableShowlistAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                rowSelectingActionPerformed(e);
            }
        });
    }

    private void btnSearchActionPerformed(ActionEvent e) {
        if (txtSearchByName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please input User Name");
        } else {
            try {
                Account acc = Account.getAccountFromUserName(txtSearchByName.getText().trim().replaceAll(("\\s"), ""));
                String[] columnTitle = {"EMPLOYEE ID", "ROLE ID", "USER NAME", "PASSWORD"};
                DefaultTableModel model = new DefaultTableModel(columnTitle, 0);

                model.addRow(new Object[]{
                        acc.getEmloyeeId(),
                        acc.getRoleId(),
                        acc.getUserName(),
                        acc.getPassword()
                });
                tableShowlistAccount.setModel(model);
            }catch (Exception em){
              JOptionPane.showMessageDialog(null,"Account does not exists!");
              txtSearchByName.setText("");
            }

        }
    }

    private void btnDiscardActionPerformed(ActionEvent e) {
        txtRoleId.setText("");
        txtEmployeeId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        btnSave.setEnabled(false);
        showListAccount();
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
    }

    private void rowSelectingActionPerformed(MouseEvent e) {
        var rowindex = tableShowlistAccount.getSelectedRow();
        txtEmployeeId.setText(tableShowlistAccount.getValueAt(rowindex, 0).toString());
        txtRoleId.setText(tableShowlistAccount.getValueAt(rowindex, 1).toString());
        txtUserName.setText(tableShowlistAccount.getValueAt(rowindex, 2).toString());
        txtPassword.setText(tableShowlistAccount.getValueAt(rowindex, 3).toString());
        btnSave.setEnabled(true);
    }

    private void showListAccount() {
        AccountDao usersDao = new AccountDao();
        String[] columnTitle = {"EMPLOYEE ID", "ROLE ID", "USER NAME", "PASSWORD"};
        DefaultTableModel model = new DefaultTableModel(columnTitle, 0);

        usersDao.getListAccounts().forEach(user -> model.addRow(new Object[]{
                user.getEmloyeeId(),
                user.getRoleId(),
                user.getUserName(),
                user.getPassword(),}));
        tableShowlistAccount.setModel(model);

        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem viewProfile = new JMenuItem("Delete");

        viewProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
            }
        });

        popupMenu.add(viewProfile);
        tableShowlistAccount.setComponentPopupMenu(popupMenu);
    }

    private void deleteAccount() {
        var dao = new AccountDao();
        var employeeId = txtEmployeeId.getText();

        var account = new Account();
        account.setEmloyeeId(Integer.parseInt(employeeId));
        dao.deleteAccount(account);
        showListAccount();

        txtRoleId.setText("");
        txtEmployeeId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
    }
}
