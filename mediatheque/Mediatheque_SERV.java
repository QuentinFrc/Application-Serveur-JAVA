package mediatheque;

import java.io.IOException;

import server.ServeurEmprunt;
import server.ServeurReservation;
import server.ServeurRetour;

public class Mediatheque_SERV {
	private final static int PORT_RESERVATION = 3000;
	private final static int PORT_EMPRUNT = 4000;
	private final static int PORT_RETOUR = 5000;
	private Thread servReserv;
	private Thread servEmprunt;
	private Thread servRetour;
	

	public Mediatheque_SERV(Mediatheque_SYS system) {
		try {
			this.servReserv = new Thread(new ServeurReservation(PORT_RESERVATION, system));
			this.servEmprunt = new Thread(new ServeurEmprunt(PORT_EMPRUNT, system));
			this.servRetour = new Thread(new ServeurRetour(PORT_RETOUR, system));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		this.servReserv.start();
		this.servEmprunt.start();
		this.servRetour.start();
	}
}
