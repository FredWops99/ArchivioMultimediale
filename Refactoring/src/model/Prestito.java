package model;

import java.io.Serializable;
import java.util.GregorianCalendar;
import myLib.GestioneDate;
import view.PrestitiView;

/**
 * classe che rappresenta un prestito di una risorsa multimediale ad un fruitore
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
	/**
	 * da settare quando il prestito termina: se scade sarà uguale alla dataScadenza, se il fruitore termina prima il prestito sarà quella data.
	 */
	private GregorianCalendar dataRitorno;
	private boolean prorogato;
	private boolean terminato;
	
	/**
	 * Creo l'oggetto Prestito che associa il fruitore alla risorsa in prestito: l'inizio del prestito è la data odierna, scadenza prestito e la data dalla quale
	 * si può richiedere una proroga vengono definite in base ai paramentri della risorsa in questione
	 * Precondizione: il fruitore può richiedere il prestito di questa categoria di risorsa && fruitore != null && risorsa != null
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
		dataPerRichiestaProroga = calcolaDataPerRichiestaProroga(dataScadenza);
		prorogato = false;	
		terminato = false;
	}
	
	/**
	 * visualizza le informazioni relative ad un prestito. In particolare sono presenti i seguenti campi:
	 * - Categoria della risorsa
	 * - Titolo della risorsa
	 * - Il fruitore che ha in prestito la risorsa
	 * - La data in cui è avvenuto il prestito
	 * - La data di scadenza del prestito
	 */
	public void visualizzaPrestito()
	{
		PrestitiView.visualizzaPrestito(this);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Categoria-------------: " + risorsa.getClass().getSimpleName() + "\n");		
		sb.append("Titolo----------------: " + risorsa.getTitolo() + "\n");
		sb.append("Fruitore--------------: " + fruitore.getUser() + "\n");
		sb.append("Data prestito---------: " + GestioneDate.visualizzaData(dataInizio) + "\n");
		sb.append("Data scadenza---------: " + GestioneDate.visualizzaData(dataScadenza) + "\n");
		if(!prorogato)
		{
			sb.append("Rinnovabile dal-------: " + GestioneDate.visualizzaData(dataPerRichiestaProroga) + "\n");
		}
		else
		{
			sb.append("Prestito non rinnovabile\n");
		}
		return sb.toString();
	}
	
	/**
	 * calcola la data nella quale deve avvenire il reso della risorsa: a seconda della risorsa essa sarà X giorni dopo l'inizio/rinnovo del prestito 
	 * (precondizione: data != null)
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
	
	/**
	 * il prestito è rinnovabile solo se non è già stato prorogato una volta
	 * @return true se il prestito è rinnovabile
	 */
	public boolean isRinnovabile()
	{
		return !prorogato;
	}
	
	/**
	 * precondizione: il prestito è rinnovabile
	 * quando un prestito viene prorogato, viene etichettato come "prorogato" e vengono aggiornate le date di scadenza e di richiestaProroga
	 */
	public void prorogaPrestito()
	{
		setProrogato(true);
		setDataScadenza(calcolaScadenza(GestioneDate.DATA_CORRENTE));
		setDataPerRichiestaProroga(calcolaDataPerRichiestaProroga(dataScadenza));
	}
	
	/**
	 * metodo che calcola da che data sarà possibile rinnovare il prestito
	 * (precondizione: data != null)
	 * @param scadenza la data di scadenza del prestito
	 * @return la data dalla quale sarà possibile rinnovare il prestito
	 */
	private GregorianCalendar calcolaDataPerRichiestaProroga(GregorianCalendar scadenza) 
	{
		GregorianCalendar data = new GregorianCalendar(scadenza.get(GregorianCalendar.YEAR), scadenza.get(GregorianCalendar.MONTH), scadenza.get(GregorianCalendar.DAY_OF_MONTH));
		data.add(GregorianCalendar.DAY_OF_MONTH, risorsa.getGiorniPrimaPerProroga());
		return data;
	}

	/**
	 * quando un prestito viene terminato, viene etichettato come "terminato" e viene aggiornata la data di ritorno del prestito
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
	public GregorianCalendar getDataPerRichiestaProroga() 
	{
		return dataPerRichiestaProroga;
	}
	public void setDataPerRichiestaProroga(GregorianCalendar dataRichiestaProroga)
	{
		this.dataPerRichiestaProroga = dataRichiestaProroga;
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
	public boolean isProrogato() 
	{
		return prorogato;
	}
}