package view;

import model.Prestiti;
import myLib.GestioneDate;
import myLib.InputDati;

public class StoricoView 
{
	public static void prestitiPerAnnoSolare(int prestitoAnnoSolare)
	{
		System.out.println("Nell'anno solare selezionato ci sono stati " + prestitoAnnoSolare +" prestiti");
	}
	
	public static void proroghePerAnnoSolare(int prorogheAnnoSolare)
	{
		System.out.println("Nell'anno solare selezionato sono stati prorogati " + prorogheAnnoSolare +" prestiti");
	}
	
	public static int AnnoSelezionato()
	{
		return InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio
	}
	
	public static void noPrestitiInAnnoSelezionato()
	{
		System.out.println("Nell'anno selezionato non sono stati richiesti prestiti");
	}
	
	public static void risorsaConPiùPrenotazioniInAnnoSelezionato(String titoloRisorsaMax, int maxGenerale)
	{
		System.out.println("La risorsa che ha avuto più prenotazioni è: " + titoloRisorsaMax + ", con un totale di " + maxGenerale + " prestiti");
	}
	
	public static void numeroPrestitiPerUtente(String fruitoreInConsiderazione,int nPrestiti)
	{
		System.out.println("L'utente " + fruitoreInConsiderazione + " ha richiesto " + nPrestiti + " prestiti nell'anno selezionato");
	}
	
	public static void libriPrestabiliInPassato()
	{
		System.out.println("Libri che erano disponibili al prestito e che ora non lo sono più: ");
	}
	
	public static void libroPrestabileInPassato(String NomeLibro)
	{
		System.out.println("- " + NomeLibro);
	}
	
	public static void nessuno()
	{
		System.out.println("Nessuno");
	}
	
	public static void newLine()
	{
		System.out.println(); //NEW LINE
	}
	
	public static void filmsPrestabiliInPassato()
	{
		System.out.println("Film che erano disponibili al prestito e che ora non lo sono più: ");
	}
	
	public static void filmPrestabileInPassato(String NomeFilm)
	{
		System.out.println("- " + NomeFilm);
	}
	
	public static void fruitoriDecaduti()
	{
		System.out.println("Fruitori decaduti: \n");
	}
	
	public static void fruitoreDecaduto(String nomeFruitore, String cognome, String dataDcesso)
	{
		System.out.println("- " + nomeFruitore + " " + cognome
				+ " (il " + dataDcesso + ")");
	}
	
	public static void noIscrizioniDecadute()
	{
		System.out.println("Nessuna iscrizione è ancora decaduta");
	}
	
	public static void iscrizioniRinnovate()
	{
		System.out.println("Iscrizioni rinnovate: \n");
	}
	
	public static void fruitoreRiiscritto(String fruitore)
	{
		System.out.println("Il fruitore " + fruitore+ " ha rinnovato la sua iscrizione nelle seguenti date:");
	}
	
	public static void dateRinnovo(String dataRinnovo)
	{
		System.out.println("- "+ dataRinnovo);
	}
	
	public static void nessunFruitoreHaRinnovato()
	{
		System.out.println("Nessun fruitore ha ancora rinnovato la sua iscrizione");
	}
	
	public static void prestitiProrogati()
	{
		System.out.println("Prestiti che sono stati rinnovati: \n");
	}
	
	public static void prestitoProrogato(String risorsaProrogata, String dataProroga)
	{
		System.out.println("- "+ risorsaProrogata + " (il " + dataProroga);
	}
	
	public static void noPrestitiRinnovvati()
	{
		System.out.println("Nessun prestito è ancora stato rinnovato");
	}
	
	public static void prestitiTerminati()
	{
		System.out.println("Prestiti che sono terminati: \n");
	}
	
	public static void prestitoTerminato(String risorsa,String titoloRisorsa,String fruitore,String dataTermine)
	{
		System.out.println(risorsa + ": "+ titoloRisorsa + ", Fruitore: " + fruitore + " il " + dataTermine + ")");
	}
	
	public static void noPrestitiTerminati()
	{
		System.out.println("Nessun prestito è ancora terminato");
	}
	
	public static void prestitiTerminatiInAnticipo()
	{
		System.out.println("Prestiti terminati prima della data di scandenza: ");
	}
	
	public static void prestitoTerminatoInAnticipo(String risorsa,String titolo,String fruitore,String dataTermine,String dataScadenza) 
	{
		System.out.println(risorsa + ": "+ titolo + ", Fruitore: " + fruitore + " (terminato il " + dataTermine +", scandenza il " + dataScadenza + ")");
	}
	
	public static void noPrestitiTerminatiInAnticipo()
	{
		System.out.println("Nessun prestito è terminato prima della sua data di scandenza");
	}
}