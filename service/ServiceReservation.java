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

			out.println("Veuillez vous identifier avec votre num�ro d'abonn�.@@");

			Abonne abonn�=null;
			boolean banni = false;
			while(abonn�==null) {
				try {
					line = in.readLine();
					int noAb = Integer.parseInt(line);
					if(m.isBanni(noAb)) {
						abonn� = m.getAbonne(noAb);
						out.println(m.getAbonne(noAb)+" vous �tes banni."+ m.getDateBan(abonn�));
						abonn� = m.getAbonne(noAb);
						System.out.println("[BDD] L'abonn� (banni) : "+abonn�+" a essay� de se connect� au serveur Reservation");
						banni = true;
						break;
					}
					if(m.isAbonn�(noAb)) {
						abonn� = m.getAbonne(noAb);
						System.out.println("[BDD] L'abonn� : "+abonn�+" s'est connect� au serveur Reservation.");
						break;
					}
					out.println("Mauvais Num�ro d'abonn� ! Veuillez r�eassayer.@@");}
				catch (NumberFormatException e) {
					out.println("Erreur : Num�ro d'abonn� invalide@@");}
			}
			if(!banni) {
				String listDoc = new String();
				for(Document d : m.getDocuments().values())
					listDoc+="##"+d;

				out.println("Bonjour "+ abonn�+", choisissez le document que vous voulez r�server pour une dur�e de 2 heures :"+listDoc+"@@");
				Document docR�serv� =null;
				while(docR�serv� ==null) {
					try {
						line =  in.readLine();
						int noDoc = Integer.parseInt(line);
						docR�serv� = m.getDocument(noDoc);
						if(!(docR�serv�==null))
							break;
						out.println("Mauvais Num�ro de document ! Veuillez r�eassayer.##"+listDoc+"@@");
					}
					catch (NumberFormatException e) {
						out.println("Erreur : Num�ro de document invalide##");
					}
				} 
				try {
					m.r�server(abonn�, docR�serv�);
					out.println("F�licitation, vous avez bien r�serv� le "+ docR�serv�);
					System.out.println("[BDD] Le document "+docR�serv�+" � �t� r�serv� par "+abonn�);
				} catch (ReservationException e) {
					if (!abonn�.aReserv�(docR�serv�)) {
						out.println(e + "##Souhaitez vous �tre averti par mail de la disponibilit� de ce document ("
								+ docR�serv� + ") ? (o/n)@@");
						if (in.readLine().contentEquals("o")) {
							try {
								m.cr�erNotification(docR�serv�, abonn�.getMail(),
										"Le document que vous attendiez est disponible !",
										"Bonjour"+abonn�+", Le document " + docR�serv�
										+ " est maintenant disponible ! Venez le reserver et l'emprunter. A tout de suite");
								System.out.println("[BDD] L'abonn� " + abonn�
										+ " a cr�er une notification de disponibilit� pour le document " + docR�serv�);
								out.println("Une notification de disponibilit� a bien �t� cr��e##");
							} catch (Exception e1) {
								out.println(e1);
								System.out.println(
										"[BDD] Un probl�me a eu lieu lors de la cr�ation de notification de disponibilit� pour l'abonn� "
												+ abonn�);
							};
						} else {
							out.println("Pas de notification cr��e##");
							System.out.println("[BDD] L'abonn� " + abonn�
									+ " n'a pas souhaitez cr�er de notification de disponibilit� pour le document "
									+ docR�serv�);
						} 
					}
					else
						out.println("Vous r�servez d�j� ce document");}
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
