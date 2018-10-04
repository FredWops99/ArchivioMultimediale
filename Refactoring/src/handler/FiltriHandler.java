package handler;

import java.util.Vector;

import controllerMVC.FilmsController;
import controllerMVC.LibriController;
import model.Film;
import model.Libri;
import model.Libro;
import model.Risorsa;
import view.FilmsView;
import view.LibriView;
import view.MessaggiSistemaView;

public class FiltriHandler 
{	
	public static Vector<Risorsa> filtraFilm(int scelta, boolean daPrenotare, FilmsController filmController) 
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
	
	public static Vector<Risorsa> filtraLibri(int scelta, boolean daPrenotare, LibriController libriController) 
	{
		Vector<Risorsa> libriFiltrati = null;
		String titoloParziale = null;
		int annoPubblicazione = 0;
		String nomeAutore = null;
		
		switch(scelta) 
		{
			case 0:	//INDIETRO
			{
				return null;
			}
			case 1: //FILTRA PER TITOLO
			{
				titoloParziale = LibriView.chiediTitolo(Libro.class);
				libriFiltrati = libriController.filtraLibriPerTitolo(titoloParziale);
				break;
			}
			case 2:	//FILTRA PER ANNO DI PUBBLICAZIONE
			{
				annoPubblicazione = LibriView.chiediAnnoPubblicazione();
				libriFiltrati = libriController.filtraLibriPerAnnoPubblicazione(annoPubblicazione);
				break;
			}
			case 3: //FILTRA PER AUTORE
			{
				nomeAutore = LibriView.chiediAutore();
				libriFiltrati = libriController.filtraLibriPerAutori(nomeAutore);
				break;
			}
		}
		if (daPrenotare == false)
		{
			if(scelta == 1 && libriFiltrati.isEmpty()) 
			{
				LibriView.risorsaNonPresente(Libri.class, titoloParziale);
				return null;
			}
			if(scelta == 2 && libriFiltrati.isEmpty())
			{
				LibriView.annoNonPresente(annoPubblicazione);
				return null;
			}
			if(scelta == 3 && libriFiltrati.isEmpty())
			{
				LibriView.autoreNonPresente(nomeAutore);
				return null;
			}
			
			for (int i=0; i <libriFiltrati.size(); i++) 
			{
				MessaggiSistemaView.cornice(true,false);
				libriController.stampaDatiLibro(libriFiltrati.get(i), false);
			}
		}
		
		else if (daPrenotare == true)
		{
			return libriFiltrati;
		}
		//se non sono da prenotare (quindi solo da visualizzare) non deve ritornare nulla
		return null;
	}
}