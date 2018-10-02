package controllerMVC;

import java.util.Vector;

import handler.risorse.films.FiltroFilmHandler;
import handler.risorse.films.ScegliFilmHandler;
import model.Film;
import model.Films;
import model.Risorsa;
import model.Risorse;
import myLib.MyMenu;
import view.FilmsView;
import view.MessaggiSistemaView;

public class FilmsController 
{
	private Films model;
	private int lastId;	
	
	private static final String[] SOTTOCATEGORIE = {"Azione","Avventura","Fantascienza"}; //le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
	private static final String IDENTIFIER = "F";
	
	public FilmsController(Films films) 
	{
		this.model = films;
		this.lastId = films.getLastId();
	}
	
	public int getLastId() 
	{
		return lastId;
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
		
		Film f = new Film(IDENTIFIER + lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non è già esistente)
		boolean aggiuntaRiuscita = model.addFilm(f);
		
		if(aggiuntaRiuscita)
		{
			FilmsView.aggiuntaRiuscita(Film.class);
			
//			il main salva su file quell'archivio, nella classe MenuOperatore
//			non c'è bisogno di settare l'archivio del main perchè è lo stesso riferimento
//			Main.setArchivio(model);
		}
		else
		{
			FilmsView.aggiuntaNonRiuscita(Film.class);
		}
	}
	
	/**
 	 * metodo per Test che consente di non chiedere in input all'utente i campi per creare la risorsa
	 */
	public void addFilm(String sottoCategoria, String titolo, String regista, int durata, int annoDiUscita, String lingua, int nLicenze)
	{
		Film f = new Film("F"+ lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		
//		aggiunge il film al model del controller (se non è già esistente)
		model.addFilm(f);
	}
	
//	deve restituire al main l'id della risorsa da eliminare (far diventare non prestabile)
	public String removeFilm()
	{
		Vector<Film> films = model.getFilms();
		
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
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = films.get((int)posizioniRicorrenze.get(0)).getId();
			films.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
			FilmsView.rimozioneAvvenuta();
		}
//		se ci sono più elementi nel vettore (più films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			FilmsView.piùRisorseStessoTitolo(Film.class, titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				FilmsView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				stampaDatiFilm(films.elementAt((int)i), false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = FilmsView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze.size());
					
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
		return idSelezionato;
	}
	
	/**
	 * metodo di Test, per non dover chiedere all'utente l'input per il titolo della risorsa da eliminare
	 */
	public void removeFilm(String titolo)
	{
		Vector<Film> films = model.getFilms();
				
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
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			films.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);
		}
//		se ci sono più elementi nel vettore (più films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			FilmsView.piùRisorseStessoTitolo(Film.class, titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				FilmsView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				stampaDatiFilm(films.elementAt((int)i), false);
				MessaggiSistemaView.cornice();
			}
			
			int daRimuovere = FilmsView.chiediRicorrenzaDaRimuovere(posizioniRicorrenze.size());
					
			if(daRimuovere > 0)
			{
				films.get((int)posizioniRicorrenze.get(daRimuovere-1)).setPrestabile(false);;
				FilmsView.rimozioneAvvenuta();
			}
		}
	}
	
	public void stampaDatiFilm(Film film, boolean perPrestito) 
	{
		FilmsView.stampaDati(film, perPrestito);
	}
	
//	main contatta il controller che si occupa dell'interazione con la view
	public void stampaDatiFilmPrestabili() 
	{
		Vector<Film>filmDaStampare = new Vector<>();
		for(Film film : model.getFilms())
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
		final String SCEGLI_CATEGORIA = "scegli la sottocategoria del film: ";
		MyMenu menu = new MyMenu(SCEGLI_CATEGORIA, SOTTOCATEGORIE, true);
		try
		{
			return SOTTOCATEGORIE[menu.scegliBase() - 1];
		}
//		se l'utente selezione 0: ANNULLA -> eccezione
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "annulla";
		}	
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
	
	public Films getModel()
	{
		return model;
	}
	
	/**
	 * se i film vengono filtrati per essere prenotati viene restituita la lista, se invece è solo per la ricerca vengono visualizzati e basta
	 * @param daPrenotare 
	 * @return la lista dei film filtrati (if daPrenotare)
	 */
	public Vector<Film> menuFiltraFilm(boolean daPrenotare) 
	{
		final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
		final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di uscita", "Filtra per regista"};
		
		MyMenu menuFiltro = new MyMenu(TITOLO_MENU_FILTRO, VOCI_TITOLO_MENU_FILTRO, true); 
		int scelta = menuFiltro.scegliBase();
		
		Vector<Film> filmFiltrati = FiltroFilmHandler.filtraFilm(scelta, daPrenotare, this);
		
		if(daPrenotare)
		{
			return filmFiltrati;
		}
		else// !daPrenotare
		{
			return null;
		}
	}
	
	public Vector<Risorsa> filtraFilmPerTitolo(String titoloParziale)
	{
		return  model.filtraRisorsaPerTitolo(titoloParziale);
	}
	
	public Vector<Risorsa> filtraFilmPerUscita(int annoUscita)
	{
		return model.filtraFilmPerUscita(annoUscita);
	}
	
	public Vector<Film> filtraFilmPerRegista(String regista)
	{
		return model.filtraFilmPerRegista(regista);
	}
	
	public Vector<Film> filmsPrestabili() 
	{
		Vector<Film>filmPrestabili = new Vector<>();
		for(Film film : model.getFilms())
		{
			if(film.isPrestabile())
			{
				filmPrestabili.add(film);
			}
		}
		return filmPrestabili;
	}
	
	/**
	 * presenta all'utente il menu per decidere se scegliere un film dall'archivio completo o filtrando la ricerca.
	 * informerà della scelta l'handler del caso d'uso (ScegliFilmHandler)
	 * @return la risorsa selezionata dall'utente
	 */
	public Risorsa menuScegliFilm() 
	{
		final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};
		final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";

		MyMenu menuSceltaFilm = new MyMenu(INTESTAZIONE_MENU, SCELTE, true); 
		int scelta = menuSceltaFilm.scegliBase();
		
		return ScegliFilmHandler.scegliFilm(scelta, this);
	}
	
	public Film selezionaFilm(Vector<Film> films) 
	{
		if(films.isEmpty())
		{
			FilmsView.noRisorseDisponibili(Film.class);
			return null;
		}
		else
		{
			for(int i = 0; i < films.size(); i++)
			{
				MessaggiSistemaView.stampaPosizione(i);
				MessaggiSistemaView.cornice();
				stampaDatiFilm(films.get(i), true);
				MessaggiSistemaView.cornice();
			}
			
			int selezione;
			do
			{
				MessaggiSistemaView.cornice();
				selezione = FilmsView.selezionaRisorsa(films.size(), Film.class);
				if(selezione == 0)
				{
					return null;
				}
				else if(films.get(selezione-1).getInPrestito() < films.get(selezione-1).getNLicenze())
				{
					return films.get(selezione-1);
				}
				else
				{
					FilmsView.copieTutteInPrestito(films.get(selezione-1).getTitolo());
				}
			}
			while(true);
		}
	}	
}