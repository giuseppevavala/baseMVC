package com.business;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail
{
   final static String username = "basemvc@gmail.com";
   final static String password = "lamiapassword";
	
   public static void SendEmailConfirmRegistration (String emailTo, String token) throws UnsupportedEncodingException, MessagingException
   {
      Properties props = new Properties();
	  props.put("mail.smtp.auth", "true");
	  props.put("mail.smtp.starttls.enable", "true");
	  props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");
      Session session = Session.getInstance(props,
    		  new javax.mail.Authenticator() {
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(username, password);
    			}
    		  });

      String msgBody = "Per confermare la registrazione clicca su questo link: " +
    		  "http://localhost:8080/base/rest/token/registration?token=" + token + "&email=" + emailTo;

      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("giuse.vavala@gmail.com", "Base MVC"));
      msg.addRecipient(Message.RecipientType.TO,
                       new InternetAddress("giuseppe.vavala@gmail.com", "Mr. User"));
      msg.setSubject("La tua registrazione è avvenuta con successo");
      msg.setText(msgBody);
      Transport.send(msg);

	   
   }
}