package menus.risorse.films;

import java.util.Vector;

import controller.FilmController;
import model.Film;
import myLib.MyMenu;
import view.FilmsView;
import view.MessaggiSistemaView;

public class MenuFiltroFilm 
{
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di uscita", "Filtra per regista"};
	
	public static Vector<Film> show(boolean daPrenotare, FilmController filmController) 
	{
		Vector<Film> filmsFiltrati = null;
		String titoloParziale = null;
		int annoUscita = 0;
		String nomeRegista = null;
		
		MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO, true); 
		int scelta = menuFiltro.scegliBase();
		switch(scelta) 
		{
			case 0:	//INDIETRO
			{
				return null;
			}
			case 1: //FILTRA PER TITOLO
			{
				titoloParziale = FilmsView.chiediTitolo(Film.class);
				filmsFiltrati = filtraFilmPerTitolo(titoloParziale, filmController);
				break;
			}
			
			case 2:	//FILTRA PER ANNO PUBBLICAZIONE
			{
				annoUscita = FilmsView.chiediAnnoUscita();
				filmsFiltrati = filtraFilmPerUscita(annoUscita, filmController);
				break;
			}
			
			case 3: //FILTRA PER AUTORE
			{
				nomeRegista = FilmsView.chiediRegista();
				filmsFiltrati = filtraFilmPerRegista(nomeRegista, filmController);
				break;
			}
		}
		if(daPrenotare == false)
		{
			if(scelta == 1 && filmsFiltrati.isEmpty()) 
			{
				FilmsView.risorsaNonPresente(Film.class, titoloParziale);
				return null;
			}
			if(scelta == 2 && filmsFiltrati.isEmpty())
			{
				FilmsView.annoNonPresente(annoUscita);
				return null;
			}
			if(scelta == 3 && filmsFiltrati.isEmpty())
			{
				FilmsView.registaNonPresente(nomeRegista);
				return null;
			}
			
			for (int i=0; i <filmsFiltrati.size(); i++) 
			{
				MessaggiSistemaView.cornice(true, false);
				filmController.stampaDati(filmsFiltrati.get(i), false);
//				filmsFiltrati.get(i).stampaDati(false);
			}	
		}
		
		else if (daPrenotare == true)
		{
			return filmsFiltrati;
		}
//		se non sono da prenotare (quindi solo da visualizzare) non deve ritornare nulla
		return null;
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * METODI PER LA RICERCA DI FILM IN BASE A DETERMINATI PARAMETRI										 *
	 * 																										 *
	 *  filtraFilmPerTitolo  -> fltra in base al titolo parziale passato dall'utente						 *
	 *  filtraFilmPerUscita  -> filtra in base all'anno di uscita immesso dall'utente						 *
	 *  filtraFilmPerRegista -> filtra in base al nome del regista passato dall'utente						 *
	 *  																									 *
	 *  ogni metodo restituisce un vector di film contenente i film che corrispondono ai parametri			 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	 
	/**
	 * filtra tutti i film in base al titolo
	 * (precondizione: titoloParziale != null)
	 * @param titoloParziale la parte di titolo usata come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public static Vector<Film> filtraFilmPerTitolo(String titoloParziale, FilmController filmController)
	{
		Vector<Film> films = filmController.getFilms().getFilms();
		
		Vector<Film> filmTrovati = new Vector<>(); 
		
		for(Film film : films)
		{
			if(film.isPrestabile() && film.getTitolo().toLowerCase().contains(titoloParziale.toLowerCase()))
			{
				filmTrovati.add(film);
			}
		}
		return filmTrovati;
	}
	
	/**
	 * filtra tutti i film in base all'anno di pubblicazione
	 * (precondizione: annoUscita != null)
	 * @param annoUscita l'anno da usare come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public static Vector<Film> filtraFilmPerUscita(int annoUscita, FilmController filmController)
	{
		Vector<Film> films = filmController.getFilms().getFilms();

		Vector<Film> filmTrovati = new Vector<>(); 
		
		for(Film film : films)
		{
			if(film.isPrestabile() && film.getAnnoDiUscita() == annoUscita)
			{
				filmTrovati.add(film);
			}
		}
		return filmTrovati;
	}
	
	/**
	 * filtra tutti i film in base al regista
	 * (precondizione: regista != null)
	 * @param regista il nome del regista da usare come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public static Vector<Film> filtraFilmPerRegista(String regista, FilmController filmController)
	{
		Vector<Film> films = filmController.getFilms().getFilms();

		Vector<Film> filmTrovati = new Vector<>(); 
		for(Film film : films)
		{
			if(film.isPrestabile())
			{
				if(regista.toLowerCase().equals(regista.toLowerCase()))
				{
					filmTrovati.add(film);
				}
			}
		}
		return filmTrovati;
	}
}
