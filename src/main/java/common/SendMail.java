package common;

import dao.EmployeeDao;
import dao.LeaveTypeDao;
import entity.Account;
import entity.Employee;
import entity.RequestLeave;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.time.format.DateTimeFormatter;

//
public class SendMail {
	public static void sendClientInfo(Account user) {
		var emp = new EmployeeDao();
		var employee = emp.getEmployeeByEmployeeId(user.getEmloyeeId());
		var fromEmail = "emailforleavemanagement@gmail.com"; // --> Input your email here
		var password = "leavemanagement123"; // --> Input your password here
		var to = employee.getEmail();
		var host = "smtp.gmail.com";
		var props = System.getProperties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587"); // --> Using pro 465 or 587 as well
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		Session session = Session.getInstance(props, auth);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setSubject("Your Login Account Infomation:");
			msg.setText("Username:" + user.getUserName() + "\n" + "Password:" + user.getPassword() + "\n");
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(String.valueOf(to)));
			Transport.send(msg);
			JOptionPane.showMessageDialog(null, "Process Successful!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public static void sendMailForRequestLeave(RequestLeave rLeave) {
        var employeeDao = new EmployeeDao();
		var lTypeDao = new LeaveTypeDao();

		var employee = employeeDao.getEmployeeByEmployeeId(rLeave.getEmployeeID());

		var lType = lTypeDao.getLeaveTypeInfoByID(rLeave.getLeaveID());
		var fromEmail = "emailforleavemanagement@gmail.com"; // --> Input your email here
		var password = "leavemanagement123"; // --> Input your password here
		var to = rLeave.getRequestTo();
		var host = "smtp.gmail.com";
		var props = System.getProperties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587"); // --> Using pro 465 or 587 as well
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		Session session = Session.getInstance(props, auth);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setSubject("Requesting for leave:");
			msg.setText("From: "+employee.getFullName()+"\n"+
					"ID: "+employee.getEmployeeId()+"\n"+
					"Email: "+employee.getEmail()+"\n"+
					"starting: "+rLeave.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\n"+
					"to: "+rLeave.getDateEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\n"+
					"Leave Type: "+lType.getLeaveType()+"\n"+
					"Description: "+rLeave.getRequestDescription()+"\n"+
					"Please log in to approve or deny this request."
			);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Transport.send(msg);
			JOptionPane.showMessageDialog(null, "Process Successful!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public static void sendMailForRequestLeaveResult(RequestLeave rLeave) {
		var employeeDao = new EmployeeDao();
		var lTypeDao = new LeaveTypeDao();

		var employee = employeeDao.getEmployeeByEmployeeId(rLeave.getEmployeeID());
		var lType = lTypeDao.getLeaveTypeInfoByID(rLeave.getLeaveID());
		var fromEmail = "emailforleavemanagement@gmail.com"; // --> Input your email here
		var password = "leavemanagement123"; // --> Input your password here
		var to = "leemongyan97@gmail.com";
		var host = "smtp.gmail.com";
		var props = System.getProperties();

		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587"); // --> Using pro 465 or 587 as well
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		Session session = Session.getInstance(props, auth);
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setSubject("Result of Requesting for leave:");
			msg.setText("From: "+employee.getFullName()+"\n"+
					"ID: "+employee.getEmployeeId()+"\n"+
					"Email: "+employee.getEmail()+"\n"+
					"starting: "+rLeave.getDateStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\n"+
					"to: "+rLeave.getDateEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\n"+
					"Leave Type: "+lType.getLeaveType()+"\n"+
					"Description: "+rLeave.getRequestDescription()+"\n"+
					"Result of Requesting: "+rLeave.getRequestStatus()+"\n"+
					"Please contact your manager or admin if you have questions."
			);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Transport.send(msg);
			JOptionPane.showMessageDialog(null, "Process Successful!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
