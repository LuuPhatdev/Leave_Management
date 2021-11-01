package gui;

import entity.Account;
import helper.RegexConst;
import helper.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GuiLogin extends JFrame {
    private JTextField txtUseName;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JPanel contentPane;

    private JLabel lbUserNameError;
    private JLabel lbPasswordError;
    private JPanel loginPane;

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
        //-- ContentPane = new JPanel();
        //-- Enable this line above if get trouble with "Content pane cannot be set to null"
        //-- Sometimes app doesn't paint the UI, please Click "Recompile" or Crt+Shift+F9  to rebuild entire the source
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        loginPane.setBounds(100, 100, 450, 250);
        setLocationRelativeTo(null);
        setTitle("LEAVE MANAGEMENT");
        setVisible(true);

        //Edit JTextfield
        txtUseName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        txtPassword.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLoginActionPerformed(e);
            }
        });

        //-- Errors Warning
        txtUseName.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                TxtUseNameFocusLost(e);
            }
        });
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                TxtPasswordFocusLost(e);
            }
        });

        //-- Focus Switching
        txtUseName.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                TxtUseNameKeyPressed(e);
            }
        });
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                TxtPasswordKeyPressed(e);
            }
        });
        loginPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                loginPane.requestFocus();
            }
        });
    }

    //-- Errors Warning Action Listener(s)
    private void TxtUseNameFocusLost(FocusEvent e) {
        var str = txtUseName.getText();
        if (Validation.checkInput(str, RegexConst.USER, 5, 15)) {
            lbUserNameError.setText("");
        } else if (str.length() < 5 || str.length() > 15) {
            lbUserNameError.setText("Username can only between 5 and 15 letters.");
        } else {
            lbUserNameError.setText("Username must not have special letters, only alphabetic and numbers.");
        }
    }
    private void TxtPasswordFocusLost(FocusEvent e) {
        var str = String.valueOf(txtPassword.getPassword());
        if (Validation.checkInput(str, RegexConst.PASS, 5, 15)) {
            lbPasswordError.setText("");
        } else if (str.length() < 5 || str.length() > 15) {
            lbPasswordError.setText("Password can only between 5 and 15 letters.");
        } else {
            lbPasswordError.setText("Password must not have special letters, only alphabetic and numbers.");
        }
    }

    //-- Focus Switch Action Listener(s)
    private void TxtUseNameKeyPressed(KeyEvent e) {
        var k = e.getKeyCode();
        if (k == KeyEvent.VK_ENTER) {
            txtPassword.requestFocus();
        }
    }
    private void TxtPasswordKeyPressed(KeyEvent e) {
        var k = e.getKeyCode();
        if (k == KeyEvent.VK_ENTER) {
            txtUseName.requestFocus();
        }
    }

    //-- Login Actionlistener(s)
    private void btnLoginActionPerformed(ActionEvent e) {
        String userName = txtUseName.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();
        if (userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please input username or password");
        } else if (!Validation.checkInput(userName, RegexConst.USER, 5, 15) || !Validation.checkInput(password,
                RegexConst.PASS, 5, 15)) {
            JOptionPane.showMessageDialog(null, "UserName and/or Password needed to be in between 5 and 15 letters " +
                    "and must not contains special letter(s)");
        } else {
            Account user = Account.getAccountFromUserName(userName);
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())) {
                if (user.getRoleId() == 1) {
                    dispose();
//                    var adm = new GuiAdmin(userName);
                    var adm = new GuiFakeAdmin(user.getEmloyeeId(), userName);
                    JOptionPane.showMessageDialog(null, "Login Success");
                } else if (user.getRoleId() == 2) {
                    dispose();
//                    var emp = new GuiEmployee(user.getEmloyeeId());
                    var fakeEmployee = new GuiFakeEmployee(user.getEmloyeeId(), userName);
                    JOptionPane.showMessageDialog(null, "Login Success");
                } else if (user.getRoleId() == 3) {
                    dispose();
//                    var fakeManagement = new GuiManager(user.getEmloyeeId());
                    var fakeEmployee = new GuiFakeManager(user.getEmloyeeId(), userName);
                    JOptionPane.showMessageDialog(null, "Login Success");
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong username or password");
                }
            }
        }
    }
}
