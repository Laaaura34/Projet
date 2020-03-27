package projetolk;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serveur {
	String hostName ="127.0.0.1";
	static int portNumber= 6148;
	
	String[] tab = {"Voiture","Chaise","Cahier","Ordinateur","Oreiller","Lavabo","Chat","Trousse","Cadre","Travail"};

	public String getTab() {
		int random =(int) (Math.random()*tab.length);
		return tab[random];
	}
	
	public void go() throws UnknownHostException, IOException {
		System.out.println("Serveur écoute");
		String reponse = null;
			ServerSocket s = null;
			//Création socket serveur
			try {
				s = new ServerSocket(portNumber);
			}catch(IOException e) {
				e.printStackTrace();
			}
			
			//Connexion client
			try {
				Socket soc;
				Socket soc1;
				BufferedReader plec =null;
				PrintWriter pred = null;
				while(true) {
				
				soc=s.accept();
				//soc1=s.accept();
				
				System.out.println("Nouveau Client connecté");
				
				ThreadClient t = new ThreadClient (s);
				Thread thread = new Thread (t);
				thread.start();
				
				//ThreadClient t1 = new ThreadClient (s,soc1);
				//Thread thread1 = new Thread (t1);
				//thread.start();

				
				
				  plec = new BufferedReader(new InputStreamReader( soc.getInputStream()));
				  pred= new PrintWriter (new BufferedWriter(new
				  OutputStreamWriter(soc.getOutputStream())),true);
				  
				  //String mot = this.getTab(); pred.println(mot); reponse = plec.readLine();
				 
				
				while(true) { 
					
				 String str= plec.readLine(); 
				if(str.equals("END")) break;
					System.out.println("Serveur reçoit de Eleve : "+str); 
					pred.println(str); }
				
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			 try {
		          s.close();
		      
		     } catch(IOException e) {
		           e.printStackTrace();
		     }
			    
		
		/*while (true) {
	      String str= plec.readLine();
	      String str1= plec1.readLine();
	     if(str.equals("END")) {
	     	if(str1.equals("END")) {
	     System.out.println("Serveur reçoit de Eleve 1 : "+str);
	     System.out.println("Serveur reçoit de Eleve 2 : "+str1);
	     pred.println(str);
	     pred1.println(str1);
	     	}
		}
		 */

		System.out.println("Fin Serveur");
		plec.close();
		pred.close();
		soc.close();
		s.close(); 
	}
	
	public static void main (String[] args) throws UnknownHostException, IOException {
		Serveur serv = new Serveur();
		serv.go();

	}
}
