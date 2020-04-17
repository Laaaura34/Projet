package projet.serveurclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import projet.serveurclient.Joueur1;

public class ThreadClient extends Thread {
	private Socket socket1, socket2;
	private ServerSocket serverSocket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	public String mot;
	private int local1;
	
	public ThreadClient(Socket socket1, Socket socket2) throws Exception{
		this.socket1=socket1;
		this.socket2=socket2;
	}	
	// Liste de mots-but
	String[] tab = {"Voiture","Chaise","Cahier","Ordinateur","Oreiller","Lavabo","Chat","Trousse","Cadre","Travail"};
	
	public void run() {	
		Scanner s = new Scanner(System.in);
		int i=0;
		local1= socket1.getLocalPort();
		StringBuilder message = null;
			try {
					BufferedReader bw = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
					PrintWriter out = new PrintWriter(socket2.getOutputStream(),true);
					PrintWriter out1 = new PrintWriter(socket1.getOutputStream(),true);
					
					// if si le local port == local port du J1
					// Renvoie le mot-but au client.
					String mot1 = "Le mot à découvrir est : " ;
					String lemot = this.getTab();
					mot = mot1 + lemot;
					if(local1 == Joueur1.local ) { 
						out1.println(mot);
					} 
					
					//Renvoie au client le message écrit du client 1 au client 2 avec les 30% de caractères en moins. 
					while(true) {
						message=getSuppr(bw.readLine());
						out.println(message);
					}
					
			}catch (IOException e ) {
				//'Déconnexion' lorsque un des joueur quitte la partie.
				System.out.println("Déconnexion");
			}
	
		}		
		
		// Fonction qui recherche si le mot but est égal au message. Elle renvoie un boolean.
		public boolean comparaison(String Mot, String Texte) {
			String [] lesMots=Texte.split("\\p{javaWhitespace}+");
			boolean trouve = false;
			for (int i=0; i<lesMots.length;i++) {
				if(lesMots[i].equals(Mot)) {
					trouve=true;
				}
			}
			return trouve;
		}
	
	
		//Fonction qui renvoie aléatoirement un mot but choisi dans une liste de mot. 
		public String getTab() {
					int random =(int) (Math.random()*tab.length);
					return tab[random];
				
		}
		//Fonction qui supprime 30% des caractères de la phrases écrite
		public StringBuilder getSuppr(String str) {
			int trs= str.length();
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


