package parte5;

import java.io.Serializable;
import myLib.MyMenu;

/**
 * Classe che raggruppa le varie tipologie di risorsa (Libri, Films,...) e opera su di esse.
 * @author Prandini Stefano
 * @author Landi Federico
 *
 */
public class Archivio implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Libri libri;
	private Films films;
	

	/**
	 * costruttore: inizializza gli oggetti Libri e Films (invocando i loro costruttori)
	 */
	public Archivio()
	{
		setLibri(new Libri());
		setFilms(new Films());
	}
	
	//GETTER//
	public Libri getLibri() 
	{
		return libri;
	}
	public Films getFilms()
	{
		return films;
	}
	
	//SETTER//
	public void setLibri(Libri libri)
	{
		this.libri = libri;
	}
	public void setFilms(Films films)
	{
		this.films = films;
	}

	/**
	 * permette di aggiungere un libro od un film all'archivio
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void aggiungiRisorsa(String[] CATEGORIE)
	{
		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase()-1];
			if(categoria == CATEGORIE[0])//LIBRO
			{
				getLibri().addLibro();
			}
			if(categoria == CATEGORIE[1])//FILM
			{
				getFilms().addFilm();
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 per uscire viene lanciata eccezione: CATEGORIE[-1].
//			non va fatto nulla, basta intercettarla
		}
	}
	
	/**
	 * permette la rimozione di un libro o di un film dall'archivio
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 * @return l'id della risorsa rimossa
	 */
	public String rimuoviRisorsa(String[] CATEGORIE) 
	{
//		se utente annulla procedura removelibro/removefilm ritornato -1
		String idRisorsa = "-1";
		System.out.println("ATTENZIONE! Se la risorsa che si desidera rimuovere ha copie attualmente in prestito, queste verranno sottratte ai fruitori");
		
		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];
			if(categoria == CATEGORIE[0])//LIBRI
			{
				idRisorsa = getLibri().removeLibro();
			}
			if(categoria == CATEGORIE[1])//FILMS
			{
				idRisorsa = getFilms().removeFilm();
			}
			return idRisorsa;
		}
//		se non sono presenti risorse ritorna -1
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "-1";
		}
	}
	
	/**
	 * mostra tute le risorse che sono in archivio e che possono essere prese in prestito
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void visualizzaRisorsePrestabili(String[] CATEGORIE) 
	{
		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
			if(categoria == CATEGORIE[0])//LIBRI
			{
				getLibri().stampaLibri();
			}
			if(categoria == CATEGORIE[1])//FILMS
			{
				getFilms().stampaFilms();
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
	
	/**
	 * permette la ricerca in archivio di un libro o di un film
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void cercaRisorsa(String[] CATEGORIE) 
	{
		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato

			if(categoria == CATEGORIE[0])//"Libri"
			{
				getLibri().cercaLibro();
			}
			else if(categoria == CATEGORIE[1])//"Films"
			{
				getFilms().cercaFilm();
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
