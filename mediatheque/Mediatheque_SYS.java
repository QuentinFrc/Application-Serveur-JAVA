package mediatheque;

import java.time.LocalDate;
import java.util.HashMap;

import exception.EmpruntException;
import exception.ReservationException;

import ressources.Abonne;
import ressources.BoiteMail;
import ressources.DVD;

import  timertask.FinBannissement;
import  timertask.FinDeReservation;

public class Mediatheque_SYS {
	private HashMap<Integer, Abonne> abonnés;
	private HashMap<Abonne, FinBannissement> bannis;
	private HashMap<Integer, Document> documents;
	private HashMap<Document,  FinDeReservation> reservations;
	private String email;
	private String password;
	private BoiteMail boiteMail;

	public Mediatheque_SYS(String email, String password) {
		this.abonnés= new HashMap<Integer, Abonne>();
		this.documents= new HashMap<Integer, Document>();
		this.bannis = new HashMap<Abonne,  FinBannissement>();
		this.reservations = new HashMap<Document,  FinDeReservation>();
		this.email = email;
		this.password = password;
	}

	public void init() throws Exception {
		Document doc1 = new DVD(2, "Halloween 2", true);
		Document doc2 = new DVD(1, "Halloween 1", true);
		Document doc3 = new DVD(3, "Halloween 3 (familly)", false);
		addDocument(doc1);
		addDocument(doc2);
		addDocument(doc3);
		Abonne Erwan =new Abonne(1,LocalDate.of(2000, 5, 14), "Erwan", "zemouripro4@gmail.com");
		Abonne Audran =new Abonne(2,LocalDate.of(1986, 12, 20), "Audran","audran.liard2@gmail.com");
		Abonne Quentin = new Abonne(3,LocalDate.of(2001, 11, 20), "Quentin", "quentin.francois75@gmail.com");
		Abonne Brette = new Abonne(3,LocalDate.of(1984, 1, 1), "JF Brette", "jean-francois.brette@u-paris.fr");
		addAbonné(Erwan);
		addAbonné(Audran);
		addAbonné(Quentin);
		addAbonné(Brette);
		this.setBoiteMail(new BoiteMail(this.email, this.password));
	}

	public void réserver(Abonne abo, Document doc){
		synchronized (doc) {
			try {
				doc.reservationPour(abo);
			} catch (ReservationException e) {
				if (e.needDate())
					throw new ReservationException(e.getMessage() + this.reservations.get(doc));
				else
					throw new ReservationException(e.getMessage());
			}
			abo.reserve(doc);
			this.reservations.put(doc, new FinDeReservation(this, doc, abo));
		}
	}

	public void emprunter(Abonne abo, Document doc)   {
		synchronized (doc) {
			try {
				doc.empruntPar(abo);
			} catch (EmpruntException e) {
				if (e.needDate())
					throw new EmpruntException(e.getMessage() + this.reservations.get(doc));
				else
					throw new EmpruntException(e.getMessage());
			}
			abo.emprunte(doc);
		}
	}

	public void déréserver(Abonne abo, Document doc) {
		synchronized (doc) {
			abo.déréserver(doc);
			this.reservations.remove(doc);
		}
	}

	public void retourner(Document doc) throws Exception {
		synchronized (doc) {
			doc.retour();
			if(!(getAbonneEmprunt(doc)==null))
				getAbonneEmprunt(doc).aRendu(doc);
			this.boiteMail.sendNotif(doc);
		}
	}

	public Abonne getAbonneEmprunt(Document doc) {
		synchronized (doc) {
			for (Abonne abo : this.abonnés.values()) {
				if (abo.aEmprunté(doc))
					return abo;
			}
			return null;
		}
	}

	public void débanir(Abonne banni) {
		this.bannis.remove(banni);	
	}

	public FinBannissement bannir(Abonne banni) {
		synchronized (banni) {
			FinBannissement f = new FinBannissement(banni, this);
			return this.bannis.put(banni, f);
		}
	}

	public void débannir(Abonne banni) {
		this.bannis.remove(banni.getNumero(), banni);
	}

	public void créerNotification(Document doc, String abo, String subject, String message) throws Exception {
		synchronized(doc) {
			this.boiteMail.createNotif(abo, subject, message, doc);
		}
	}

	public void notifier(Document doc) throws Exception {
		synchronized(doc) {
			this.boiteMail.sendNotif(doc);
		}
	}

	//-----------------GETTER / SETTER -----------------\\
	public String getEmail() {
		return email;
	}
	public BoiteMail getBoiteMail() {
		return boiteMail;
	}
	public void setBoiteMail(BoiteMail centreNotif) {
		this.boiteMail = centreNotif;
	}
	public HashMap<Integer, Abonne> getAbonnés() {
		return this.abonnés;
	}
	public Abonne getAbonne(int numéro) {
		return this.abonnés.get(numéro);
	}
	public HashMap<Abonne,  FinBannissement> getBannis() {
		return bannis;
	}
	public  FinBannissement getDateBan(Abonne ab) {
		return this.bannis.get(ab);
	}
	public boolean isBanni(int num) {
		return this.bannis.containsKey(this.getAbonne(num));
	}
	public boolean isAbonné(int  num) {
		return this.abonnés.containsKey(num);
	}	
	public HashMap<Integer, Document> getDocuments() {
		return this.documents;
	}
	public Document getDocument(int numéro)  {
		if(this.documents.get(numéro) == null)
			return null;
		return this.documents.get(numéro);
	}
	public void addDocument(Document document) {
		this.documents.put(document.numero(), document);
	}
	public void addAbonné(Abonne abonné) {
		this.abonnés.put(abonné.getNumero(), abonné);
	}

	public boolean isEmprunté(Document docRetourné) {
		// TODO Auto-generated method stub
		return this.getAbonneEmprunt(docRetourné)==null ? false : true;
	}
}
