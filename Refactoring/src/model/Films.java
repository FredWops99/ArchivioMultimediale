package model;

import java.io.Serializable;
import java.util.Vector;
import menus.risorse.films.MenuFiltroFilm;
import menus.risorse.films.MenuScegliFilm;
import menus.risorse.films.MenuSottoCategoriaFilm;
import view.FilmsView;
import view.MessaggiSistemaView;

/**
 * Classe che rappresenta l'insieme dei film in archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Films implements Serializable
{	
	private static final String TITOLO_MENU_FILTRO = "Scegli in base a cosa filtrare la ricerca: ";
	private static final String[] VOCI_TITOLO_MENU_FILTRO = {"Filtra per titolo", "Filtra per anno di uscita", "Filtra per regista"};
	private static final long serialVersionUID = 1L;
	/**
	 * id incrementale univoco per ogni film
	 */
	private int lastId;	
	private Vector<Film> films;
	
	/**
	 * costruttore della classe: inizializza il Vector di films
	 */
	public Films()
	{
		this.films = new Vector<Film>();
		lastId = 0;
	}
	public Vector<Film> getfilms() 
	{
		return films;
	}
	public void setfilms(Vector<Film> films) 
	{
		this.films = films;
	}
	
	/**
	 * procedura per l'aggiunta di un film alla raccolta: chiede all'utente di inserire tutti i campi necessari, crea l'oggetto Film e lo aggiunge al vector
	 */
	public void addFilm()
	{
		String sottoCategoria = this.scegliSottoCategoria();//la sottocategoria della categoria FILM ("Azione","Avventura","Fantascienza"...)
//		se l'utente annulla la procedura
		if(sottoCategoria == "annulla")
		{
			return;
		}
		
		String titolo = FilmsView.chiediTitolo();
		int durata = FilmsView.chiediDurata();
		int annoDiUscita = FilmsView.chiediAnnoUscita();
		String lingua = FilmsView.chiediLingua();
		String regista = FilmsView.chiediRegista();
		int nLicenze = FilmsView.chiediNumeroLicenze();
		
		Film f = new Film("F"+lastId++, sottoCategoria, titolo, regista, durata, annoDiUscita, lingua, nLicenze);
		addPerSottoCategorie(f);
		
		FilmsView.aggiuntaRiuscita();
	}
	
	/**
	 * presenta all'utente la scelta della sottocategoria di Film tra quelle presenti in elenco
	 * @return la scelta dell'utente
	 */
	private String scegliSottoCategoria()
	{
		String sottocategoria = MenuSottoCategoriaFilm.show();
		
		return sottocategoria;
	}
	
	/**
	 * inserisco i films nel Vector in modo che siano ordinati per sottocategorie, così, quando vengono stampati, i generi sono in ordine
	 * (il metodo stampaFilms li raccoglierà per generi)
	 * (precondizione: f != null)
	 * @param f il film da inserire
	 */
	private void addPerSottoCategorie(Film f)
	{
		if(films.isEmpty())
		{
			films.add(f);
		}
		else
		{
			for(int i = 0; i < films.size(); i++)
			{
				if(films.get(i).getSottoCategoria().equals(f.getSottoCategoria()))
				{
					films.add(i+1, f);
					return;
				}
			}
			films.add(f);
		}
	}
	
	/**
	 * procedura per rimuovere un film dalla raccolta: viene chiesto il nome del film e se ce ne sono più di uno viene chiesto all'utente quale eliminare
	 * @return l'id del film che l'utente ha deciso di rimuovere ("-1" se non viene rimosso nessun film)
	 */
	public String removeFilm()
	{
		String idSelezionato;
		
		String titolo = FilmsView.chiediFilmDaRimuovere();
		
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
			FilmsView.filmNonPresente();
			idSelezionato = "-1";
		}
//		se nel vettore delle ricorrenze c'è solo una posizione, elimino l'elemento in quella posizioni in films
		else if(posizioniRicorrenze.size()==1)
		{
			idSelezionato = films.get((int)posizioniRicorrenze.get(0)).getId();
			films.get((int)posizioniRicorrenze.get(0)).setPrestabile(false);;
			FilmsView.rimozioneAvvenuta();
		}
//		se ci sono più elementi nel vettore (più films con il nome inserito dall'operatore) li stampo e chiedo di selezionare quale si vuole rimuovere:
//		l'utente inserisce quale rimuovere -> prendo la posizione in films di quell'elemento e lo rimuovo da films
		else
		{
			FilmsView.piùFilmStessoTitolo(titolo);
			
			int pos = 0;
			for(Integer i : posizioniRicorrenze)
			{
				FilmsView.numeroRicorrenza(pos);
				MessaggiSistemaView.cornice();
				films.elementAt((int)i).stampaDati(false);
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
		return idSelezionato;
	}
	
	/**
	 * stampa i dati dei film corrispondenti ai parametri di ricerca specificati dall'utente
	 */
	public void cercaFilm()
	{
		MenuFiltroFilm.show(films, false);	
	}
	
	/**
	 * stampa tutti i films raggruppandoli per sottocategoria
	 */
	public void stampaFilms()
	{
		Vector<Film>filmDaStampare = new Vector<>();
		for(Film film : films)
		{
			if(film.isPrestabile())
			{
				filmDaStampare.add(film);
			}
		}
		FilmsView.stampaDati(filmDaStampare);
		
		if(filmDaStampare.isEmpty())
		{
			System.out.println("In archivio non sono presenti film disponibili");
		}
		else
		{
			if(filmDaStampare.size()==1)
			{
				System.out.println("\nE' presente un film in archivio: ");
			}
			else
			{
				System.out.println("\nSono presenti " + films.size() + " films in archivio: ");
			}
			
			for(int i = 0; i < filmDaStampare.size(); i++)
			{
//				stampa la sottocategoria solo se è il primo elemento o se il sottogenere è cambiato (sono in ordine nel vettore)
				if(i == 0 || filmDaStampare.get(i).getSottoCategoria() != filmDaStampare.get(i-1).getSottoCategoria())
				{
					MessaggiSistemaView.cornice();
					System.out.println(filmDaStampare.get(i).getSottoCategoria());
					MessaggiSistemaView.cornice();
				}
				System.out.println("titolo: " + filmDaStampare.get(i).getTitolo());
			}
		}
	}
	
	/**
	 * Consente all'utente di selezionare un film in base a dei criteri di ricerca
	 * @return il film corrispondente ai criteri inseriti dall'utente
	 */
	public Film scegliFilm() 
	{
		Film filmSelezionato = MenuScegliFilm.show(films);
		return filmSelezionato;
	}	
}