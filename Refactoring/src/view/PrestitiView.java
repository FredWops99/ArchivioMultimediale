package view;

import model.Risorsa;

public class PrestitiView 
{
	public static void messaggioUtenteMinorenne() 
	{
		System.out.println("Ci dispiace, per accedere devi essere maggiorenne");
	}

	public static void raggiunteRisorseMassime(String categoria) 
	{
		System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
				+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
	}

	public static void prenotazioneEffettuata(Risorsa risorsa) 
	{
		System.out.println(risorsa.getTitolo() + " prenotato con successo!");		
	}

	public static void risorsaPosseduta() 
	{
		System.out.println("Prenotazione rifiutata: possiedi già questa risorsa in prestito");	
	}
}
