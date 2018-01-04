package parte3;

import java.io.Serializable;

public class Archivio implements Serializable 
{
//	creo questa classe in modo da avere tutte le risorse insieme e anche per salvare solo questo oggetto in un file
//	per questa versione non serve ancora
	
	private static final long serialVersionUID = 1L;
	
	private Libri libri;
//	private Films films;
	
	public Archivio()
	{
		setLibri(new Libri());
//		films = new Films();
	}

	public Libri getLibri() 
	{
		return libri;
	}
	public void setLibri(Libri libri)
	{
		this.libri = libri;
	}

}
