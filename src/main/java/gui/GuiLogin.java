package gui;

import entity.Account;
import entity.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiLogin extends JFrame {
    private JTextField txtUseName;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JPanel contentPane;

    /**
     * Launch
     * Please read README file before you run this app
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GuiLogin frame = new GuiLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GuiLogin() {
//        contentPane = new JPanel();
//        Enable this line above if get trouble with "Content pane cannot be set to null"
//        Sometimes app doesn't paint the UI, please Click "Recompile" or Crt+Shift+F9  to rebuild entire the source
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 250);
        setTitle("LEAVE MANAGEMENT");
        setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLoginActionPerformed(e);
            }
        });
    }

    private void btnLoginActionPerformed(ActionEvent e) {
        String userName = txtUseName.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();
        if (userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please input username or password");
        } else {
            Account user = Account.getAccountFromUserName(userName);
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                if (user.getRoleId() == 1) {
                    dispose();
                    var adm = new GuiAdmin(userName);
                    JOptionPane.showMessageDialog(null, "Login Success");
                } else if (user.getRoleId() == 2) {
                    dispose();
                    var emp = new GuiEmployee(user.getEmloyeeId());
                    JOptionPane.showMessageDialog(null, "Login Success");
                } else if (user.getRoleId() == 3) {
                    dispose();
                    var manager = new GuiManager(user.getEmloyeeId());
                    JOptionPane.showMessageDialog(null, "Login Success");
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong username or password");
                }
            }
        }
    }


}
