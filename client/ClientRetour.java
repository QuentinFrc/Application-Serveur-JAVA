package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*ce client respecte le protocole BTTP (BretteText Transfer protocol)
    et ne contient aucune information
	liée au domaine traité par les services que le serveur va nous proposer
	
	mettre dans les arguments BTTP:host:port 
*/

public class ClientRetour {
		private static int PORT;
		private static String HOST; 
	
	public static void main(String[] args) throws IOException {
		BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
		// connexion
		//String url = args[0];
		String url ="BTTP:localhost:5000";
		try {validBTTP(url);} 
		catch (Exception e) {
				System.out.println(e.getMessage());
				return;
		}
		Socket socket = null;		
		try {
			socket = new Socket(HOST, PORT);
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
			// Informe l'utilisateur de la connection
			System.out.println("Connecté au serveur " + socket.getInetAddress() + ":"+ socket.getPort());
			
			String line;
			// protocole BTTP jusqu'à saisie de "0" ou fermeture coté service
			do {// réception et affichage de la question provenant du service
				line = sin.readLine();
				if (line == null) break; // fermeture par le service
				line = format(line);
				System.out.println(line);
				line = clavier.readLine();
				if (line.equals("")) break; // fermeture par le client
				// envoie au service de la réponse saisie au clavier
				sout.println(line);
			} while (true);
			socket.close();
		}
		catch (IOException e) { System.err.println("Fin du service" ); }
		// Refermer dans tous les cas la socket
		try { if (socket != null) socket.close(); } 
		catch (IOException e2) { ; }		
	}

	private static void validBTTP(String url) throws Exception { 
		String[] elts = url.split(":");
		if ((elts.length != 3) || 
				(!elts[0].equals("BTTP")) ||
				(!isNumeric(elts[2]))){
			throw new Exception("L'URL ne respecte pas le protocole BTTP");
		}
		HOST = elts[1]; 
		PORT =  Integer.parseInt(elts[2]);
	}

	private static boolean isNumeric(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	  private static String format(String s) {
	        s=s.replaceAll("##", "\n");
	        s=s.replaceAll("@@", "\n->");
	        return s;
	    }
}