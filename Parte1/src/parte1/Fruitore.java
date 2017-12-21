package parte1;

import java.io.Serializable;
import java.util.GregorianCalendar;

import myLib.GestioneDate;

public class Fruitore implements Serializable 
{	
	private static final long serialVersionUID = 1L;
	private String nome; 
	private String cognome;
	private GregorianCalendar dataNascita; 
	private GregorianCalendar dataIscrizione;
	private GregorianCalendar dataScadenza;
	
	public Fruitore(String nome, String cognome, GregorianCalendar dataNascita, GregorianCalendar dataIscrizione) 
	{
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataIscrizione = dataIscrizione;
//		gli passo dataIscrizione perchè sennò il metodo non potrebbe accedere a dataIscrizione, non ancora salvato
		this.dataScadenza = calcolaScadenza(dataIscrizione);
	}
	/**
	 * se mi iscrivo il 29 febbraio, tra 5 anni non ci sarà il 29 febbraio: la scadenza sarà il 1 marzo.
	 * se mi iscrivo un giorno diverso dal 29 febbraio, la scadenza sarà il giorno stesso ma 5 anni dopo
	 * @return	la data di scadenza calcolata
	 */
	private GregorianCalendar calcolaScadenza(GregorianCalendar dataIscrizione) 
	{
		GregorianCalendar scadenza = new GregorianCalendar();
		if(dataIscrizione.get(GregorianCalendar.DAY_OF_MONTH)==29 && dataIscrizione.get(GregorianCalendar.MONTH)==1)
		{
			scadenza = new GregorianCalendar(dataIscrizione.get(GregorianCalendar.YEAR)+5, 2, 1);
		}
		else
		{
			scadenza = new GregorianCalendar(dataIscrizione.get(GregorianCalendar.YEAR)+5,dataIscrizione.get(GregorianCalendar.MONTH), dataIscrizione.get(GregorianCalendar.DAY_OF_MONTH));
		}
		return scadenza;
	}

	public String getNome() 
	{
		return nome;
	}
	public String getCognome() 
	{
		return cognome;
	}
	public GregorianCalendar getDataNascita() 
	{
		return dataNascita;
	}
	public GregorianCalendar getDataIscrizione() 
	{
		return dataIscrizione;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	public void setDataNascita(GregorianCalendar dataNascita) 
	{
		this.dataNascita = dataNascita;
	}
	public void setDataIscrizione(GregorianCalendar dataIscrizione) 
	{
		this.dataIscrizione = dataIscrizione;
	}

	public GregorianCalendar getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(GregorianCalendar dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public void stampaDati() 
	{
		System.out.println("----------------------------------------\n");
		System.out.println("Nome----------------------: " + nome);
		System.out.println("Cognome-------------------: " + cognome);
		System.out.println("Data di nascita-----------: " + GestioneDate.visualizzaData(dataNascita));
		System.out.println("Data di iscrizione--------: " + GestioneDate.visualizzaData(dataIscrizione));
		System.out.println("Data scadenza iscrizione--: " + GestioneDate.visualizzaData(dataScadenza) + "\n");
	}
}
