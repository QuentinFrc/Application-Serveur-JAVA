package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

import mediatheque.Document;
import mediatheque.Mediatheque_SYS;

import ressources.Abonne;
import timertask.FinBannissement;

public class ServiceRetour implements Runnable {
	private final Socket client;
	private Mediatheque_SYS m;

	public ServiceRetour(Socket client, Mediatheque_SYS m) {
		this.client = client;
		this.m = m;
	}

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter (client.getOutputStream(), true);
			String line;
			out.println("Quel Document rapportez vous ?@@");
			Document docRetourn� = null;
			try {
				line = in.readLine();
				int i = Integer.parseInt(line);
				if(!(m.getDocument(i)==null)) {
					docRetourn� = m.getDocument(i);
					if(m.isEmprunt�(docRetourn�)) {
						Abonne aboAEmprunt� = m.getAbonneEmprunt(docRetourn�);
						out.println("Est il endommag� ? (si oui saisissez \"o\" sinon ce que vous voulez)@@");
						line = in.readLine();
						if(line.contentEquals("o")||isAbim�()) {
							this.bannir(aboAEmprunt�);
							out.println("Le document a �t� abim�##"+RandomMess(aboAEmprunt�));
							out.close();
							System.out.println("[BDD] Remise en mauvaise �tat du "+docRetourn�);
						}
						else if(( LocalDate.now().getDayOfYear() -aboAEmprunt�.getDateEmprunt(docRetourn�).getDayOfYear())>-1){
							this.bannir(aboAEmprunt�);
							out.println("Le document a �t� rendu en retard##"+RandomMess(aboAEmprunt�));
							out.close();
							System.out.println("[BDD] Remise en retard du "+docRetourn�);
						}
						m.retourner(docRetourn�);
						System.out.println("[BDD] Le document "+docRetourn�+" � �t� rapport�");
						out.println("Le document a bien �t� rapport�. Merci !");
					}
					else
						out.println("Ce document n'a pas �t� emprunt�##");
				}
				else
					out.println("Ce document n'existe pas");
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String RandomMess(Abonne abo) {
		String mess = new String();
		mess="Vous �tes maintenant banni jusqu'au "+m.getDateBan(abo)+"##";
		int i = (int) (Math.random()*10); 
		switch (i) {   
		case 1:{
			mess+="L'aventure s'arr�te ici";
			break;}
		case 2:{
			mess+="La tribue a d�cid� de vous exclure, revenez avec la hache sacr�e";
			break;}
		case 3:{
			mess+="Les modos, bannez moi celui la";
			break;}
		case 4:{
			mess+="Heureux qui, comme Ulysse, a fait un beau voyage";
			break;}
		case 5:{
			mess+="Contry road, Take me home";
			break;}
		case 6:{
			mess+="Pas de m�diath�que pour ceux qui abiment ce qu'on leur prette";
			break;}
		case 7:{
			mess+="Vous avez une nouvelle qu�te ! Partez d'ici !";
			break;}
		case 8:{
			mess+="Un jedi doit prendre soin de sa force, pars t'entrainer";
			break;}
		case 9:{
			mess+="if(retard || abim�) ## bannir();";
			break;
		}
		default:
			mess+="Le conseil a d�cid� de vous exclure et la sentence est irr�voquable";
		}
		return mess;
	}

	private boolean isAbim�() {
		double r=Math.random();
		if(r<=0.15) {
			System.out.println("[BDD] Vous avez une vision de bison M.Brette");
			return true ;
		}
		return false;
	}

	private void bannir(Abonne abo) {
		FinBannissement ancienne = m.bannir(abo);
		if(ancienne==null)
			System.out.println("[BDD] L'abonn� "+abo+" � �t� banni jusqu'au "+m.getDateBan(abo));
		else
			System.out.println("[BDD] L'abonn� "+abo+" qui �t� banni jusqu'au "+ancienne+", est maintenant banni jusqu'au "+m.getDateBan(abo));
	}
}

