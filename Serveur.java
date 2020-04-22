package projet.serveurclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import projet.serveurclient.ThreadClient1;

//Serveur.java : Classe serveur qui permet le lancement et la synchronisation des ThreadClient 1 et 2.
//Le serveur attend que les 2 joueurs se connectent afin de pouvoir les accepter et démarrer la communication.
//Il peut gérer plusieurs communications clientes à la fois grâce au Multithreading.

public class Serveur {
	String hostName ="127.0.0.1";
	static int portNumber= 8000;
	
	public static void main (String[] args) throws Exception {
			System.out.println("Serveur écoute");
			ServerSocket s = null;
		try {
			
			//Création socket serveur
			s = new ServerSocket(portNumber);
			while(true) {
				
				
				//Connexion du Joueur1
				Socket soc1;
				soc1=s.accept();
			System.out.println("Nouveau Joueur 1 connecté: "+soc1);
			
			
				//Connexion du Joueur2
				Socket soc2;
				soc2=s.accept();
			System.out.println("Nouveau Joueur 2 connecté: "+soc2);
			
			
				//Lancement des threads grâce à la classe ThreadClient
				new ThreadClient1(soc1,soc2).start();
				new ThreadClient2(soc1,soc2).start();
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}		
