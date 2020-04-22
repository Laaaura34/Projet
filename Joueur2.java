package projet.serveurclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
//Joueur2.java : Classe Joueur2 qui correspond à une classe client. 
// Va pouvoir communiquer avec le joueur 1 grâce à la classe WriterThread.java
public class Joueur2 {
	static final int port= 8000;
	
	public static void main (String[] args) throws IOException {
		Socket socket= new Socket ("127.0.0.1", port);
		System.out.println("Bienvenue sur la partie Joueur 2.");
		
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
