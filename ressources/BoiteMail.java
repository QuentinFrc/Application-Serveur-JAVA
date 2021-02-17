package ressources;

import java.util.ArrayList;
import java.util.HashMap;

import mediatheque.Document;

public class BoiteMail {
	private String user;
	private String password;
	private HashMap<Document, ArrayList<Email>> notifications;

	public BoiteMail() {
		this.notifications = new HashMap<>();
	}

	public BoiteMail(String user, String password) throws Exception {
		this();
		this.setUser(user);
		this.setPassword(password);
	}

	public void createNotif(String emailAbo, String subject, String message, Document doc) throws Exception {
		ArrayList<Email> emails;
		if(!notifications.containsKey(doc)) {
			emails = new ArrayList<>();
		}
		else {
			emails = notifications.get(doc);
		}
		emails.add(new Email(user, password, emailAbo, subject, message));
		this.notifications.put(doc, emails);
	}

	public void sendNotif(Document doc) throws Exception {
		ArrayList<Email> emails = this.notifications.get(doc);
		if(!(emails ==null)) {
			for(Email email : emails) {
				System.out.println("[BDD] Un email à été envoyé à l'adresse : "+email.getMailTo());
				email.sendEmail(email.getHost(), email.getPort(), user, password, email.getMailTo(), email.getSubject(), email.getMessage());
			}
		}
	}

	//-----------------GETTER / SETTER -----------------\\
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}
