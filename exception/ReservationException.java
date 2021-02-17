
package exception;


public class ReservationException extends RuntimeException {
	private boolean needDate;
	
	public ReservationException(String string) {
		super(string);
	}
	public ReservationException(String string, boolean bool) {
		super(string);
		this.needDate=bool;
	}
	public boolean needDate() {
		return needDate;
	}
	public boolean réservé() {
		return true;
	}
}

