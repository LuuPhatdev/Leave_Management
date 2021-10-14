package gui;

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
        }else{

        }
    }


}
