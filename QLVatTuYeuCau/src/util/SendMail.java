package util;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	private String username = "evnCanTho@gmail.com";
	private String password = "evnCanTho2015";
	private Properties props;
	
	/**
	 */
	public SendMail() {
		this.username = "evnCanTho@gmail.com";
		this.password = "evnCanTho2015";
		this.props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");
			props.put("mail.smtp.starttls.enable", "true");
	}
	
	/**
	 * @param username
	 * @param password
	 */
	public SendMail(String username, String password) {
		this.username = username;
		this.password = password;
		this.props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true");
	}
	
	/**
	 * @param username
	 * @param password
	 * @param props
	 */
	public SendMail(String username, String password, Properties props) {
		this.username = username;
		this.password = password;
		this.props = props;
	  props.put("mail.smtp.host", "smtp.gmail.com");  
	  props.put("mail.smtp.socketFactory.port", "465");  
	  props.put("mail.smtp.socketFactory.class",  
	            "javax.net.ssl.SSLSocketFactory");  
	  props.put("mail.smtp.auth", "true");  
	  props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true");
	  	props.put("mail.smtp.starttls.enable", "true");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
	public void send(Mail mail) {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			MimeMessage  message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(mail.getTo()));
			StandardCharsets.UTF_8.displayName();
			message.setSubject(mail.getSubject());
//			message.setHeader(mail.getSubject(), "text/html; charset=UTF-8"); //(arg0);(mail.getSubject());
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setContent(mail.getContent(),"text/html; charset=UTF-8");
			//message.setContent(mail.getContent(),"text/html; charset=UTF-8");
//			message.setContent(mail.getContent(),"text/html; charset=UTF-8");
			Transport.send(message);
			
		} catch (Exception e) {
		}
	}
	public static void main(String[] args) {
		SendMail sendMailer = new SendMail("evnCanTho@gmail.com", "evnCanTho2015");
		Mail mail = new Mail();
		mail.setSubject("Đây là chủ đề ĐÃ ẨN. Công văn vừa được thêm mới");
		mail.setContent("Bạn được phân công cấp vật tư");
		mail.setFrom("quoipro94@gmail.com");
		mail.setTo("quoib1203959@student.ctu.edu.vn");
		sendMailer.send(mail);
	}
}
	