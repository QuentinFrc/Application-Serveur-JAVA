package ressources;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

import mediatheque.Document;

public class Abonne{
	private ArrayList<Document> docLoué;
	private ArrayList<Document> reservé;
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
		this.docLoué=new ArrayList<Document>();
		this.reservé=new ArrayList<Document>();
		this.dateEmprunt=new HashMap<Document, LocalDate>();
	}
	

	public LocalDate getDateEmprunt(Document d) {
		return dateEmprunt.get(d);
	}

	// *******************reservation*******************
	public void reserve(Document d) {
		reservé.add(d);
	}
	public void déréserver(Document d) {
		if (aReservé(d)) {
			reservé.remove(d);
		}
	}
	public ArrayList<Document> getAllReservé() {
		return reservé;
	}
	public boolean aReservé(Document doc) {
		return reservé.contains(doc);
	}
	// *******************location*******************
	public void aRendu(Document doc) {
		this.docLoué.remove(doc);
	}
	public boolean aEmprunté(Document doc) {
		return this.docLoué.contains(doc);
	}
	public void emprunte(Document d) {
		if(aReservé(d)) this.reservé.remove(d);
		this.docLoué.add(d);
		this.dateEmprunt.put(d, LocalDate.now());
	}
	public boolean loue(Document d) {
		return docLoué.contains(d);
	}
	public ArrayList<Document> getAllDocLouer() {
		return docLoué;
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
