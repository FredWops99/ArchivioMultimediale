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
				filmsFiltrati = filtraFilmPerTitolo(titoloParziale);
				break;
			}
			case 2:	//FILTRA PER ANNO PUBBLICAZIONE
			{
				annoUscita = FilmsView.chiediAnnoUscita();
				filmsFiltrati = filtraFilmPerUscita(annoUscita);
				break;
			}
			case 3: //FILTRA PER AUTORE
			{
				nomeRegista = FilmsView.chiediRegista();
				filmsFiltrati = filtraFilmPerRegista(nomeRegista);
				break;
			}
		}
		if(!daPrenotare)
		{
			if(scelta == 1 && filmsFiltrati.isEmpty()) 
			{
				FilmsView.risorsaNonPresente("films",titoloParziale);
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
				FilmsView.stampaDati(filmsFiltrati.get(i), false);
			}	
		}
		
		else if (daPrenotare)
		{
			return filmsFiltrati;
		}
//		se non sono da prenotare (quindi solo da visualizzare) non deve ritornare nulla
		return null;
	}
	
	public Vector<Risorsa> filtraFilmPerTitolo(String titoloParziale)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = filmsController.getModel().filtraRisorsePerTitolo("Films", titoloParziale);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getCategoria().equals("Films"))
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
	
	public Vector<Risorsa> filtraFilmPerUscita(int annoUscita)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = filmsController.getModel().filtraRisorsePerUscita("Films", annoUscita);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getCategoria().equals("Films"))
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
	
	public Vector<Risorsa> filtraFilmPerRegista(String regista)
	{
		Vector<Risorsa> result = new Vector<>();
		Vector<Risorsa> filtrati = filmsController.getModel().filtraFilmPerRegista("Films", regista);
		for(Risorsa risorsa : filtrati)
		{
			if(risorsa.getCategoria().equals("Films"))
			{
				result.addElement(risorsa);
			}
		}
		return result;
	}
}