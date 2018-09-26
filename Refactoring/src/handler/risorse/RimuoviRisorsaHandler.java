package handler.risorse;

import controllerMVC.ArchivioController;

public class RimuoviRisorsaHandler 
{
	public static String rimuoviRisorsa(int scelta, String[] CATEGORIE, ArchivioController archivioController)
	{			
		try
		{
			String categoria = CATEGORIE[scelta - 1];
			return archivioController.rimuoviRisorsa(categoria);		
		}
//		se non sono presenti risorse ritorna -1
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "-1";
		}
	}
}
