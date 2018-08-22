package view;

import model.Prestito;
import model.Risorsa;
import myLib.GestioneDate;
import myLib.InputDati;

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
		System.out.println("Prenotazione rifiutata: possiedi gi‡ questa risorsa in prestito");	
	}

	public static void numeroRisorseTornateDaPrestito(int tornati) 
	{
		System.out.println("Risorse tornate dal prestito: " + tornati);	
	}

//	per l'operatore
	public static void noPrestitiAttivi() 
	{
		System.out.println("Al momento non sono presenti prestiti attivi");	
	}

//	per il fruitore
	public static void noPrestiti() 
	{
		System.out.println("Non hai prestiti attivi!");	
	}

	public static void prestitoDaTerminare() 
	{
		System.out.println("Seleziona il prestito che vuoi terminare: ");	
	}

	public static int chiediRisorsaDaTerminare(int size) 
	{
		return InputDati.leggiIntero("\nSeleziona la risorsa della quale vuoi terminare il prestito (0 per annullare): ", 0, size);
	}

	public static void prestitoTerminato() 
	{
		System.out.println("Prestito terminato!");	
	}

	public static void prestitiEliminati() 
	{
		System.out.println("i tuoi prestiti sono stati eliminati");	
	}

	public static void noRinnovi() 
	{
		System.out.println("Non hai prestiti attivi da rinnovare!");	
	}

	public static void selezionaRinnovo() 
	{
		System.out.println("Seleziona il prestito che vuoi rinnovare: ");	
	}

	public static int chiediRisorsaDaRinnovare(int size) 
	{
		return InputDati.leggiIntero
				("\nSeleziona la risorsa per la quale chiedere il rinnovo del prestito (0 per annullare): ", 0, size);
	}

	public static void prestitoGi‡Prorogato() 
	{
		System.out.println("Hai gi‡ prorogato questo prestito: puoi eseguire questa azione solo una volta per ogni prestito");	
	}

	public static void prestitoNonRinnovabile(Prestito prestitoSelezionato) 
	{
		System.out.println("Il tuo prestito non Ë ancora rinnovabile: ");
		System.out.println("potrai effettuare questa operazione tra il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataPerRichiestaProroga()) + 
																" e il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataScadenza()));	
	}
}
