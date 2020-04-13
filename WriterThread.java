package projet.serveurclient;

import java.io.PrintWriter;
import java.util.Scanner;

public class WriterThread implements Runnable {
	PrintWriter out;
	String mess= null;
	int i=0;
	public WriterThread(PrintWriter out1) {
		out=out1;	
	}
	public void run() {
		Scanner scanner = new Scanner (System.in);
			while(true && i<5) {
				mess=scanner.nextLine();
				if(mess.length()<50) {
					out.println(mess);
				} else {
					System.out.println("Message du Serveur : Trop de caractères! Reécrire le message.");
					i--;
				}
				i++;
			}
			System.out.println("Message du Serveur : Fin de la conversation. Perdu!");
			scanner.close();
		}
}

