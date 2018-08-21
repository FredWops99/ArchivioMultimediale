package view;

import model.Fruitore;
import model.Prestito;
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
	
	public static void filmsPrestabiliInPassato()
	{
		System.out.println(); //NEW LINE
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
	
	public static void noPrestitiRinnovvati()
	{
		System.out.println("Nessun prestito è ancora stato rinnovato");
	}
	
	public static void prestitiTerminati()
	{
		System.out.println("Prestiti che sono terminati: \n");
	}
	
	public static void noPrestitiTerminati()
	{
		System.out.println("Nessun prestito è ancora terminato");
	}
	
	public static void prestitiTerminatiInAnticipo()
	{
		System.out.println("Prestiti terminati prima della data di scandenza: ");
	}
	
	public static void noPrestitiTerminatiInAnticipo()
	{
		System.out.println("Nessun prestito è terminato prima della sua data di scandenza");
	}

	public static void fruitoreDecaduto(Fruitore fruitore) 
	{
		System.out.println("- " + fruitore.getNome() + " " + fruitore.getCognome() + " (il " + 
				GestioneDate.visualizzaData(fruitore.getDataScadenza()) + ")");
	}

	public static void prestitoProrogato(Prestito prestito) 
	{
		System.out.println("- "+ prestito.getRisorsa() + " (il " + prestito.getDataPerRichiestaProroga());

	}

	public static void prestitoTerminato(Prestito prestito) 
	{
		String risorsa = prestito.getRisorsa().getClass().getSimpleName();
		String titoloRisorsa = prestito.getRisorsa().getTitolo();
		String fruitore = prestito.getFruitore().getUser();
		String dataTermine = GestioneDate.visualizzaData(prestito.getDataRitorno());
		
		System.out.println(risorsa + ": "+ titoloRisorsa + ", Fruitore: " + fruitore + " (il " + dataTermine + ")");
	}

	public static void prestitoTerminatoInAnticipo(Prestito prestito) 
	{
		String risorsa = prestito.getRisorsa().getClass().getSimpleName();
		String titoloRisorsa = prestito.getRisorsa().getTitolo();
		String fruitore = prestito.getFruitore().getUser();
		String dataTermine = GestioneDate.visualizzaData(prestito.getDataRitorno());
		String dataScadenza = GestioneDate.visualizzaData(prestito.getDataScadenza());

		System.out.println(risorsa + ": "+ titoloRisorsa + ", Fruitore: " + fruitore + " (terminato il " + dataTermine + 
				", scandenza il " + dataScadenza + ")");

	}
}