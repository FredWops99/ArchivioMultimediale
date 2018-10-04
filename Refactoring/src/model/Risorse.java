package model;

import java.util.Vector;

public abstract class Risorse 
{
	private Vector<Risorsa> risorse;
	/**
	 * id incrementale univoco per ogni risorsa
	 */
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
//				se tutte le risorse saranno nello stesso vettore bisogna controllare anche che la classe dell'oggetto sia lo stesso
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
			if(risorsa.stessiAttributi(r))
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

}
