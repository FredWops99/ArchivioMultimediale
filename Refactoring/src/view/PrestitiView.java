package view;

import interfaces.Risorsa;
import model.Prestito;
import myLib.GestioneDate;
import myLib.InputDati;
import viewInterfaces.IPrestitiView;

public class PrestitiView implements IPrestitiView
{
	public void visualizzaPrestito(Prestito prestito) 
	{
		System.out.println(prestito.toString());
	}
	
	public void messaggioUtenteMinorenne() 
	{
		System.out.println("Ci dispiace, per accedere devi essere maggiorenne");
	}

	public void raggiunteRisorseMassime(String categoria) 
	{
		System.out.println("\nNon puoi prenotare altri " + categoria + ": " 
				+ "\nHai raggiunto il numero massimo di risorse in prestito per questa categoria");
	}

	public void prenotazioneEffettuata(Risorsa risorsa) 
	{
		System.out.println(risorsa.getTitolo() + " prenotato con successo!");		
	}

	public void risorsaPosseduta() 
	{
		System.out.println("Prenotazione rifiutata: possiedi gi‡ questa risorsa in prestito");	
	}

	public void numeroRisorseTornateDaPrestito(int tornati) 
	{
		System.out.println("Risorse tornate dal prestito: " + tornati);	
	}

//	per l'operatore
	public void noPrestitiAttivi() 
	{
		System.out.println("Al momento non sono presenti prestiti attivi");	
	}

//	per il fruitore
	public void noPrestiti() 
	{
		System.out.println("Non hai prestiti attivi!");	
	}

	public void prestitoDaTerminare() 
	{
		System.out.println("Seleziona il prestito che vuoi terminare: ");	
	}

	public int chiediRisorsaDaTerminare(int size) 
	{
		return InputDati.leggiIntero("\nSeleziona la risorsa della quale vuoi terminare il prestito (0 per annullare): ", 0, size);
	}

	public void prestitoTerminato() 
	{
		System.out.println("Prestito terminato!");	
	}

	public void prestitiEliminati() 
	{
		System.out.println("i tuoi prestiti sono stati eliminati");	
	}

	public void noRinnovi() 
	{
		System.out.println("Non hai prestiti attivi da rinnovare!");	
	}

	public void selezionaRinnovo() 
	{
		System.out.println("Seleziona il prestito che vuoi rinnovare: ");	
	}

	public int chiediRisorsaDaRinnovare(int size) 
	{
		return InputDati.leggiIntero
				("\nSeleziona la risorsa per la quale chiedere il rinnovo del prestito (0 per annullare): ", 0, size);
	}

	public void prestitoGi‡Prorogato() 
	{
		System.out.println("Hai gi‡ prorogato questo prestito: puoi eseguire questa azione solo una volta per ogni prestito");	
	}

	public void prestitoNonRinnovabile(Prestito prestitoSelezionato) 
	{
		System.out.println("Il tuo prestito non Ë ancora rinnovabile: ");
		System.out.println("potrai effettuare questa operazione tra il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataPerRichiestaProroga()) + 
																" e il " + GestioneDate.visualizzaData(prestitoSelezionato.getDataScadenza()));	
	}
}
