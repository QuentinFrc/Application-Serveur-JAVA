package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import exception.ReservationException;
import mediatheque.Document;
import mediatheque.Mediatheque_SYS;
import ressources.Abonne;

public class ServiceReservation implements Runnable{
	private final Socket client;
	private Mediatheque_SYS m;

	public ServiceReservation(Socket client, Mediatheque_SYS m) {
		this.client = client;
		this.m = m;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
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
						out.println(m.getAbonne(noAb)+" vous êtes banni."+ m.getDateBan(abonné));
						abonné = m.getAbonne(noAb);
						System.out.println("[BDD] L'abonné (banni) : "+abonné+" a essayé de se connecté au serveur Reservation");
						banni = true;
						break;
					}
					if(m.isAbonné(noAb)) {
						abonné = m.getAbonne(noAb);
						System.out.println("[BDD] L'abonné : "+abonné+" s'est connecté au serveur Reservation.");
						break;
					}
					out.println("Mauvais Numéro d'abonné ! Veuillez réeassayer.@@");}
				catch (NumberFormatException e) {
					out.println("Erreur : Numéro d'abonné invalide@@");}
			}
			if(!banni) {
				String listDoc = new String();
				for(Document d : m.getDocuments().values())
					listDoc+="##"+d;

				out.println("Bonjour "+ abonné+", choisissez le document que vous voulez réserver pour une durée de 2 heures :"+listDoc+"@@");
				Document docRéservé =null;
				while(docRéservé ==null) {
					try {
						line =  in.readLine();
						int noDoc = Integer.parseInt(line);
						docRéservé = m.getDocument(noDoc);
						if(!(docRéservé==null))
							break;
						out.println("Mauvais Numéro de document ! Veuillez réeassayer.##"+listDoc+"@@");
					}
					catch (NumberFormatException e) {
						out.println("Erreur : Numéro de document invalide##");
					}
				} 
				try {
					m.réserver(abonné, docRéservé);
					out.println("Félicitation, vous avez bien réservé le "+ docRéservé);
					System.out.println("[BDD] Le document "+docRéservé+" à été réservé par "+abonné);
				} catch (ReservationException e) {
					if (!abonné.aReservé(docRéservé)) {
						out.println(e + "##Souhaitez vous être averti par mail de la disponibilité de ce document ("
								+ docRéservé + ") ? (o/n)@@");
						if (in.readLine().contentEquals("o")) {
							try {
								m.créerNotification(docRéservé, abonné.getMail(),
										"Le document que vous attendiez est disponible !",
										"Bonjour"+abonné+", Le document " + docRéservé
										+ " est maintenant disponible ! Venez le reserver et l'emprunter. A tout de suite");
								System.out.println("[BDD] L'abonné " + abonné
										+ " a créer une notification de disponibilité pour le document " + docRéservé);
								out.println("Une notification de disponibilité a bien été créée##");
							} catch (Exception e1) {
								out.println(e1);
								System.out.println(
										"[BDD] Un problème a eu lieu lors de la création de notification de disponibilité pour l'abonné "
												+ abonné);
							};
						} else {
							out.println("Pas de notification créée##");
							System.out.println("[BDD] L'abonné " + abonné
									+ " n'a pas souhaitez créer de notification de disponibilité pour le document "
									+ docRéservé);
						} 
					}
					else
						out.println("Vous réservez déjà ce document");}
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
