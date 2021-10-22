package gui;

import dao.AccountDao;
import entity.Account;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

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
        setBounds(100, 100, 550, 250);
        setLocationRelativeTo(null);
        setTitle("Update Employee");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GuiUpdate.this.guiAdmin.setEnabled(true);
            }
        });

        tableShowlistAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tableShowListAccountActionPerformed(e);
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
    }

    private void btnSearchActionPerformed(ActionEvent e) {
        if (txtSearchByName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please input User Name");
        } else {
            Account acc = Account.getAccountFromUserName(txtSearchByName.getText());
            var model = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int column) {
                    return String.class;
                }

                @Override
                public boolean isCellEditable(int row, int col) {
                    return switch (col) {
                        case 0, 1, 2, 3 -> false;
                        default -> true;
                    };
                }
            };

            model.addColumn("EMPLOYEE ID");
            model.addColumn("ROLE ID");
            model.addColumn("USER NAME");
            model.addColumn("PASSWORD");

            model.addRow(new Object[]{
                    acc.getEmloyeeId(),
                    acc.getRoleId(),
                    acc.getUserName(),
                    acc.getPassword()
            });
            tableShowlistAccount.setModel(model);
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

    private void tableShowListAccountActionPerformed(MouseEvent e) {
        var rowindex = tableShowlistAccount.getSelectedRow();
        txtEmployeeId.setText(tableShowlistAccount.getValueAt(rowindex, 0).toString());
        txtRoleId.setText(tableShowlistAccount.getValueAt(rowindex, 1).toString());
        txtUserName.setText(tableShowlistAccount.getValueAt(rowindex, 2).toString());
        txtPassword.setText(tableShowlistAccount.getValueAt(rowindex, 3).toString());
        btnSave.setEnabled(true);
    }

    private void showListAccount() {
        AccountDao usersDao = new AccountDao();
        var model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return switch (col) {
                    case 0, 1, 2, 3 -> false;
                    default -> true;
                };
            }
        };

        model.addColumn("EMPLOYEE ID");
        model.addColumn("ROLE ID");
        model.addColumn("USER NAME");
        model.addColumn("PASSWORD");

        usersDao.getListAccounts().forEach(user -> model.addRow(new Object[]{
                user.getEmloyeeId(),
                user.getRoleId(),
                user.getUserName(),
                user.getPassword(),}));
        tableShowlistAccount.setModel(model);
    }
}
