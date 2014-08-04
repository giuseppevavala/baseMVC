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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendEmail
{
	@Value ("${mail.user}")
	private String username;

	@Value ("${mail.password}")
	private String password;
	
	@Value ("${host.name}")
	private String host;
	
   public void SendEmailConfirmRegistration (String emailTo, String token) throws UnsupportedEncodingException, MessagingException
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
    		  host + "/rest/token/registration?token=" + token + "&email=" + emailTo;

      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(username, "Base MVC"));
      msg.addRecipient(Message.RecipientType.TO,
                       new InternetAddress(emailTo, "Mr. User"));
      msg.setSubject("La tua registrazione è avvenuta con successo");
      msg.setText(msgBody);
      Transport.send(msg);

	   
   }
}