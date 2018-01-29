package parte5;

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
	/**
	 * da settare quando il prestito termina: se scade sarà uguale alla dataScadenza, se il fruitore termina prima il prestito sarà quella data.
	 * viene usata solo nello storico, in quanto nel main i prestiti scaduti vengono eliminati
	 */
	private GregorianCalendar dataRitorno;
	private boolean prorogato;
	private boolean terminato;
	
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
//		non faccio "dataProroga = dataScadenza" sennò dopo si modifica anche dataScadenza
		dataRichiestaProroga = new GregorianCalendar(dataScadenza.get(GregorianCalendar.YEAR), dataScadenza.get(GregorianCalendar.MONTH), dataScadenza.get(GregorianCalendar.DAY_OF_MONTH));
		dataRichiestaProroga.add(GregorianCalendar.DAY_OF_MONTH, risorsa.getGiorniPrimaPerProroga());
		prorogato = false;	
		terminato = false;
	}
	
	/**
	 * visualizza un prestito. In particolare sono presenti i seguenti campi relativi al prestito
	 * - Categoria della risorsa
	 * - Titolo della risorsa
	 * - Il fruitore che ha in prestito la risorsa
	 * - La data in cui è avvenuto il prestito
	 * - La data di scadenza del prestito
	 */
	public void visualizzaPrestito()
	{
		System.out.println("Categoria-------------: " + risorsa.getClass().getSimpleName());
		System.out.println("Titolo----------------: " + risorsa.getTitolo());
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
	
	
	/**
	 * calcola la data nella quale deve avvenire il reso della risorsa
	 * @param data
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
	
	
	/**
	 * il prestito è rinnovabile solo se non è già stato prorogato una volta
	 * @return true se il prestito è rinnovabile
	 */
	public boolean isRinnovabile()
	{
		return !prorogato;
	}
	
	
	/**
	 * permetta la proroga del prestito
	 */
	public void prorogaPrestito()
	{
		setProrogato(true);
		setDataRichiestaProroga(GestioneDate.DATA_CORRENTE);
		setDataScadenza(calcolaScadenza(GestioneDate.DATA_CORRENTE));
	}
	
	/**
	 * permette di terminare un prestito settando la variabile "terminato" = true
	 */
	public void terminaPrestito()
	{
		setDataRitorno(GestioneDate.DATA_CORRENTE);
		terminato=true;
	}
	
	
	//GETTER + SETTER//
	
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
	public void setProrogato(boolean prorogato) 
	{
		this.prorogato = prorogato;
	}
	public GregorianCalendar getDataRitorno() 
	{
		return dataRitorno;
	}
	public void setDataRitorno(GregorianCalendar dataRitorno) 
	{
		this.dataRitorno = dataRitorno;
	}
	public boolean isTerminato() 
	{
		return terminato;
	}
	public void setTerminato(boolean terminato) 
	{
		this.terminato = terminato;
	}
	
	//variabile che mi indica se il prestito è stato soggetto a proroga
	public boolean isProrogato() 
	{
		return prorogato;
	}
}
