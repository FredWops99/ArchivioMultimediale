package controller;

import java.util.Vector;

import menus.risorse.films.MenuFiltroFilm;
import menus.risorse.films.MenuScegliFilm;
import menus.risorse.films.MenuSottoCategoriaFilm;
import model.Archivio;
import model.Film;
import model.Films;
import model.Main;
import view.FilmsView;
import view.MessaggiSistemaView;

public class FilmController 
{
	private Archivio model;
	private int lastId;	
	
	public FilmController(Archivio archivio) 
	{
		this.model = archivio;
		this.lastId = model.getFilms().getLastId();
	}

	public void addFilm()
	{
		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria FILM ("Azione","Avventura","Fantascienza"...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return;
		}

		String titolo = FilmsView.chiediTitolo(Film.class);
		int durata = FilmsView.chiediDurata();
		int annoDiUscita = FilmsView.chiediAnnoUscita();
		String lingua = FilmsView.chiediLingua();
		String regista = FilmsView.chiediRegista();
		int nLicenze = FilmsView.chiediNumeroLicenze();
		
		Film f = new Film("F"+ lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non � gi� esistente)
		boolean aggiuntaRiuscita = model.getFilms().addFilm(f);
		
		if(aggiuntaRiuscita)
		{
			FilmsView.aggiuntaRiuscita(Film.class);
			
//			controller aggiorna anche l'archivio del main, cos� da rimanere aggiornato tra i vari controller
//			poi il main salva su file quell'archivio, nella classe MenuOperatore
			Main.setArchivio(model);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
		}
		
	}
	
//	deve restituire al main l'id della risorsa da eliminare (far diventare non prestabile)
	public String removeFilm()
	{
		Vector<Film> films = model.getFilms().getFilms();
		
		String idSelezionato;
		
		String titolo = FilmsView.chiediRisorsaDaRimuovere(Film.class);
		
		Vector<Integer> posizioniRicorrenze = new Vector<>();
		
		for (int i = 0; i < films.size(); i++) 
		{
			if(films.get(i).isPrestabile() && films.get(i).getTitolo().toLowerCase().equals(titolo.toLowerCase()))
			{
//				ogni volta che in films trovo un libro con il nome inserito dall'operatore, aggiungo la sua posizione al vettore
				posizioniRicorrenze.add(i);
			}
		}
		if(posizioniRicorrenze.size()==0)
		{
			FilmsView.risorsaNonPresente(Film.class);
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'� solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = films.get((int)posizioniRicorrenze.get(0)).getId();
			films.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
			FilmsView.rimozioneAvvenuta();
		}
//		se ci sono pi� elementi nel vettore (pi� films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			FilmsView.pi�RisorseStessoTitolo(Film.class, titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				FilmsView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				stampaDati(films.elementAt((int)i), false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = FilmsView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze);
					
			if(daRimuovere > 0)
			{
				idSelezionato = films.get((int)posizioniRicorrenze.get(daRimuovere-1)).getId();
				films.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				FilmsView.rimozioneAvvenuta();
			}
			else//0: annulla
			{
				idSelezionato = "-1";
			}
		}
		Main.setArchivio(model);
		
		return idSelezionato;
	}
	
	public void stampaDati(Film elementAt, boolean perPrestito) 
	{
		FilmsView.stampaDati(elementAt, perPrestito);
	}
	
	public void stampaDati() 
	{
		Vector<Film>filmDaStampare = new Vector<>();
		for(Film film : model.getFilms().getFilms())
		{
			if(film.isPrestabile())
			{
				filmDaStampare.add(film);
			}
		}
		FilmsView.stampaDati(filmDaStampare);
	}

	private String scegliSottoCategoria()
	{
		String sottocategoria = MenuSottoCategoriaFilm.show();
		
		return sottocategoria;
	}

	public void conferma(boolean aggiuntaRiuscita) 
	{
		if(aggiuntaRiuscita)
		{
			FilmsView.aggiuntaRiuscita(Film.class);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
		}
	}
	
	public Films getFilms()
	{
		return this.getFilms();
	}

	public void cercaFilm() 
	{
			MenuFiltroFilm.show(false, this);			
	}
	
	/**
	 * Consente all'utente di selezionare un film in base a dei criteri di ricerca
	 * @return il film corrispondente ai criteri inseriti dall'utente
	 */
	public Film scegliFilm() 
	{
		Film filmSelezionato = MenuScegliFilm.show(this);
		return filmSelezionato;
	}	
}
