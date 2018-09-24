package handler.risorse.films;

import java.util.Vector;

import controller.FilmsController;
import model.Film;
import view.FilmsView;
import view.MessaggiSistemaView;

public class FiltroFilmHandler 
{	
	public static Vector<Film> filtraFilm(int scelta, boolean daPrenotare, FilmsController filmController) 
	{
		Vector<Film> filmsFiltrati = null;
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
				titoloParziale = FilmsView.chiediTitolo(Film.class);
				filmsFiltrati = filmController.filtraFilmPerTitolo(titoloParziale);
				break;
			}
			case 2:	//FILTRA PER ANNO PUBBLICAZIONE
			{
				annoUscita = FilmsView.chiediAnnoUscita();
				filmsFiltrati = filmController.filtraFilmPerUscita(annoUscita);
				break;
			}
			case 3: //FILTRA PER AUTORE
			{
				nomeRegista = FilmsView.chiediRegista();
				filmsFiltrati = filmController.filtraFilmPerRegista(nomeRegista);
				break;
			}
		}
		if(!daPrenotare)
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
				filmController.stampaDatiFilm(filmsFiltrati.get(i), false);
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