package projet;

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
	
	public static void main (String[] args) throws UnknownHostException, IOException {
		String hostName ="127.0.0.1";
		int portNumber= 6148;
		ServerSocket s = new ServerSocket(portNumber);
		System.out.println("Serveur écoute");
		
		Socket soc = s.accept();
		Socket soc1 = s.accept();
		
		BufferedReader plec = new BufferedReader( 
								new InputStreamReader( soc.getInputStream())
								);
		
		PrintWriter pred= new PrintWriter (
							new BufferedWriter(
								new OutputStreamWriter(soc.getOutputStream())),
							true);
		
		BufferedReader plec1 = new BufferedReader( 
						new InputStreamReader( soc1.getInputStream())
						);

		PrintWriter pred1= new PrintWriter (
						new BufferedWriter(
								new OutputStreamWriter(soc1.getOutputStream())),
						true);

		
		while(true) {
			 String str= plec.readLine();
		if(str.equals("END")) break;
		System.out.println("Serveur reçoit de Eleve 1 : "+str);
			pred.println(str);
		}
		while(true) {
		     String str= plec1.readLine();
		if(str.equals("END")) break;
		System.out.println("Serveur reçoit de Eleve 2 : "+str);
			pred1.println(str);
		}
		
//		while (true) {
//	     String str1= plec1.readLine();
//	     String str= plec.readLine();
//	     if(str.equals("END")) {
//	     	if(str1.equals("END")) {
//	     System.out.println("Serveur reçoit de Eleve 2 : "+str1);
//	     System.out.println("Serveur reçoit de Eleve 1 : "+str);
//	     pred.println(str);
//	     pred1.println(str1);
//	     	}
//	      }

		System.out.println("Fin Serveur");
		plec.close();
		pred.close();
		soc.close();
		plec1.close();
		pred1.close();
		soc1.close();
		s.close();
	}
}
