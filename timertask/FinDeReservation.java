package timertask;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import mediatheque.Document;
import mediatheque.Mediatheque_SYS;
import ressources.Abonne;

public class FinDeReservation extends TimerTask{
	private Document docRéservé;
	private Abonne abonné;
	private Mediatheque_SYS media;
	private Timer t;
	private LocalDateTime dateReserv;
	private static final long duréeReservation = 7200000;
	
	public Timer getTimer() {return t;}
	
	public FinDeReservation(Mediatheque_SYS m, Document d , Abonne abo) {
		this.media = m;
		this.docRéservé = d;
		this.abonné = abo;
		this.t =new Timer();
		this.dateReserv = LocalDateTime.now();
		t.schedule(this, duréeReservation);
	}

	public LocalDateTime getDateReserv() {
		return dateReserv;
	}

	@Override
	public void run() {
		media.déréserver(abonné, docRéservé);
		this.t.cancel();
		System.out.println("[BDD] Le document "+docRéservé+" n'est plus réservé par : "+abonné+".");
	}
	public String toString() {
		int heure = this.dateReserv.getHour()+2;
		String heureFormaté = (heure+2<10) ? "0"+heure : ""+heure;
		int minutes = this.dateReserv.getMinute();
		String minutesFormaté = (minutes<10) ? "0"+minutes : ""+minutes ;
		return heureFormaté+"h"+minutesFormaté;
	}

	public static long getDurée() {
		// TODO Auto-generated method stub
		return duréeReservation;
	}
}
