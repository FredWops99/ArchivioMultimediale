package parte4;

import java.io.Serializable;
import java.util.GregorianCalendar;

import myLib.GestioneDate;

/**
 * classe che rappresenta i prestiti di una risorsa multimediale ad un fruitore
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Prestito implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Risorsa risorsa;
	private Fruitore fruitore;
	private GregorianCalendar dataInizio;
	private GregorianCalendar dataScadenza;
	/**
	 * la data dalla quale è possibile richiedere la proroga del prestito
	 */
	private GregorianCalendar dataPerRichiestaProroga;
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
		this.dataScadenza = calcolaScadenza(dataInizio);
		dataPerRichiestaProroga = calcolaDataPerRichiestaProroga(this.dataScadenza);
		prorogato = false;		
	}
	
	public void visualizzaPrestito()
	{
		System.out.println("Categoria-------------: " + risorsa.getClass().getSimpleName());
		System.out.println("Titolo----------------: " + risorsa.getTitolo());
		System.out.println("Fruitore--------------: " + fruitore.getUser());
		System.out.println("Data prestito---------: " + GestioneDate.visualizzaData(dataInizio));
		System.out.println("Data scadenza---------: " + GestioneDate.visualizzaData(dataScadenza));
		if(!prorogato)
		{
			System.out.println("Rinnovabile dal-------: " + GestioneDate.visualizzaData(dataPerRichiestaProroga));
		}
		else
		{
			System.out.println("Prestito non rinnovabile");
		}
	}
	
	/**
	 * il prestito è rinnovabile solo se non è già stato prorogato una volta
	 * @return true se il prestito è rinnovabile
	 */
	public boolean isRinnovabile()
	{
		return !prorogato;
	}
	
	/**
	 * quando un prestito viene prorogato, viene etichettato come "prorogato" e vengono aggiornate le date di scadenza e di richiestaProroga
	 */
	public void prorogaPrestito()
	{
		setProrogato(true);
		setDataScadenza(calcolaScadenza(GestioneDate.DATA_CORRENTE));
		setDataPerRichiestaProroga(calcolaDataPerRichiestaProroga(dataScadenza));
	}
	
	/**
	 * calcola la data nella quale deve avvenire il reso della risorsa: a seconda della risorsa essa sarà X giorni dopo l'inizio/rinnovo del prestito 
	 * @param data la data di inizio o di rinnovo del prestito
	 * @return la data di scadenza del prestito
	 */
	public GregorianCalendar calcolaScadenza(GregorianCalendar data)
	{
//		non faccio "dataScadenza = dataInizio" sennò dopo si modifica anche dataInizio
		dataScadenza = new GregorianCalendar(data.get(GregorianCalendar.YEAR), data.get(GregorianCalendar.MONTH), data.get(GregorianCalendar.DAY_OF_MONTH));
//		aggiungo i giorni della durata del prestito della risorsa
		dataScadenza.add(GregorianCalendar.DAY_OF_MONTH, risorsa.getGiorniDurataPrestito());
		return dataScadenza;
	}
	
	private GregorianCalendar calcolaDataPerRichiestaProroga(GregorianCalendar scadenza) 
	{
		GregorianCalendar data = new GregorianCalendar(scadenza.get(GregorianCalendar.YEAR), scadenza.get(GregorianCalendar.MONTH), scadenza.get(GregorianCalendar.DAY_OF_MONTH));
		data.add(GregorianCalendar.DAY_OF_MONTH, risorsa.getGiorniPrimaPerProroga());
		return data;
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
	public GregorianCalendar getDataPerRichiestaProroga() 
	{
		return dataPerRichiestaProroga;
	}
	public void setDataPerRichiestaProroga(GregorianCalendar dataRichiestaProroga)
	{
		this.dataPerRichiestaProroga = dataRichiestaProroga;
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
