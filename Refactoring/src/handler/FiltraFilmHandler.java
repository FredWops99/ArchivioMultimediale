package handler;

import java.util.Vector;
import controllerMVC.FilmsController;
import interfaces.Risorsa;
import myLib.MyMenu;
import view.FilmsView;
import view.MessaggiSistemaView;

public class FiltraFilmHandler 
{	
	private FilmsController filmsController;
	
	public FiltraFilmHandler(FilmsController filmsController)
	{
		this.filmsController = filmsController;
	}
	
	/**
	 * se i film vengono filtrati per essere prenotati viene restituita la lista, se invece è solo per la ricerca vengono visualizzati e basta
	 * @param daPrenotare 
	 * @return la lista dei film filtrati (if daPrenotare)
	 */
	public Vector<Risorsa> menuFiltraFilm(boolean daPrenotare) 
	{
		final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
		final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di uscita", "Filtra per regista"};
		
		MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO, true); 
		int scelta = menuFiltro.scegliBase();
		
		Vector<Risorsa> filmFiltrati = filtraFilm(scelta, daPrenotare);
		
		if(daPrenotare)
		{
			return filmFiltrati;
		}
		else// !daPrenotare
		{
			return null;
		}
	}
	
	public Vector<Risorsa> filtraFilm(int scelta, boolean daPrenotare) 
	{
		Vector<Risorsa> filmsFiltrati = null;
		String titoloParziale = null;
		int annoUscita = 0;
		String nomeRegista = null;
		
		switch(scelta) 
		{
			case 0:	//INDIETRO
			{
				return null;
			}
			case 1: //FILTRA PER TITOLO
			{
				titoloParziale = FilmsView.chiediTitolo();
				filmsFiltrati = filmsController.filtraFilmPerTitolo(titoloParziale);
				break;
			}
			case 2:	//FILTRA PER ANNO PUBBLICAZIONE
			{
				annoUscita = FilmsView.chiediAnnoUscita();
				filmsFiltrati = filmsController.filtraFilmPerUscita(annoUscita);
				break;
			}
			case 3: //FILTRA PER AUTORE
			{
				nomeRegista = FilmsView.chiediRegista();
				filmsFiltrati = filmsController.filtraFilmPerRegista(nomeRegista);
				break;
			}
		}
		if(!daPrenotare)
		{
			if(scelta == 1 && filmsFiltrati.isEmpty()) 
			{
				FilmsView.risorsaNonPresente(titoloParziale);
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
				filmsController.stampaDatiFilm(filmsFiltrati.get(i), false);
			}	
		}
		
		else if (daPrenotare)
		{
			return filmsFiltrati;
		}
//		se non sono da prenotare (quindi solo da visualizzare) non deve ritornare nulla
		return null;
	}
}