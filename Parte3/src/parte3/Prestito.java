package parte3;

import java.io.Serializable;
import java.util.GregorianCalendar;

import myLib.GestioneDate;

public class Prestito implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Risorsa risorsa;
	private Fruitore fruitore;
	private GregorianCalendar dataInizio;
	private GregorianCalendar dataScadenza;
	private GregorianCalendar dataRichiestaProroga;
	private boolean prorogato;
	
	/**
	 * Creo l'oggetto Prestito che associa il fruitore alla risorsa in prestito: l'inizio del prestito è la data odierna, scadenza prestito e la data dalla quale
	 * si può richiedere una proroga vengono definite in base ai paramentri della risorsa in questione
	 * Precondizione: il fruitore può richiedere il prestito di questa categoria di risorsa
	 * 
	 * @param fruitore il fruitore che richiede il prestito
	 * @param risorsa la risorsa che il fruitore chiede in prestito
	 */
	public Prestito(Fruitore fruitore, Risorsa risorsa)
	{
		this.risorsa = risorsa;
		this.fruitore = fruitore;
		this.dataInizio = GestioneDate.DATA_CORRENTE;
//		non faccio "dataScadenza = dataInizio" sennò dopo si modifica anche dataInizio
		dataScadenza = new GregorianCalendar(dataInizio.get(GregorianCalendar.YEAR), dataInizio.get(GregorianCalendar.MONTH), dataInizio.get(GregorianCalendar.DAY_OF_MONTH));
//		aggiungo i giorni della durata del prestito della risorsa
		dataScadenza.add(GregorianCalendar.DAY_OF_MONTH, risorsa.getGiorniDurataPrestito());
//		non faccio "dataProroga = dataScadenza" sennò dopo si modifica anche dataScadenza
		dataRichiestaProroga = new GregorianCalendar(dataScadenza.get(GregorianCalendar.YEAR), dataScadenza.get(GregorianCalendar.MONTH), dataScadenza.get(GregorianCalendar.DAY_OF_MONTH));
		dataRichiestaProroga.add(GregorianCalendar.DAY_OF_MONTH, risorsa.getGiorniPrimaPerProroga());
		prorogato = false;		
	}
	
	public void visualizzaPrestito()
	{
		System.out.println("Categoria-------------: " + risorsa.getClass().getSimpleName());
		System.out.println("Titolo----------------: " + risorsa.getNome());
		System.out.println("Fruitore--------------: " + fruitore.getUser());
		System.out.println("Data prestito---------: " + GestioneDate.visualizzaData(dataInizio));
		System.out.println("Data scadenza---------: " + GestioneDate.visualizzaData(dataScadenza));
		if(!prorogato)
		{
			System.out.println("Rinnovabile dal-------: " + GestioneDate.visualizzaData(dataRichiestaProroga));
		}
		else
		{
			System.out.println("Prestito non rinnovabile");
		}
	}
	
	public Risorsa getRisorsa() 
	{
		return risorsa;
	}
	public void setRisorsa(Risorsa risorsa) 
	{
		this.risorsa = risorsa;
	}
	public Fruitore getFruitore() 
	{
		return fruitore;
	}
	public void setFruitore(Fruitore fruitore) 
	{
		this.fruitore = fruitore;
	}
	public GregorianCalendar getDataInizio()
	{
		return dataInizio;
	}
	public void setDataInizio(GregorianCalendar dataInizio) 
	{
		this.dataInizio = dataInizio;
	}
	public GregorianCalendar getDataScadenza() 
	{
		return dataScadenza;
	}
	public void setDataScadenza(GregorianCalendar dataScadenza) 
	{
		this.dataScadenza = dataScadenza;
	}
	public GregorianCalendar getDataRichiestaProroga() 
	{
		return dataRichiestaProroga;
	}
	public void setDataRichiestaProroga(GregorianCalendar dataRichiestaProroga)
	{
		this.dataRichiestaProroga = dataRichiestaProroga;
	}
	public boolean isProrogato() 
	{
		return prorogato;
	}
	public void setProrogato(boolean prorogato) 
	{
		this.prorogato = prorogato;
	}
}
