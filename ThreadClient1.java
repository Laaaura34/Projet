package projet.serveurclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import projet.serveurclient.Joueur1;

//ThreadClient1.java :  spécifique du Joueur 1 qui comprend plusieurs fonction permettant l'envoie du message 
//au joueur2.
//Comprend les fonctions suivantes : 
// -> Supression de 30 % de caractères.
// -> Envoi d'un mot-but au Joueur 1 que celui-ci devra faire deviner au joueur 2.


public class ThreadClient1 extends Thread {
	private Socket socket1, socket2;
	public  String mot;
	public static String lemot;
	
	public ThreadClient1(Socket socket1, Socket socket2) throws Exception{
		this.socket1=socket1;
		this.socket2=socket2;
	}	
	// Liste de mots-but
	String[] tab = {"Voiture","Chaise","Cahier","Ordinateur","Oreiller","Lavabo","Chat","Trousse","Cadre","Mathématique"};
	
	public void run() {	
		Scanner s = new Scanner(System.in);
		int i=0;
		StringBuilder message = null;
			try {
					BufferedReader bw = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
					BufferedReader bw1 = new BufferedReader( new InputStreamReader(socket2.getInputStream()));
					PrintWriter out = new PrintWriter(socket1.getOutputStream(),true);
					PrintWriter out1 = new PrintWriter(socket2.getOutputStream(),true);
					
					// Renvoie le mot-but au joueur 1.
					String mot1 = "Le mot à faire découvrir est : " ;
					lemot = this.getTab();
					mot = mot1 + lemot;
					out.println( mot);
					
					
					//Renvoie au Joueur 2 le message écrit du client 1 avec les 30% de caractères en moins. 
					String J1 = "Reçu de Joueur 1 : ";
					while(true) {
						while(i<5) {
						message=getSuppr(bw.readLine());
						out1.println(J1 + message);
						i++;
						}
						
				
					}
					
			}catch (IOException e ) {
				//'Déconnexion' lorsque un des joueur quitte la partie.
				System.out.println("Déconnexion");
			}
	
		}		
		
		//Fonction qui renvoie aléatoirement un mot but choisi dans une liste de mot. 
		public String getTab() {
					int random =(int) (Math.random()*tab.length);
					return tab[random];
				
		}
		public String getLeMot() {
			return getTab();
		}
		
		
		//Fonction qui supprime 30% des caractères de la phrases écrite
		public StringBuilder getSuppr(String str) {
			int trs= str.replaceAll(" ", "").length();
			int calcul = trs - ((int) (trs*0.3));
			
			//Met nombres aléatoires dans une liste
			List<Integer> liste = new ArrayList<Integer>();

				while (liste.size()<calcul) {
					Random random = new Random();
					int nb = random.nextInt(trs);
					liste.add(nb);
					//Supprime les doublons dans la liste
					Set set = new HashSet() ;
			        set.addAll(liste) ;
			        liste = new ArrayList(set) ;   
				}	
				
			// Trie la liste par ordre croissant    
			Collections.sort(liste);		

			// Ajoute les caractères à garder dans un String
			StringBuilder sbf = new StringBuilder("");
			for (int i=0; i<calcul; i++) {
				int a= liste.get(i);
				sbf.append(str.charAt(a));				

			}
			return sbf;
		}
					
				
	}


