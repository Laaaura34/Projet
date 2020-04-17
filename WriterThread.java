package projet.serveurclient;

import java.io.PrintWriter;
import java.util.Scanner;
import projet.serveurclient.ThreadClient;

public class WriterThread implements Runnable {
	PrintWriter out;
	String mess= null;
	int i=0;
	public WriterThread(PrintWriter out1) {
		out=out1;	
	}
	public void run() {
		Scanner scanner = new Scanner (System.in);
			//Boucle qui dure 5+5 messages
			//Si à la fin des 5+5 messages le mot n'est pas trouver : Perdu. 
			while(true && i<5) {
				mess= scanner.nextLine();
				// On vérifie si la longueur du message est inférieur à 50 caractères. 
				// Sinon on propose au joueur de retenter sa chance.
				if(mess.length()<50) {
					out.println(mess);
				} else {
					System.out.println("Message du Serveur : Trop de caractères! Réecrire le message.");
					i--;
				}
				i++;
			}
			System.out.println("Message du Serveur : Fin de la conversation. Perdu!");
			scanner.close();
		}
}


