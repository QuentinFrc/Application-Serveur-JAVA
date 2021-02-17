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

			out.println("Veuillez vous identifier avec votre num�ro d'abonn�.@@");

			Abonne abonn�=null;
			boolean banni = false;

			while(abonn�==null) {
				try {
					line = in.readLine();
					int noAb = Integer.parseInt(line);
					if(m.isBanni(noAb)) {
						abonn� = m.getAbonne(noAb);
						out.println(abonn�+" vous �tes banni."+m.getDateBan(abonn�));
						System.out.println("[BDD] L'abonn� (banni) : "+abonn�+" a essay� de se connect� au serveur Emprunt");
						banni = true;
						break;
					}
					if(m.isAbonn�(noAb)) {
						abonn� = m.getAbonne(noAb);
						System.out.println("[BDD] L'abonn� : "+abonn�+" s'est connect� au serveur Emprunt");
						break;
					}
					out.println("Mauvais Num�ro d'abonn� ! Veuillez r�eassayer.@@");}
				catch (NumberFormatException e) {
					out.println("Erreur : Num�ro d'abonn� invalide@@");}
			}
			if(!banni) {
				String listDoc = new String();
				for(Document d : m.getDocuments().values())
					listDoc+=d+" ##";
				Document docEmprunt� =null;
				out.println("Bonjour "+ abonn�.getNom()+", choisissez le document que vous voulez emprunter :##"+listDoc+"@@");

				while (docEmprunt�==null) {
					try{
						line =  in.readLine();
						int noDoc = Integer.parseInt(line);
						docEmprunt�= m.getDocument(noDoc);
						if(!(docEmprunt�==null))
							break;
						out.println("Mauvais Num�ro de document ! Veuillez r�eassayer.##"+listDoc+"@@");
					}catch (NumberFormatException e) {
						out.println("Erreur : Num�ro de document invalide@@");}
				}
				try {
					m.emprunter(abonn�, docEmprunt�);
					System.out.println("[BDD] Le document "+docEmprunt�+" � �t� emprunt� par : "+abonn�);
					out.println("F�licitation, vous avez bien emprunt� le "+ docEmprunt�);
				} catch (EmpruntException e) {
					out.println(e);
					System.out.println("[BDD] L'abonn� : "+abonn�+" � essay� d'emprunt� un document.");
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
