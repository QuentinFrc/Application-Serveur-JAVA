package ressources;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class Email
{
	private String host, port = "587";
	private String from;
	private String password;
	private String mailTo;
	private String subject; 
	private String message;
	private String[] attachements;

	public Email(String mailFrom, String password, String mailTo, String subject, String message) throws Exception
	{
		if (mailFrom.contains("@gmail"))
			this.host = "smtp.gmail.com";
		else if (mailFrom.contains("@yahoo"))
			this.host = "smtp.mail.yahoo.com";
		else
			this.host = "smtp.live.com";
		setFrom(mailFrom);
		setPassword(password);
		setMailTo(mailTo);
		setSubject(subject);
		setMessage(message);
	}
	/*
	public Email(String mailFrom, String password, String mailTo, String subject, String message, String[] attachFiles) throws Exception
	{	
		this(mailFrom, password, mailTo, subject, message);
		this.attachements=attachFiles;
	}
	 */

	public void sendEmail(String host, String port, final String userName, final String password, String toAddress, String subject, String message) throws Exception
	{
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		Authenticator auth = new Authenticator()
		{
			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		/*
		if (attachFiles != null && attachFiles.length > 0)
		{
			for (String filePath : attachFiles)
			{
				MimeBodyPart attachPart = new MimeBodyPart();

				try
				{
					attachPart.attachFile(filePath);
				}
				finally
				{
					multipart.addBodyPart(attachPart);
				}
			}
		}
		 */
		msg.setContent(multipart);

		Transport.send(msg);
	}

	//-----------------GETTER / SETTER -----------------\\
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getAttachements() {
		return attachements;
	}

}