package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiAdmin extends JFrame {

    private final String userName;
    private JPanel contentPane;
    private JPanel sidePanel;
    private JPanel centerPanel;
    private JPanel p1;
    private JPanel btn1;
    private JPanel p2;
    private JPanel btn2;
    private JPanel btnLogOut;
    private JPanel btnAddUser;
    private JPanel btnUpdate;
    private JPanel btnDelete;

    public GuiAdmin(String userName) {
        this.userName = userName;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 637);
        setTitle("User Management");
        setVisible(true);

        btn1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btn1ActionPerformed(e);
            }
        });

        btn2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btn2ActionPerformed(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        btnLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnLogOutActionPerformed(e);
            }
        });
    }

    private void btn1ActionPerformed(MouseEvent e) {
        p1.setVisible(true);
        p2.setVisible(false);
    }

    private void btn2ActionPerformed(MouseEvent e) {
        p1.setVisible(false);
        p2.setVisible(true);
    }

    private void btnLogOutActionPerformed(MouseEvent e) {
        int q = JOptionPane.showOptionDialog(null, "Are you sure exit?", "Exit", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (q == JOptionPane.YES_OPTION) {
            this.dispose();
            GuiLogin mainPane = new GuiLogin();
            mainPane.setVisible(true);
        }
    }
}
