package model;

import java.io.Serializable;
import java.util.Vector;

/**
 * Classe che rappresenta l'insieme dei film in archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Films extends Risorse implements Serializable  
{	
	private static final long serialVersionUID = 1L;
	
//	public Vector<Film> getFilms() 
//	{
//		return films;
//	}
//	public void setFilms(Vector<Film> films) 
//	{
//		this.films = films;
//	}
	
//	public boolean addFilm(Film f)
//	{			
//		if(!filmEsistente(f))
//		{
//			addPerSottoCategorie(f);
//			return true;
//		}
//		else
//		{
//			return false;
//		}		
//	}
	
//	public boolean filmEsistente(Film f) 
//	{
//		for(Film film : films)
//		{			
//			if(f.getTitolo().equals(film.getTitolo()) && f.getRegista().equals(film.getRegista()) && f.getDurata() == film.getDurata() 
//				&& f.getAnnoDiUscita() == film.getAnnoDiUscita() && f.getLingua().equals(film.getLingua()) 
//				&& f.getSottoCategoria().equals(film.getSottoCategoria()) && f.isPrestabile() == film.isPrestabile())
//			{
//				return true;
//			}
//		}
//		return false;
//	}
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
//	private void addPerSottoCategorie(Film f)
//	{
//		if(films.isEmpty())
//		{
//			films.add(f);
//		}
//		else
//		{
//			for(int i = 0; i < films.size(); i++)
//			{
//				if(films.get(i).getSottoCategoria().equals(f.getSottoCategoria()))
//				{
//					films.add(i+1, f);
//					return;
//				}
//			}
//			films.add(f);
//		}
//	}
	
	/**
	 * filtra tutti i film in base al titolo
	 * (precondizione: titoloParziale != null)
	 * @param titoloParziale la parte di titolo usata come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
//	public Vector<Film> filtraFilmPerTitolo(String titoloParziale)
//	{		
//		Vector<Film> filmTrovati = new Vector<>(); 
//		
//		for(Film film : films)
//		{
//			if(film.isPrestabile() && film.getTitolo().toLowerCase().contains(titoloParziale.toLowerCase()))
//			{
//				filmTrovati.add(film);
//			}
//		}
//		return filmTrovati;
//	}
//	
	/**
	 * filtra tutti i film in base all'anno di pubblicazione
	 * (precondizione: annoUscita != null)
	 * @param annoUscita l'anno da usare come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
//	public Vector<Film> filtraFilmPerUscita(int annoUscita) 
//	{
//		Vector<Film> filmTrovati = new Vector<>(); 
//		
//		for(Film film : films)
//		{
//			if(film.isPrestabile() && film.getAnnoDiUscita() == annoUscita)
//			{
//				filmTrovati.add(film);
//			}
//		}
//		return filmTrovati;
//	}

	/**
	 * filtra tutti i film in base al regista
	 * (precondizione: regista != null)
	 * @param regista il nome del regista da usare come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public Vector<Risorsa> filtraFilmPerRegista(String regista)
	{
		Vector<Risorsa> filmTrovati = new Vector<>(); 
		for(Risorsa film : getRisorse())
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