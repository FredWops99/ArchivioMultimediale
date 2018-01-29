package parte4;

import java.io.Serializable;
import java.util.GregorianCalendar;

import myLib.BelleStringhe;
import myLib.GestioneDate;

/**
 * Classe rappresentate un fruitore dell'archivio multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Fruitore implements Serializable 
{	
	private static final long serialVersionUID = 1L;
	private String nome; 
	private String cognome;
	private GregorianCalendar dataNascita; 
	private GregorianCalendar dataIscrizione;
	private GregorianCalendar dataScadenza;
	private GregorianCalendar dataInizioRinnovo; //la data dalla quale si può rinnovare l'iscrizione
	private String username;
	private String password;
	
	/**
	 * Costruttore della classe Fruitore
	 * @param nome il nome del fruitore
	 * @param cognome il cognome del fruitore
	 * @param dataNascita la data di nascita del fruitore
	 * @param dataIscrizione la data di iscrizione del fruitore
	 * @param user username del fruitore
	 * @param password password del fruitore
	 */
	public Fruitore(String nome, String cognome, GregorianCalendar dataNascita, GregorianCalendar dataIscrizione, String user, String password) 
	{
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataIscrizione = dataIscrizione;
		this.username = user;
		this.password = password;
//		calcolo scadenza e inizioRinnovo in base alla data di iscrizione
		this.dataScadenza = calcolaScadenza();
		this.dataInizioRinnovo = CalcolaInizioRinnovo();
	}
	
	/**
	 * calcola la data in cui scade l'iscrizione, cioè 5 anni dopo essa
	 * @return	la data di scadenza calcolata (5 anni dopo l'iscrizione)
	 */
	private GregorianCalendar calcolaScadenza() 
	{
//		il metodo add tiene conto degli anni bisestili e della lunghezza dei mesi: se mi iscrivo il 29 febbraio e tra 5 anni non c'è il 29 febbraio, ritorna il 28.
//		metto scadenza uguale a dataIscrizione e poi aggiungo 5 anni, non faccio scadenza = dataIscrizione sennò si modifica anche dataIscrizione
		GregorianCalendar scadenza = new GregorianCalendar(dataIscrizione.get(GregorianCalendar.YEAR), dataIscrizione.get(GregorianCalendar.MONTH), dataIscrizione.get(GregorianCalendar.DAY_OF_MONTH));
		scadenza.add(GregorianCalendar.YEAR, 5);
		return scadenza;
	}
	
	/**
	 * calcola la data dalla quale l'utente puà richiedere il rinnovo dell'iscrizione (10 giorni prima della scadenza)
	 * il metodo add(field, amount) tiene conto della lunghezza dei mesi e degli anni bisestili
	 * @return la data dalla quale si può rinnovare l'iscrizione
	 */
	private GregorianCalendar CalcolaInizioRinnovo()
	{
//		metto scadenza uguale a dataIscrizione e poi tolgo i 10 giorni, non faccio scadenza = dataIscrizione sennò si modifica anche data iscrizione
		GregorianCalendar scadenza = new GregorianCalendar(dataScadenza.get(GregorianCalendar.YEAR), dataScadenza.get(GregorianCalendar.MONTH), dataScadenza.get(GregorianCalendar.DAY_OF_MONTH));
		scadenza.add(GregorianCalendar.DAY_OF_MONTH, -10);
		return scadenza;
	}
	
	/**
	 * controlla che la data odierna sia successiva alla data dalla quale si può rinnovare l'iscrizione
	 * non controllo che sia precedente alla data di scadenza perchè a inizio programma le iscrizioni scadute vengono rimosse
	 * @return true se il fruitore può rinnovare l'iscrizione
	 */
	private boolean fruitoreRinnovabile()
	{
//		compareTo ritorna 1 solo quando DATACORRENTE è successiva a dataInizioRinnovo
		if(GestioneDate.DATA_CORRENTE.compareTo(dataInizioRinnovo) == 1)
		{
			return true;
		}
		else return false;
	}
	
	/**
	 * Rinnova l'iscrizione di un Fruitore (se possibile)
	 */
	public void rinnovo()
	{  
		if(fruitoreRinnovabile())
		{
//			potrebbe servire mantenere la vera data di iscrizione?
			setDataIscrizione(GestioneDate.DATA_CORRENTE);
			System.out.println("la tua iscrizione è stata rinnovata");
		}
		else
		{
			System.out.println("Al momento la tua iscrizione non può essere rinnovata:");
			System.out.println("Potrai effettuare il rinnovo tra il " + GestioneDate.visualizzaData(dataInizioRinnovo) + " e il " + GestioneDate.visualizzaData(dataScadenza));
		}
	}
	
	/**
	 * stampa tutti i dati di un fruitore
	 */
	public void stampaDati() 
	{
		System.out.println(BelleStringhe.CORNICE);
		System.out.println("Nome----------------------: " + nome);
		System.out.println("Cognome-------------------: " + cognome);
		System.out.println("Username------------------: " + username);
		System.out.println("Data di nascita-----------: " + GestioneDate.visualizzaData(dataNascita));
		System.out.println("Data di iscrizione--------: " + GestioneDate.visualizzaData(dataIscrizione));
		System.out.println("Data scadenza iscrizione--: " + GestioneDate.visualizzaData(dataScadenza));
		System.out.println("Rinnovo iscrizione dal----: " + GestioneDate.visualizzaData(dataInizioRinnovo));
	}

	// -- GETTER -- //
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
	public String getUser() 
	{
		return username;
	}
	public String getPassword() 
	{
		return password;
	}
	public GregorianCalendar getDataScadenza() 
	{
		return dataScadenza;
	}
	
	// -- SETTER -- //
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
	
	/**
	 * quando viene modificata la data iscrizione (rinnovi) vengono ricalcolate e modificate anche le date di scadenza e di inzio rinnovo
	 * @param dataIscrizione la nuova data di iscrizione
	 */
	public void setDataIscrizione(GregorianCalendar dataIscrizione) 
	{
		this.dataIscrizione = dataIscrizione;
		this.dataScadenza = calcolaScadenza();
		this.dataInizioRinnovo = CalcolaInizioRinnovo();
	}
	public void setDataScadenza(GregorianCalendar dataScadenza) 
	{
		this.dataScadenza = dataScadenza;
	}
	public void setUser(String user) 
	{
		this.username = user;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
}
