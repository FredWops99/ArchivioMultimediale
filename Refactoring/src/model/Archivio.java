package model;

import java.io.Serializable;

/**
 * Classe che raggruppa le varie tipologie di risorsa (Libri, Films,...) e opera su di esse.
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Archivio implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Libri libri;
	private Films films;
	
	/**
	 * costruttore: inizializza gli oggetti Libri e Films (invocando i loro costruttori)
	 */
	public Archivio()
	{
		setLibri(new Libri());
		setFilms(new Films());
	}
	
	//GETTER//
	public Libri getLibri() 
	{
		return libri;
	}
	public Films getFilms()
	{
		return films;
	}
	//SETTER//
	public void setLibri(Libri libri)
	{
		this.libri = libri;
	}
	public void setFilms(Films films)
	{
		this.films = films;
	}

	public Risorsa getRisorsa(String id) 
	{
		Risorsa r = null;
		
		switch(id.charAt(0))
		{
		case 'L':
		{
			for(Libro libro : libri.getLibri())
			{
				if(libro.getId().equals(id))
				{
					r = libro;
				}
			}
			break;
		}
		case 'F':
		{
			for(Film film : films.getFilms())
			{
				if(film.getId().equals(id))
				{
					r = film;
				}
			}
			break;
		}
		default:
		{
			return null;
		}
		}
		return r;
	}
	
}
