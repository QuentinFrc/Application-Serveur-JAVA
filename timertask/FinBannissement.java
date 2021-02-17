package timertask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import mediatheque.Mediatheque_SYS;
import ressources.Abonne;

public class FinBannissement extends TimerTask {
	//private static final long dur�eBannissement = 10000;//a changer
	private Abonne banni;
	private Timer t;
	private LocalDateTime dateBan;
	private Mediatheque_SYS m;
	private LocalDate deban;

	public FinBannissement(Abonne abo, Mediatheque_SYS m) {
		this.banni = abo;
		this.t = new Timer();
		this.dateBan = LocalDateTime.now();
		this.m = m;
		this.deban= LocalDate.now().plusMonths(1);
		t.schedule(this,Date.from(deban.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}
	
	public LocalDateTime getDateBan() {
		return this.dateBan;
	}

	@Override
	public void run() {
		m.d�bannir(banni);
		t.cancel();
		System.out.println("[BDD] L'abonn� "+banni+" : ["+banni.getNumero()+"] est d�banni.");
	}
	
	public String toString() {
		String s = new String();
		s+=" Date du d�bannissement : "+this.deban;
		return s;
	}

}
