package view;

import model.Fruitore;
import model.Prestito;
import myLib.GestioneDate;
import myLib.InputDati;
import viewInterfaces.IStoricoView;

public class StoricoView implements IStoricoView
{
	public void prestitiPerAnnoSolare(int prestitiAnnoSolare)
	{
		System.out.println("Nell'anno solare selezionato ci sono stati " + prestitiAnnoSolare +" prestiti");
	}
	
	public void proroghePerAnnoSolare(int prorogheAnnoSolare)
	{
		System.out.println("Nell'anno solare selezionato sono stati prorogati " + prorogheAnnoSolare +" prestiti");
	}
	
	public int selezionaAnno()
	{
		return InputDati.leggiIntero("Inserisci l'anno: ", 1980, GestioneDate.ANNO_CORRENTE);//si potrà mettere l'anno del prestito più vecchio
	}
	
	public void noPrestitiInAnnoSelezionato()
	{
		System.out.println("Nell'anno selezionato non sono stati richiesti prestiti");
	}
	
	public void risorsaConPiùPrenotazioniInAnnoSelezionato(String titoloRisorsaMax, int maxGenerale)
	{
		System.out.println("La risorsa che ha avuto più prenotazioni è: " + titoloRisorsaMax + ", con un totale di " + maxGenerale + " prestiti");
	}
	
	public void numeroPrestitiPerUtente(String fruitoreInConsiderazione,int nPrestiti)
	{
		System.out.println("L'utente " + fruitoreInConsiderazione + " ha richiesto " + nPrestiti + " prestiti nell'anno selezionato");
	}
	
	public void libriPrestabiliInPassato()
	{
		System.out.println("Libri che erano disponibili al prestito e che ora non lo sono più: ");
	}
	
	public void libroPrestabileInPassato(String NomeLibro)
	{
		System.out.println("- " + NomeLibro);
	}
	
	public void nessuno()
	{
		System.out.println("Nessuno");
	}
	
	public void filmsPrestabiliInPassato()
	{
		System.out.println(); //NEW LINE
		System.out.println("Film che erano disponibili al prestito e che ora non lo sono più: ");
	}
	
	public void filmPrestabileInPassato(String NomeFilm)
	{
		System.out.println("- " + NomeFilm);
	}
	
	public void fruitoriDecaduti()
	{
		System.out.println("Fruitori decaduti: \n");
	}
	
	public void noIscrizioniDecadute()
	{
		System.out.println("Nessuna iscrizione è ancora decaduta");
	}
	
	public void iscrizioniRinnovate()
	{
		System.out.println("Iscrizioni rinnovate: \n");
	}
	
	public void fruitoreRiiscritto(String fruitore)
	{
		System.out.println("Il fruitore " + fruitore+ " ha rinnovato la sua iscrizione nelle seguenti date:");
	}
	
	public void dateRinnovo(String dataRinnovo)
	{
		System.out.println("- "+ dataRinnovo);
	}
	
	public void nessunFruitoreHaRinnovato()
	{
		System.out.println("Nessun fruitore ha ancora rinnovato la sua iscrizione");
	}
	
	public void prestitiProrogati()
	{
		System.out.println("Prestiti che sono stati rinnovati: \n");
	}
	
	public void noPrestitiRinnovati()
	{
		System.out.println("Nessun prestito è ancora stato rinnovato");
	}
	
	public void prestitiTerminati()
	{
		System.out.println("Prestiti che sono terminati: \n");
	}
	
	public void noPrestitiTerminati()
	{
		System.out.println("Nessun prestito è ancora terminato");
	}
	
	public void prestitiTerminatiInAnticipo()
	{
		System.out.println("Prestiti terminati prima della data di scandenza: ");
	}
	
	public void noPrestitiTerminatiInAnticipo()
	{
		System.out.println("Nessun prestito è terminato prima della sua data di scandenza");
	}

	public void fruitoreDecaduto(Fruitore fruitore) 
	{
		System.out.println("- " + fruitore.getNome() + " " + fruitore.getCognome() + " (il " + 
				GestioneDate.visualizzaData(fruitore.getDataScadenza()) + ")");
	}

	public void prestitoProrogato(Prestito prestito) 
	{
		System.out.println("- "+ prestito.getRisorsa() + " (il " + prestito.getDataPerRichiestaProroga());

	}

	public void prestitoTerminato(Prestito prestito) 
	{
		String risorsa = prestito.getRisorsa().getClass().getSimpleName();
		String titoloRisorsa = prestito.getRisorsa().getTitolo();
		String fruitore = prestito.getFruitore().getUser();
		String dataTermine = GestioneDate.visualizzaData(prestito.getDataRitorno());
		
		System.out.println(risorsa + ": "+ titoloRisorsa + ", Fruitore: " + fruitore + " (il " + dataTermine + ")");
	}

	public void prestitoTerminatoInAnticipo(Prestito prestito) 
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