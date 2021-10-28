package gui;

import common.SendMail;
import dao.AnnualLeaveDao;
import dao.EmployeeDao;
import dao.RequestLeaveDao;
import entity.AnnualLeave;
import entity.Employee;
import gui.Form.GuiUpdateEmloyee;

import javax.management.Notification;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GuiRequestChecking extends JFrame {
    private JPanel contentPane;
    private JButton btnAccept;
    private JButton btnDeny;
    private JLabel lbEmployeeID;
    private JLabel lbRequestID;
    private JLabel lblEmployeeRequest;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private RequestLeaveDao requestLeaveDao = new RequestLeaveDao();
    private AnnualLeaveDao annualLeaveDao = new AnnualLeaveDao();
    private EmployeeDao employeeDao = new EmployeeDao();

    public GuiRequestChecking(JFrame frame, JTable table, DefaultTableModel tableModel) {
        super(String.valueOf(frame));
        this.table = table;
        this.tableModel = tableModel;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        setTitle("User Management");
        setLocationRelativeTo(null);
        setVisible(true);

        lblEmployeeRequest.setText(table.getModel().getValueAt(table.getSelectedRow(),1).toString() + "Requested Time Off");
        lbEmployeeID.setText("Employee ID: " + table.getModel().getValueAt(table.getSelectedRow(), 2).toString());
        lbRequestID.setText("Request ID: " + table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
        btnAccept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BtnAcceptActionPerformed(e);
            }
        });

        btnDeny.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BtnDenyActionPerformed(e);
            }
        });
    }

    private void BtnAcceptActionPerformed(ActionEvent e) {

        var annualLeave = new AnnualLeave();
        var employee =
                employeeDao.getEmployeeByEmployeeId(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 2).toString()));

        requestLeaveDao.updateRequestLeaveByIDToStatus(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString()),
                "accepted");

        var requestLeave =
                requestLeaveDao.getRequestLeaveByRequestID(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString()));

        annualLeave.setEmployeeID(employee.getEmployeeId());
        annualLeave.setDateTimeOff(LocalDate.parse(table.getModel().getValueAt(table.getSelectedRow(), 4).toString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        annualLeave.setDescriptionTimeOff(table.getModel().getValueAt(table.getSelectedRow(), 7).toString());
        annualLeave.setUsed(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 6).toString()));
        annualLeave.setAccrued(0);
        annualLeave.setBalance(employee.getAnnualLeave() - annualLeave.getUsed());
        employee.setAnnualLeave(annualLeave.getBalance());

        annualLeaveDao.insertAnnualLeave(annualLeave);
        employeeDao.updateSingleEmployee(employee);

        tableModel.removeRow(table.getSelectedRow());
        table.repaint();
        SendMail.sendMailForRequestLeaveResult(requestLeave);
        dispose();
    }

    private void BtnDenyActionPerformed(ActionEvent e) {

        var employee =
                employeeDao.getEmployeeByEmployeeId(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 2).toString()));

        requestLeaveDao.updateRequestLeaveByIDToStatus(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString()),
                "denied");

        var requestLeave =
                requestLeaveDao.getRequestLeaveByRequestID(Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 0).toString()));

        tableModel.removeRow(table.getSelectedRow());
        table.repaint();
        SendMail.sendMailForRequestLeaveResult(requestLeave);
        dispose();
    }

}
