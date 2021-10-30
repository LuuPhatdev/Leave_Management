package gui;

import common.SendMail;
import dao.AnnualLeaveDao;
import dao.EmployeeDao;
import dao.RequestLeaveDao;
import entity.AnnualLeave;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GuiCancellingRequest extends JFrame {
    private JPanel contentPane;
    private JButton btnCancel;
    private JButton btnKeep;
    private JLabel lbRequestID;
    private JLabel lbCancellingRequest;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private RequestLeaveDao requestLeaveDao = new RequestLeaveDao();
    private AnnualLeaveDao annualLeaveDao = new AnnualLeaveDao();
    private EmployeeDao employeeDao = new EmployeeDao();

    public GuiCancellingRequest(JFrame frame, JTable table, DefaultTableModel tableModel) {
        super(String.valueOf(frame));
        this.table = table;
        this.tableModel = tableModel;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        setTitle("User Management");
        setLocationRelativeTo(null);
        setVisible(true);

        lbRequestID.setText("Request ID: " + table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BtnCancelActionPerformed(e);
            }
        });
        btnKeep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BtnKeepActionPerformed(e);
            }
        });
    }

    private void BtnKeepActionPerformed(ActionEvent e) {
        dispose();
    }

    private void BtnCancelActionPerformed(ActionEvent e) {
        var requestLeave = requestLeaveDao.getRequestLeaveByRequestID(
                Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 1).toString()));
        var employee = employeeDao.getEmployeeByEmployeeId(requestLeave.getEmployeeID());
        if(table.getModel().getValueAt(table.getSelectedRow(), 0).toString().equals("accepted")){
            employee.setAnnualLeave(employee.getAnnualLeave()+requestLeave.getAmount());
        }
        var annualLeave = new AnnualLeave();
        annualLeave.setEmployeeID(employee.getEmployeeId());
        annualLeave.setDateTimeOff(LocalDate.now());
        annualLeave.setDescriptionTimeOff("Cancelling request id: "+requestLeave.getRequestID());
        annualLeave.setUsed(0);
        annualLeave.setAccrued(requestLeave.getAmount());
        annualLeave.setBalance(employee.getAnnualLeave());

        employeeDao.updateSingleEmployee(employee);
        requestLeaveDao.updateRequestLeaveByIDToStatus(requestLeave.getRequestID(), "cancelled");
        annualLeaveDao.insertAnnualLeave(annualLeave);
        tableModel.setValueAt("cancelled", table.getSelectedRow(), 0);
        table.setModel(tableModel);
        dispose();
    }
}
