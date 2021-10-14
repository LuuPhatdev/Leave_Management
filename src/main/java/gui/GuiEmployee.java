package gui;

import javax.swing.*;

public class GuiEmployee extends JFrame {
    private JPanel contentPane;

    private final String userName;

    public GuiEmployee(String userName) {
        this.userName = userName;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 637);
        setTitle("User Management");
        setVisible(true);
    }
}
