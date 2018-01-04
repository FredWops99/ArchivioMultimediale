package parte3;

import java.io.Serializable;
import java.util.Vector;

import myLib.GestioneDate;

public class Prestiti implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Vector<Prestito> prestiti;
	
	public Prestiti()
	{
		prestiti = new Vector<>();
	}
	

	public Vector<Prestito> getPrestiti() 
	{
		return prestiti;
	}
	public void setPrestiti(Vector<Prestito> prestiti)
	{
		this.prestiti = prestiti;
	}

	/**
	 * controllo per tutti i prestiti presenti se sono scaduti (li rimuovo) oppure no
	 */
	public void controlloPrestiti() 
	{
		for (int i = 0; i < prestiti.size(); i++) 
		{
			if(prestiti.get(i).getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)	//se dataScadenza è precedente a oggi ritorna -1
			{
				prestiti.remove(i);
			}
		}
	}
	
}
