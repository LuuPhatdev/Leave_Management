package common;

import entity.Account;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;

//
public class SendMail {
	public static void sendClientInfo(Account user) {
		var fromEmail = ""; // --> Input your email here
		var password = ""; // --> Input your password here
		var to = user.getEmail();
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
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			Transport.send(msg);
			JOptionPane.showMessageDialog(null, "Process Successful!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
