package projet.serveurclient;

//Joueur1.java : Classe Joueur1 qui correspond à une classe client. 
//Va pouvoir communiquer avec le joueur 2 grâce à la classe WriterThread.java.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

//Appel de la classe WriterThread.java
import projet.serveurclient.WriterThread;

public class Joueur1 {
	static final int port= 8000;
	
					
		public static void main (String[] args) throws IOException {
			Socket socket= new Socket ("127.0.0.1", port);
			System.out.println("Bienvenue sur la partie Joueur 1");
			
			BufferedReader bw = new BufferedReader (new InputStreamReader(socket.getInputStream()));
			PrintWriter out= new PrintWriter(new BufferedWriter( new OutputStreamWriter (socket.getOutputStream())),true);
			
			
			// Lancement du Thread 'WriterThread'
			Thread Envoie = new Thread(new WriterThread(out));
			Envoie.start();

			while(true) {
				System.out.println(bw.readLine());
			}
	
		}
		
}

