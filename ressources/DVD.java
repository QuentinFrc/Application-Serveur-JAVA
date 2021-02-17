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
		if(isEmprunté()) 
			throw new ReservationException("Le DVD est déjà emprunté");
		else if(isReservé())
			throw new ReservationException("Le DVD est déjà reservé jusqu'à ", true);
		else
			this.abo = ab;
	}

	public void empruntPar(Abonne ab) throws EmpruntException  {
		if (adulte && ab.getAge() < ageMini)
			throw new EmpruntException("Vous n'avez pas l'âge pour emprunter ce DVD");
		else if (isReservéPour(ab)) {
			this.abo = ab;
		} 
		else {
			if (isEmprunté())
				throw new EmpruntException("Le DVD est déjà emprunté");
			else if (isReservé())
				throw new EmpruntException("Le DVD est reservé jusqu'à ", true);
			else if (adulte && ab.getAge() < ageMini)
				throw new EmpruntException("Vous n'avez pas l'âge pour emprunter ce DVD");
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

	public boolean isEmpruntéReservé() {
		if(abo==null)
			return false;
		else 
			return true;
	}
	private boolean isReservéPour(Abonne ab) {
		if(abo==ab && abo.aReservé(this))
			return true;
		else 
			return false;
	}
	private boolean isReservé() {
		if(abo==null) {
			return false;
		}
		return this.abo.aReservé(this);
	}
	private boolean isEmprunté() {
		if(abo==null)
			return false;
		return this.abo.aEmprunté(this);
	}

	public String toString() {
		String s = new String();
		s+="DVD n° "+this.numero+" : \""+this.nom +"\"";
		return s;
	}
}
