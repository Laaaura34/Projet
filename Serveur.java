package projet.serveurclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import projet.serveurclient.ThreadClient;

public class Serveur {
	String hostName ="127.0.0.1";
	static int portNumber= 6148;
	
	public static void main (String[] args) throws Exception {
			System.out.println("Serveur écoute");
			ServerSocket s = null;
		try {
			//Création socket serveur
			s = new ServerSocket(portNumber);
			while(true) {
			//Connexion client
				Socket soc1;
				soc1=s.accept();
			System.out.println("Nouveau Client connecté");
			
				Socket soc2;
				soc2=s.accept();
			System.out.println("Nouveau Client connecté");
			
			// Lancement des threads
				new ThreadClient(soc1,soc2).start();
				new ThreadClient(soc2,soc1).start();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}	
