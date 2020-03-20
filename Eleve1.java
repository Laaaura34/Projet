package projet;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Eleve1 {
	static final int port= 6148;
	
	public static void main (String[]args) throws Exception {
		Socket socket = new Socket ("127.0.0.1", port);
		System.out.println("SOCKET =" + socket);
		
		BufferedReader plec = new BufferedReader (
								new InputStreamReader(socket.getInputStream())
								);
		
		PrintWriter pred= new PrintWriter(
							new BufferedWriter( 
								new OutputStreamWriter (socket.getOutputStream())),
							true);
		
		Scanner scanner = new Scanner (System.in);
		for ( int i=0; i<5;i++) {
			 String s = scanner.nextLine();
		    pred.println(s);
			String str = plec.readLine();
			System.out.println("Client reçoit:"+str);
		}
		
		System.out.println("FIN CLIENT");
		pred.println("END");
		pred.close();
		plec.close();
		socket.close();
		scanner.close();
	}

}
