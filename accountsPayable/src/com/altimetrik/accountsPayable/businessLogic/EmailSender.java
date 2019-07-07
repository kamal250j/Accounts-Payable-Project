package com.altimetrik.accountsPayable.businessLogic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	private String emailAddress;
	private Properties userCredentials = new Properties();
	
	public EmailSender(String _emailAddress){
		emailAddress = _emailAddress;
		String to = emailAddress;
		String subject = "Invoice Bill Status";
		String body = "Your invoice has been approved. Keep up the good work";
		this.sendFromEmail(to,subject,body);
	}
	
	public boolean sendFromEmail(String to,String subject,String body){
		
		Properties mailConfiguration = System.getProperties();
		Properties userProperties = new Properties();
		try {
			userCredentials.load(new 
					FileReader("C:\\Users\\kmuwani\\workspace\\accountsPayable"
							+ "\\src\\com\\altimetrik\\accountsPayable\\"
							+ "api\\userCredentials.properties"));
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String host = "smtp.gmail.com";
		mailConfiguration.put("mail.smtp.starttls.enable", "true");
		mailConfiguration.put("mail.smtp.host", host);
		mailConfiguration.put("mail.smtp.user", userCredentials.getProperty("userName"));
		mailConfiguration.put("mail.smtp.password", userCredentials.getProperty("password"));
		mailConfiguration.put("mail.smtp.port", "587");
		mailConfiguration.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(mailConfiguration);
		MimeMessage message = new MimeMessage(session);
		Transport transport = null;
		
		try {
			message.setFrom(new InternetAddress(userCredentials.getProperty("userName")));
			InternetAddress toAddress = new InternetAddress(userCredentials.getProperty("userName"));

		    message.addRecipient(Message.RecipientType.TO, toAddress);

		    message.setSubject(subject);
		    message.setText(body);
		    transport = session.getTransport("smtp");
		    transport.connect(host, userCredentials.getProperty("userName"), userCredentials.getProperty("password"));
		    transport.sendMessage(message, message.getAllRecipients());
//		    System.out.println("Message sent");

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e2){
			e2.printStackTrace();
		} finally{
				try {
					if(transport != null)
						transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
		}
		
		return true;
		
	}
	
}
