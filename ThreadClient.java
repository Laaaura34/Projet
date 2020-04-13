package projet.serveurclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

import projet.serveurclient.mots;

public class ThreadClient extends Thread {
	private Socket socket1, socket2;
	private ServerSocket serverSocket;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	
	public ThreadClient(Socket socket1, Socket socket2) throws Exception{
		this.socket1=socket1;
		this.socket2=socket2;
		
	}	
	String[] tab = {"Voiture","Chaise","Cahier","Ordinateur","Oreiller","Lavabo","Chat","Trousse","Cadre","Travail"};
	
	public void run() {	
		Scanner s = new Scanner(System.in);
		StringBuilder message = null;
			try {
					BufferedReader bw = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
					PrintWriter out = new PrintWriter(socket2.getOutputStream(),true);
					
					
						String mot = this.getTab();
						out.println(mot);
					
						
						while(true) { 
							message=getSuppr(bw.readLine());
							out.println(message);			
						}		
				
			}
			catch (IOException e ) {
				System.out.println("Déconnexion");
			}

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


