package projet.serveurclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import projet.serveurclient.WriterThread;

public class Eleve1 {
	static final int port= 6148;
	
		public static void main (String[] args) throws IOException {
			Socket socket= new Socket ("127.0.0.1", port);
			System.out.println("SOCKET =" + socket);
			
			BufferedReader bw = new BufferedReader (new InputStreamReader(socket.getInputStream()));
			PrintWriter out= new PrintWriter(new BufferedWriter( new OutputStreamWriter (socket.getOutputStream())),true);

	
				Thread Envoie = new Thread(new WriterThread(out));
				Envoie.start();
			
				while(true) {
					System.out.println(bw.readLine());
				}
	
		}
}
