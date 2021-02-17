package timertask;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import mediatheque.Document;
import mediatheque.Mediatheque_SYS;
import ressources.Abonne;

public class FinDeReservation extends TimerTask{
	private Document docR�serv�;
	private Abonne abonn�;
	private Mediatheque_SYS media;
	private Timer t;
	private LocalDateTime dateReserv;
	private static final long dur�eReservation = 7200000;
	
	public Timer getTimer() {return t;}
	
	public FinDeReservation(Mediatheque_SYS m, Document d , Abonne abo) {
		this.media = m;
		this.docR�serv� = d;
		this.abonn� = abo;
		this.t =new Timer();
		this.dateReserv = LocalDateTime.now();
		t.schedule(this, dur�eReservation);
	}

	public LocalDateTime getDateReserv() {
		return dateReserv;
	}

	@Override
	public void run() {
		media.d�r�server(abonn�, docR�serv�);
		this.t.cancel();
		System.out.println("[BDD] Le document "+docR�serv�+" n'est plus r�serv� par : "+abonn�+".");
	}
	public String toString() {
		int heure = this.dateReserv.getHour()+2;
		String heureFormat� = (heure+2<10) ? "0"+heure : ""+heure;
		int minutes = this.dateReserv.getMinute();
		String minutesFormat� = (minutes<10) ? "0"+minutes : ""+minutes ;
		return heureFormat�+"h"+minutesFormat�;
	}

	public static long getDur�e() {
		// TODO Auto-generated method stub
		return dur�eReservation;
	}
}
