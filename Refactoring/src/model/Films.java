package model;

import java.io.Serializable;
import java.util.Vector;

import controller.FilmController;
import menus.risorse.films.MenuFiltroFilm;
import menus.risorse.films.MenuScegliFilm;
import menus.risorse.films.MenuSottoCategoriaFilm;
//import view.FilmsView;
//import view.MessaggiSistemaView;
//import view.RisorseView;

/**
 * Classe che rappresenta l'insieme dei film in archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Films implements Serializable
{	
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
//		lastId = 0;
	}
	public Vector<Film> getfilms() 
	{
		return films;
	}
	public void setfilms(Vector<Film> films) 
	{
		this.films = films;
	}
	
	public int getLastId()
	{
		return lastId;
	}
	
	public boolean addFilm(Film f)
	{			
		if(!filmEsistente(f))
		{
			addPerSottoCategorie(f);
			return true;
		}
		else
		{
			return false;
		}		
	}
	
	private boolean filmEsistente(Film f) 
	{
		for(Film film : films)
		{			
			if(f.getTitolo().equals(film.getTitolo()) && f.getRegista().equals(film.getRegista()) && f.getDurata() == film.getDurata() 
				&& f.getAnnoDiUscita() == film.getAnnoDiUscita() && f.getLingua().equals(film.getLingua()) 
				&& f.getSottoCategoria().equals(film.getSottoCategoria()) && f.isPrestabile() == film.isPrestabile())
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * presenta all'utente la scelta della sottocategoria di Film tra quelle presenti in elenco
	 * @return la scelta dell'utente
	 */
//	private String scegliSottoCategoria()
//	{
//		String sottocategoria = MenuSottoCategoriaFilm.show();
//		
//		return sottocategoria;
//	}
	
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
//	public String removeFilm()
	{
		
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