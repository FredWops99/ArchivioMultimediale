package model;

import java.io.Serializable;
import java.util.Vector;

import interfaces.Risorsa;

public class Risorse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Vector<Risorsa> risorse;
	
//	servono per gli id incrementali delle risorse
	private int lastId;
	
	public Risorse()
	{
		this.risorse = new Vector<>();
		lastId=0;
	}
	
	public Vector<Risorsa> getRisorse() 
	{
		return risorse;
	}
	public void setRisorse(Vector<Risorsa> risorse) 
	{
		this.risorse = risorse;
	}
	
	public int getLastId()
	{
		return lastId;
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
			lastId++;
			return true;
		}
		else return false;
	}
	
	public void addPerSottoCategorie(Risorsa r)
	{
//		se il vettore è vuoto o se c'è un solo elemento, aggiungo in coda
		if(risorse.size() <= 1)
		{
			risorse.add(r);
		}
		else //size > 1
		{
			int posCandidata = -1;
			for(int i = 0; i < risorse.size(); i++)
			{
				if(risorse.get(i).getClass().equals(r.getClass()))
				{
//					quando trovo una risorsa della stessa classe salvo la posizione
					posCandidata = i;
					if(risorse.get(i).getSottoCategoria().equals(r.getSottoCategoria()))
					{
						risorse.add(i+1, r);
						return;
					}
				}
//				c'era un elemento della stessa classe ma quello di adesso non è più della stessa classe:
//				devo inserire la risorsa in questa posizione, cioè alla fine delle risorse con stessa Classe
//				se posCandidata è -1 non ho ancora trovato la stessa classe
				else if(posCandidata != -1)
				{
					risorse.add(i, r);
					posCandidata = -1;//non ce ne sarebbe bisogno
					return;
				}
			}
//			se non c'è un'altra risorsa con stessa categoria (classe)
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
