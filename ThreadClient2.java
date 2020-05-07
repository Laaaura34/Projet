package projet.serveurclient;

//ThreadClient2.java :  spécifique du Joueur 2.
//Permet au joueur 2 d'envoyer un message au joueur1 sans altération du nombre de caractères.
//Contient la fonction suivante :
//-> Comparaison entre la phrase que le Joueur 2 renvoie au joueur 1 et le mot-but. Si le mot est dans le message : 
//true. Donc la partie s'arrête et elle est Gagnée.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// Appel de la Classe ThreadClient1.java
import projet.serveurclient.ThreadClient1;

public class ThreadClient2 extends Thread {
	private Socket socket1, socket2;
	private ServerSocket serverSocket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	public String mot;
	 
	//Constructeur de la classe ThreadClient2 qui prend en compte deux sockets.	   
	public ThreadClient2(Socket socket1, Socket socket2) throws Exception{
		this.socket1=socket1;
		this.socket2=socket2;
	}
	
	// Fonction qui recherche si le mot but est égal au message. Elle renvoie un boolean.
	public boolean comparaison(String Mot, String Texte) {
	
	  String [] lesMots=Texte.split(" "); 
	  boolean trouve =false;
	  for (int i=0 ; i<lesMots.length ;i++) { 
		  //Vérifie si le mot au rang i du message du Joueur2 correspond au mot-but.
		  if(lesMots[i].equalsIgnoreCase(Mot)) { 
			  trouve=true;
			  System.out.println("Le joueur2 à trouvé le mot recherché! Partie Gagnée.");
			  break;
		  }
	  }
	  return trouve;
	}

	
	//Lancement du run() de ThreadClient2.java
	public void run() {	
		Scanner s = new Scanner(System.in);
		int i=0;
			try {
				//Flux entrant du Joueur1
				BufferedReader bw1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
				
				//Flux entrant du Joueur2
				BufferedReader bw2 = new BufferedReader( new InputStreamReader(socket2.getInputStream()));
				
				//Flux sortant du Joueur1
				PrintWriter out1 = new PrintWriter(socket1.getOutputStream(),true);
				
				//Flux sortant du Joueur1
				PrintWriter out2 = new PrintWriter(socket2.getOutputStream(),true);
					
				try {	
				//Renvoie au client le message écrit du Joueur2  au Joueur1 sans dénaturation du message. 
				String J2 = "Recu de Joueur 2 : ";
				String J2Serveur = "Message du Joueur2 : ";
				while(i<5) {
					String message = bw2.readLine();
					out1.println(J2 + message);
					System.out.println(J2Serveur + message );

					//Comparaison du mot-but avec le message du Joueur2
					if(comparaison(ThreadClient1.lemot,message)==true) {
						
							//Si true : envoie simultanément au deux Joueurs "Gagné! Fin de la partie"
							// -> Grâce au synchronized
						synchronized(this) {
							notifyAll();  // notifyAll() permet d'envoyer en simultané les messages.
							out1.println("Gagne! Fin de la partie");
							out2.println("Gagne! Fin de la partie");
						
						
						} 
					i++;
					}
					
				}
				}catch (IOException ex) {
					socket1.close();
					socket2.close();
				}
				
			}catch (IOException e ) {
				
				//'Déconnexion' lorsque un des joueur quitte la partie.
				System.out.println("Déconnexion");
			
			}
		}		
					
				
	}
