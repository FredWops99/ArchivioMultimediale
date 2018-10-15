package model;

import java.io.Serializable;
import java.util.Vector;

public class Risorse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Vector<Risorsa> risorse;
	/**
	 * id incrementale univoco per ogni risorsa
	 */
	private int lastIdFilm;
	private int lastIdLibro;
	
	public Risorse()
	{
		this.risorse = new Vector<>();
		lastIdFilm=0;
		lastIdLibro=0;
	}
	
	public Vector<Risorsa> getRisorse() 
	{
		return risorse;
	}
	public void setRisorse(Vector<Risorsa> risorse) 
	{
		this.risorse = risorse;
	}
	
	public int getLastIdLibro()
	{
		return lastIdLibro;
	}
	
	public int getLastIdFilm()
	{
		return lastIdFilm;
	}
	
	public Risorsa getRisorsa(String id)
	{
		for(Risorsa risorsa : risorse)
		{
			if(id.equals(risorsa.getId()))
			{
				return risorsa;
			}
		}
		return null;
	}
	
	public void addPerSottoCategorie(Risorsa r)
	{
		if(risorse.isEmpty())
		{
			risorse.add(r);
		}
		else
		{
			for(int i = 0; i < risorse.size(); i++)
			{
//				
//				se tutte le risorse saranno nello stesso vettore bisogna controllare anche che la classe dell'oggetto sia la stessa
//				
				if(risorse.get(i).getSottoCategoria().equals(r.getSottoCategoria()))
				{
					risorse.add(i+1, r);
					return;
				}
			}
			risorse.add(r);
		}
	}
	
	public Vector<Risorsa> filtraRisorsePerTitolo(String titoloParziale) 
	{
		Vector<Risorsa> risorseTrovate = new Vector<>(); 
		
		for(Risorsa r : risorse)
		{
			if(r.isPrestabile() && r.getTitolo().toLowerCase().contains(titoloParziale.toLowerCase()))
			{
				risorseTrovate.add(r);
			}
		}
		return risorseTrovate;
	}
	
	public Vector<Risorsa> filtraRisorsePerUscita(int annoUscita) 
	{
		Vector<Risorsa> risorseTrovate = new Vector<>(); 
		
		for(Risorsa r : risorse)
		{
			if(r.isPrestabile() && r.getAnnoDiUscita() == annoUscita)
			{
				risorseTrovate.add(r);
			}
		}
		return risorseTrovate;
	}
	
	public boolean risorsaEsistente(Risorsa r)
	{
		for(Risorsa risorsa : risorse)
		{
			if(risorsa.getId().charAt(0)==r.getId().charAt(0) && risorsa.stessiAttributi(r))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean addRisorsa(Risorsa r)
	{
		if(!risorsaEsistente(r))
		{
			addPerSottoCategorie(r);
			return true;
		}
		else
		{
			return false;
		}	
	}
	
	/**
	 * filtra tutti i libri in base all'autore
	 * (precondizione: autore != null)
	 * @param autore il nome dell'autore da usare come criterio
	 * @return un vector contenente i libri corrispondenti al criterio
	 */
	public Vector<Risorsa> filtraLibriPerAutori(String autore) 
	{
		Vector<Risorsa> libriTrovati = new Vector<>(); 
		for(Risorsa r : getRisorse())
		{
			if(r.getId().charAt(0)=='L')
			{
//				metodo getAutori è solo di Libro: posso fare il cast perchè primo char è L
				Libro libro = (Libro)r;
				if(libro.isPrestabile())
				{
					for(String s : libro.getAutori())
					{
						if(s.toLowerCase().equals(autore.toLowerCase()))
						{
							libriTrovati.add(libro);
						}
					}
				}
			}
		}
		return libriTrovati;
	}
	
	/**
	 * filtra tutti i film in base al regista
	 * (precondizione: regista != null)
	 * @param regista il nome del regista da usare come criterio
	 * @return un vector contenente i film corrispondenti al criterio
	 */
	public Vector<Risorsa> filtraFilmPerRegista(String regista)
	{
		Vector<Risorsa> filmTrovati = new Vector<>(); 
		for(Risorsa r : getRisorse())
		{
			if(r.getId().charAt(0) == 'F' && r.isPrestabile())
			{
				if(regista.toLowerCase().equals(regista.toLowerCase()))
				{
					filmTrovati.add(r);
				}
			}
		}
		return filmTrovati;
	}

}
