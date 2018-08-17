package model;

import java.io.Serializable;

import menus.risorse.*;
import view.MessaggiSistemaView;

/**
 * Classe che raggruppa le varie tipologie di risorsa (Libri, Films,...) e opera su di esse.
 * @author Prandini Stefano
 * @author Landi Federico
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
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void aggiungiRisorsa(String[] CATEGORIE)
	{
		MenuAggiungiRisorsa.show(CATEGORIE, libri, films);
	}
	
	/**
	 * permette la rimozione di un libro o di un film dall'archivio
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 * @return l'id della risorsa rimossa
	 */
	public String rimuoviRisorsa(String[] CATEGORIE) 
	{
		MessaggiSistemaView.avvisoRimozioneRisorsa();
		
//		se utente annulla procedura removelibro/removefilm ritornato -1
		String idRimosso = MenuRimuoviRisorsa.showAndReturnID(CATEGORIE, libri, films);
		return idRimosso;
	}
	
	/**
	 * mostra tute le risorse che sono in archivio e che possono essere prese in prestito
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void visualizzaRisorsePrestabili(String[] CATEGORIE) 
	{
		MenuRisorsePrestabili.show(CATEGORIE, libri, films);
	}
	
	/**
	 * permette la ricerca in archivio di un libro o di un film
	 * (precondizione: CATEGORIE != null)
	 * @param CATEGORIE le categorie di risorsa tra cui scegliere
	 */
	public void cercaRisorsa(String[] CATEGORIE) 
	{
		MenuCercaRisorsa.show(CATEGORIE, libri, films);
	}
}
