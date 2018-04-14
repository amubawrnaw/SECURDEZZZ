package Model;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	public EmailSender(){};

   public boolean sendForgotPasswordEmail(String toPerson, String verificationCode){
	   final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	   // Get a Properties object
	      Properties props = System.getProperties();
	      props.setProperty("mail.smtp.host", "smtp.gmail.com");
	      props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	      props.setProperty("mail.smtp.socketFactory.fallback", "false");
	      props.setProperty("mail.smtp.port", "465");
	      props.setProperty("mail.smtp.socketFactory.port", "465");
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.debug", "true");
	      props.put("mail.store.protocol", "pop3");
	      props.put("mail.transport.protocol", "smtp");
	      final String username = "watanaysxdshop@gmail.com";//
	      final String password = "securdexd";
	      try{
	      Session session = Session.getDefaultInstance(props, 
	                           new Authenticator(){
	                              protected PasswordAuthentication getPasswordAuthentication() {
	                                 return new PasswordAuthentication(username, password);
	                              }});

	    // -- Create a new message --
	      Message msg = new MimeMessage(session);

	   // -- Set the FROM and TO fields --
	      msg.setFrom(new InternetAddress("watanaysxdshop@gmail.com"));
	      msg.setRecipients(Message.RecipientType.TO, 
	                       InternetAddress.parse(toPerson,false));
	      msg.setSubject("Forgot password code");
	      msg.setText("Your verification code is " + verificationCode);
	      msg.setSentDate(new Date());
	      Transport.send(msg);
	      System.out.println("Message sent.");
	   }catch (MessagingException e){ 
		   e.printStackTrace();
		   return false;
	   }
	      return true;
   }
   
   public boolean sendBanReason(String toPerson, String reason){
	   final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	   // Get a Properties object
	      Properties props = System.getProperties();
	      props.setProperty("mail.smtp.host", "smtp.gmail.com");
	      props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	      props.setProperty("mail.smtp.socketFactory.fallback", "false");
	      props.setProperty("mail.smtp.port", "465");
	      props.setProperty("mail.smtp.socketFactory.port", "465");
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.debug", "true");
	      props.put("mail.store.protocol", "pop3");
	      props.put("mail.transport.protocol", "smtp");
	      final String username = "watanaysxdshop@gmail.com";//
	      final String password = "securdexd";
	      try{
	      Session session = Session.getDefaultInstance(props, 
	                           new Authenticator(){
	                              protected PasswordAuthentication getPasswordAuthentication() {
	                                 return new PasswordAuthentication(username, password);
	                              }});

	    // -- Create a new message --
	      Message msg = new MimeMessage(session);

	   // -- Set the FROM and TO fields --
	      msg.setFrom(new InternetAddress("watanaysxdshop@gmail.com"));
	      msg.setRecipients(Message.RecipientType.TO, 
	                       InternetAddress.parse(toPerson,false));
	      msg.setSubject("You were banned");
	      msg.setText(reason);
	      msg.setSentDate(new Date());
	      Transport.send(msg);
	      System.out.println("Message sent.");
	   }catch (MessagingException e){ 
		   e.printStackTrace();
		   return false;
	   }
	      return true;
   }
   
   public boolean sendunBanReason(String toPerson, String reason){
	   final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	   // Get a Properties object
	      Properties props = System.getProperties();
	      props.setProperty("mail.smtp.host", "smtp.gmail.com");
	      props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	      props.setProperty("mail.smtp.socketFactory.fallback", "false");
	      props.setProperty("mail.smtp.port", "465");
	      props.setProperty("mail.smtp.socketFactory.port", "465");
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.debug", "true");
	      props.put("mail.store.protocol", "pop3");
	      props.put("mail.transport.protocol", "smtp");
	      final String username = "watanaysxdshop@gmail.com";//
	      final String password = "securdexd";
	      try{
	      Session session = Session.getDefaultInstance(props, 
	                           new Authenticator(){
	                              protected PasswordAuthentication getPasswordAuthentication() {
	                                 return new PasswordAuthentication(username, password);
	                              }});

	    // -- Create a new message --
	      Message msg = new MimeMessage(session);

	   // -- Set the FROM and TO fields --
	      msg.setFrom(new InternetAddress("watanaysxdshop@gmail.com"));
	      msg.setRecipients(Message.RecipientType.TO, 
	                       InternetAddress.parse(toPerson,false));
	      msg.setSubject("You were unbanned");
	      msg.setText(reason);
	      msg.setSentDate(new Date());
	      Transport.send(msg);
	      System.out.println("Message sent.");
	   }catch (MessagingException e){ 
		   e.printStackTrace();
		   return false;
	   }
	      return true;
   }
}