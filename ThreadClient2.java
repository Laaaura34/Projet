package projet.serveurclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import projet.serveurclient.ThreadClient1;

//ThreadClient2.java :  spécifique du Joueur 2.
// Permet au joueur 2 d'envoyer un message au joueur1 sans altération du nombre de caractères.
//Contient la fonction suivante :
//-> Comparaison entre la phrase que le Joueur 2 renvoie au joueur 1 et le mot-but. Si le mot est dans le message : 
//true. Donc la partie s'arrête et elle est Gagnée.
//
public class ThreadClient2 extends Thread {
	private Socket socket1, socket2;
	private ServerSocket serverSocket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	public String mot;
	
	public ThreadClient2(Socket socket1, Socket socket2) throws Exception{
		this.socket1=socket1;
		this.socket2=socket2;
	}	

	public void run() {	
		Scanner s = new Scanner(System.in);
		int i=0;
			try {
					BufferedReader bw = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
					BufferedReader bw1 = new BufferedReader( new InputStreamReader(socket2.getInputStream()));
					PrintWriter out = new PrintWriter(socket1.getOutputStream(),true);
					PrintWriter out1 = new PrintWriter(socket2.getOutputStream(),true);
					
					
					//Renvoie au client le message écrit du client 1 au client 2 avec les 30% de caractères en moins. 
					String J2 = "Reçu de Joueur 2 : ";
					while(true && i<5) {
							out.println(J2 + bw1.readLine());
							if(comparaison(ThreadClient1.lemot,bw1.readLine())!=0) {
								synchronized(this) {
									notifyAll();
									//out.println(bw1.readLine());
									out.println("Gagné! Fin de la partie");
									out1.println("Gagné! Fin de la partie");
									i=4;
								}
							}
							i++;
					}
					synchronized(this) {
						out.println("Déconnexion");
						out1.println("Déconnexion");
						
					}
					
					
			}catch (IOException e ) {
				//'Déconnexion' lorsque un des joueur quitte la partie.
				System.out.println("Déconnexion");
			}
	
		}		
		
		// Fonction qui recherche si le mot but est égal au message. Elle renvoie un boolean.
		public int comparaison(String Mot, String Texte) {
			String [] lesMots=Texte.split("\\p{javaWhitespace}+");
			boolean trouve = false;
			int compt = 0;
			for (int i=0; i<lesMots.length;i++) {
				if(lesMots[i].equalsIgnoreCase(Mot)) {
					trouve=true;
					compt=+1;
					System.out.println("Le joueur2 à trouver le mot-but! Partie Gagnée.");
				}
			}
			return compt;
		}
					
				
	}
