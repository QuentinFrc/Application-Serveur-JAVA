package exception;

public class EmpruntException extends RuntimeException {
	private boolean needDate;
	
	public EmpruntException(String string) {
		super(string);
	}
	public EmpruntException(String string, boolean bool) {
		super(string);
		this.needDate =bool;
	}
	public boolean needDate() {
		return needDate;
	}
}
