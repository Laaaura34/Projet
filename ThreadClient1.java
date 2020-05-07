package projet.serveurclient;

//ThreadClient1.java :  spécifique du Joueur 1 qui comprend plusieurs fonction permettant l'envoie du message 
//au joueur2.
//Comprend les fonctions suivantes : 
//-> Supression de 30 % de caractères.
//-> Envoi d'un mot-but au Joueur 1 que celui-ci devra faire deviner au joueur 2.
//-> Fonction qui supprime le mot-but si le Joueur1 le prononce avant de l'envoyer au Joueur2.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class ThreadClient1 extends Thread {
	private Socket socket1, socket2;
	public  String mot;
	public static String lemot;
	
	//Constructeur de la classe ThreadClient1 qui prend en compte deux sockets.
	public ThreadClient1(Socket socket1, Socket socket2) throws Exception{
		this.socket1=socket1;
		this.socket2=socket2;
	}	
	// Liste de mot-but
	String[] tab = {"Voiture","Chaise","Cahier","Chat","Trousse"};


	//Fonction qui renvoie aléatoirement un mot but choisi dans une liste de mot. 
	public String getTab() {
				int random =(int) (Math.random()*tab.length);
				return tab[random];
	}

	// Fonction qui supprime le mot-but si celui-ci est dans la phrase du joueur 1
	private String SuppressionMot(String Mot, String Texte) throws InterruptedException {
		boolean trouve = false;
		String newCh ="";
		int i =0;
		if(Texte.equalsIgnoreCase(Mot)) { 	// Recherche dans le message si le mot est présent. 
			newCh=Texte.replaceAll(Mot, "**");	// Remplace le mot par un espace. 
			trouve= true;
		} else {
			newCh = Texte;	//Sinon, on remplace la nouvelle chaîne de caractères par l'ancienne. 
		}
		return newCh;	//Retourne la nouvelle chaîne de caractère modifiée ou pas. 
	}
	
	
	// Fonction qui permet de ne pas prendre en compte les accents et la casse des mots. 
	// Utilisation de Normalizer. 
	public static String newFormat(String str) {
		String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}
	
	
	//Fonction qui supprime 30% des caractères/mots de la phrases écrite
	public String getSuppr30(String str) {
		
		str = str.toLowerCase();
		String Message = newFormat(str);
		Random random = new Random();	// Random : nombre aléatoire.
		String MessageSortie = ""; // un String : correpond à la nouvelle chaîne de caractère.
		String lesMots[]= Message.split(" "); // Permet de délimiter chaque mot : un espace correspond à un nouveau mot.
		
		for (int i=0; i<lesMots.length;i++) { // Tant qu'il y a des mots dans le message on continu. 
			double trs = (0.3*lesMots[i].length()); // On calcule 30% des caractères du mot i. 
		
			for (int k = 0; k<trs;k++) {
				int a = random.nextInt(lesMots[i].length()); 	// Correspond aux caractères que nous allons supprimer.
				lesMots[i]= lesMots[i].replace(lesMots[i].charAt(a), '.');	 // Remplace les caractères à supprimer par '.'
			}
			MessageSortie += lesMots[i]+ " "; 	// Correspond à la nouvelle chaîne de caractère : on ajoute un mot après l'autre que l'on sépare d'espaces.
		}
		return newFormat(MessageSortie);	
	}
	
	
	
	//Lancement du run() de ThreadClient1.java
	public void run() {	
		Scanner s = new Scanner(System.in);
		String message = null;
		String NewMess = null;
			try {
					// Flux entrant du Joueur1
					BufferedReader bw1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
					
					// Flux entrant du Joueur2
					BufferedReader bw2 = new BufferedReader( new InputStreamReader(socket2.getInputStream()));
					
					// Flux sortant du Joueur1
					PrintWriter out1 = new PrintWriter(socket1.getOutputStream(),true);
					
					// Flux sortant du Joueur2
					PrintWriter out2 = new PrintWriter(socket2.getOutputStream(),true);
					
					
					// Renvoie le mot-but au joueur 1.
					String mot1 = "Le mot à faire découvrir est : " ;
					lemot = this.getTab();
					mot = mot1 + lemot;
					out1.println( mot);
					System.out.println(mot); // On affiche au serveur le mot à faire découvrir.
					
					
					//Renvoie au Joueur 2 le message du Joueur1 avec les 30% de caractères en moins. 
					String J1 = "Recu de Joueur 1 : ";
					String J1Serveur = "Message du Joueur1 (sans modification) : ";
					while(true) {
						String accent = newFormat(bw1.readLine());
						NewMess=SuppressionMot(lemot,accent);
						message=getSuppr30(NewMess);
						out2.println(J1 + message);
					
						System.out.println(J1Serveur + accent);
					}
					
			}catch (IOException e ) {
				
				//'Déconnexion' lorsque un des joueurs quitte la partie.
				System.out.println("Déconnexion");
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
	
		}		
}




