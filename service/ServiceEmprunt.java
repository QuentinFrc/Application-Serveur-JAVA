package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import exception.EmpruntException;
import mediatheque.Document;
import mediatheque.Mediatheque_SYS;
import ressources.Abonne;

public class ServiceEmprunt implements Runnable {

	private final Socket client;
	private Mediatheque_SYS m;

	public ServiceEmprunt(Socket client, Mediatheque_SYS m) {
		this.client = client;
		this.m = m;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter (client.getOutputStream(), true);
			String line;

			out.println("Veuillez vous identifier avec votre numéro d'abonné.@@");

			Abonne abonné=null;
			boolean banni = false;

			while(abonné==null) {
				try {
					line = in.readLine();
					int noAb = Integer.parseInt(line);
					if(m.isBanni(noAb)) {
						abonné = m.getAbonne(noAb);
						out.println(abonné+" vous êtes banni."+m.getDateBan(abonné));
						System.out.println("[BDD] L'abonné (banni) : "+abonné+" a essayé de se connecté au serveur Emprunt");
						banni = true;
						break;
					}
					if(m.isAbonné(noAb)) {
						abonné = m.getAbonne(noAb);
						System.out.println("[BDD] L'abonné : "+abonné+" s'est connecté au serveur Emprunt");
						break;
					}
					out.println("Mauvais Numéro d'abonné ! Veuillez réeassayer.@@");}
				catch (NumberFormatException e) {
					out.println("Erreur : Numéro d'abonné invalide@@");}
			}
			if(!banni) {
				String listDoc = new String();
				for(Document d : m.getDocuments().values())
					listDoc+=d+" ##";
				Document docEmprunté =null;
				out.println("Bonjour "+ abonné.getNom()+", choisissez le document que vous voulez emprunter :##"+listDoc+"@@");

				while (docEmprunté==null) {
					try{
						line =  in.readLine();
						int noDoc = Integer.parseInt(line);
						docEmprunté= m.getDocument(noDoc);
						if(!(docEmprunté==null))
							break;
						out.println("Mauvais Numéro de document ! Veuillez réeassayer.##"+listDoc+"@@");
					}catch (NumberFormatException e) {
						out.println("Erreur : Numéro de document invalide@@");}
				}
				try {
					m.emprunter(abonné, docEmprunté);
					System.out.println("[BDD] Le document "+docEmprunté+" à été emprunté par : "+abonné);
					out.println("Félicitation, vous avez bien emprunté le "+ docEmprunté);
				} catch (EmpruntException e) {
					out.println(e);
					System.out.println("[BDD] L'abonné : "+abonné+" à essayé d'emprunté un document.");
				}
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void finalize() throws Throwable {
		client.close(); 
	}
}
