package parte4;

import java.io.Serializable;

public class Archivio implements Serializable 
{
//	creo questa classe in modo da avere tutte le risorse insieme e per salvare solo questo oggetto in un file
	
	private static final long serialVersionUID = 1L;
	
	private Libri libri;
	private Films films;
	
	public Archivio()
	{
		setLibri(new Libri());
		setFilms(new Films());
	}

	public Libri getLibri() 
	{
		return libri;
	}
	public void setLibri(Libri libri)
	{
		this.libri = libri;
	}
	public Films getFilms()
	{
		return films;
	}
	public void setFilms(Films films)
	{
		this.films = films;
	}

}
