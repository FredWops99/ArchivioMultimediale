package it.unibs.pajc;

import java.io.*;
import java.net.*;

public class MainClient 
{
	public static void main(String[] args) 
	{
		try		//METTO LA creazione oggetti tra tonde: così sono validi anche fuori dal BLOCCO TRY. TRY-WITH-RESOURCE
		(
			Socket client = new Socket("127.0.0.1", 1234);	
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		)
		
		{
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); //tastiera
			System.out.printf("(Funzionamento: digitare una stringa da inviare al server, esso la convertirà in UPPER CASE e la restituirà)\n");
			System.out.printf("Digitare \"quit\" per chiudere la connessione\n");
			String request, response;
			System.out.printf("\nRequest: ");
			while((request = stdin.readLine()) != null)
			{
				out.println(request);
				
				response = in.readLine();			//buffer in e out sono sincronizzati
				System.out.println("Response: " + response);
				
				System.out.printf("Request: ");
				if("QUIT".equals(response))
				break;
			}
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
		
		System.out.println("...CLIENT DISCONNESSO...");
	}

}
