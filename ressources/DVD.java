package ressources;

import exception.EmpruntException;
import exception.ReservationException;
import mediatheque.Document;

public class DVD implements Document{
	private int numero;
	private String nom;
	private boolean adulte;
	private Abonne abo;
	private static final int ageMini = 16;

	public DVD(int numero, String nom, boolean adulte) {
		this.numero = numero;
		this.nom = nom;
		this.adulte = adulte;
	}
	public int numero() {return numero;}
	public Abonne getAb () {return this.abo;}

	public void reservationPour(Abonne ab) throws ReservationException {
		if(isEmprunt�()) 
			throw new ReservationException("Le DVD est d�j� emprunt�");
		else if(isReserv�())
			throw new ReservationException("Le DVD est d�j� reserv� jusqu'� ", true);
		else
			this.abo = ab;
	}

	public void empruntPar(Abonne ab) throws EmpruntException  {
		if (adulte && ab.getAge() < ageMini)
			throw new EmpruntException("Vous n'avez pas l'�ge pour emprunter ce DVD");
		else if (isReserv�Pour(ab)) {
			this.abo = ab;
		} 
		else {
			if (isEmprunt�())
				throw new EmpruntException("Le DVD est d�j� emprunt�");
			else if (isReserv�())
				throw new EmpruntException("Le DVD est reserv� jusqu'� ", true);
			else if (adulte && ab.getAge() < ageMini)
				throw new EmpruntException("Vous n'avez pas l'�ge pour emprunter ce DVD");
			else
				this.abo = ab;
		}
	}

	public void retour() {
		this.abo = null;
	}

	public String getNom() {
		return nom;
	}

	public boolean isPourAdulte() {
		return adulte;
	}

	public boolean isEmprunt�Reserv�() {
		if(abo==null)
			return false;
		else 
			return true;
	}
	private boolean isReserv�Pour(Abonne ab) {
		if(abo==ab && abo.aReserv�(this))
			return true;
		else 
			return false;
	}
	private boolean isReserv�() {
		if(abo==null) {
			return false;
		}
		return this.abo.aReserv�(this);
	}
	private boolean isEmprunt�() {
		if(abo==null)
			return false;
		return this.abo.aEmprunt�(this);
	}

	public String toString() {
		String s = new String();
		s+="DVD n� "+this.numero+" : \""+this.nom +"\"";
		return s;
	}
}
