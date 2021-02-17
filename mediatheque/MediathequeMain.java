package mediatheque;

public class MediathequeMain {
	private static Mediatheque_SYS system;
	private static Mediatheque_SERV server;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 setSystem_(new Mediatheque_SYS("mediathequejavalol@hotmail.com", "thequeMedia.56"));
		 try {
			system.init();
		} catch (Exception e) {
			System.err.println(e);
		}
		 setServer_(new Mediatheque_SERV(system));
		 server.init();
	}

	//-----------------GETTER / SETTER -----------------\\
	public static void setSystem_(Mediatheque_SYS mediatheque_SYS) {
		MediathequeMain.system = mediatheque_SYS;
	}
	public static Mediatheque_SERV getServer_() {
		return server;
	}
	public static void setServer_(Mediatheque_SERV mediatheque_serv) {
		MediathequeMain.server = mediatheque_serv;
	}
}
