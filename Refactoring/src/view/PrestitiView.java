package view;

import model.Prestito;
import model.Risorsa;

public class PrestitiView 
{
	public static void visualizzaPrestito(Prestito prestito) 
	{
		System.out.println(prestito.toString());
	}
	
	public static void messaggioUtenteMinorenne() 
	{
		System.out.println("Ci dispiace, per accedere devi essere maggiorenne");
	}

	public static void raggiunteRisorseMassime(String categoria) 
	{
		System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
				+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
	}

//	va in risorsaView?
	public static void prenotazioneEffettuata(Risorsa risorsa) 
	{
		System.out.println(risorsa.getTitolo() + " prenotato con successo!");		
	}

//	va in risorsa view?
	public static void risorsaPosseduta() 
	{
		System.out.println("Prenotazione rifiutata: possiedi già questa risorsa in prestito");	
	}

	public static void numeroRisorseTornateDaPrestito(int tornati) 
	{
		System.out.println("Risorse tornate dal prestito: " + tornati);	
	}

	public static void noPrestitiAttivi() 
	{
		System.out.println("Al momento non sono presenti prestiti attivi");	
	}
}
