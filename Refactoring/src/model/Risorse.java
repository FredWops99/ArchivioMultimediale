package model;

import java.util.Vector;

public abstract class Risorse 
{
	Vector<Risorsa> risorse;
	
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
				if(risorse.get(i).getSottoCategoria().equals(r.getSottoCategoria()))
				{
					risorse.add(i+1, r);
					return;
				}
			}
			risorse.add(r);
		}
	}
	
	public Vector<Risorsa> filtraRisorsaPerTitolo(String titoloParziale) 
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
	
	public Vector<Risorsa> filtraFilmPerUscita(int annoUscita) 
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
}
