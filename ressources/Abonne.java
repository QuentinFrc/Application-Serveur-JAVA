package ressources;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

import mediatheque.Document;

public class Abonne{
	private ArrayList<Document> docLou�;
	private ArrayList<Document> reserv�;
	private HashMap<Document, LocalDate> dateEmprunt;
	private int numero;
	private LocalDate birthDate;
	private String nom;
	private String mail;

	
	public Abonne(int num ,LocalDate dtn,String n, String mail){
		this.birthDate=dtn;
		this.nom=n;
		this.numero=num;
		this.mail = mail;
		this.docLou�=new ArrayList<Document>();
		this.reserv�=new ArrayList<Document>();
		this.dateEmprunt=new HashMap<Document, LocalDate>();
	}
	

	public LocalDate getDateEmprunt(Document d) {
		return dateEmprunt.get(d);
	}

	// *******************reservation*******************
	public void reserve(Document d) {
		reserv�.add(d);
	}
	public void d�r�server(Document d) {
		if (aReserv�(d)) {
			reserv�.remove(d);
		}
	}
	public ArrayList<Document> getAllReserv�() {
		return reserv�;
	}
	public boolean aReserv�(Document doc) {
		return reserv�.contains(doc);
	}
	// *******************location*******************
	public void aRendu(Document doc) {
		this.docLou�.remove(doc);
	}
	public boolean aEmprunt�(Document doc) {
		return this.docLou�.contains(doc);
	}
	public void emprunte(Document d) {
		if(aReserv�(d)) this.reserv�.remove(d);
		this.docLou�.add(d);
		this.dateEmprunt.put(d, LocalDate.now());
	}
	public boolean loue(Document d) {
		return docLou�.contains(d);
	}
	public ArrayList<Document> getAllDocLouer() {
		return docLou�;
	}
	
	public int getNumero() {
		return numero;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getAge() {
		LocalDate currentDate = LocalDate.now();
		return Period.between(birthDate, currentDate).getYears();
	}
	public String toString(){
		String s = new String();
		s+=this.getNom();
		return s;
	}


	public String getMail() {
		// TODO Auto-generated method stub
		return this.mail;
	}
}
