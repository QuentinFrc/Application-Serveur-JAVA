package server;

import java.io.IOException;

import java.net.ServerSocket;

import mediatheque.Mediatheque_SYS;
import service.ServiceEmprunt;

public class ServeurEmprunt implements Runnable {
    private ServerSocket listen_socket;
    private Mediatheque_SYS m;
    
    // Cree un serveur TCP - objet de la classe ServerSocket
    public ServeurEmprunt (int port, Mediatheque_SYS m1) throws IOException {
        listen_socket = new ServerSocket(port);
        this.m=m1;
    }

    // Le serveur ecoute et accepte les connexions. 
    public void run() {
        try {
            System.err.println("Lancement du serveur au port "+this.listen_socket.getLocalPort());
            while(true)
                new Thread(new ServiceEmprunt(listen_socket.accept(),m)).start();
        }
        catch (IOException e) { 
            try {this.listen_socket.close();} catch (IOException e1) {}
            System.err.println("Arr�t du serveur au port "+this.listen_socket.getLocalPort());
        }
    }

     // restituer les ressources --> finalize
    protected void finalize() throws Throwable {
        try {this.listen_socket.close();} catch (IOException e1) {}
    }
}