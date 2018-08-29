package handler.risorse.libri;

import java.util.Vector;

import controller.LibriController;
import model.Libro;

/**
 * Gestisce il caso d'uso di scelta di un libro, in entrambi gli scenari: scelta dall'archivio completo o filtrando la ricerca
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class ScegliLibroHandler 
{	
	public static Libro scegliLibro(int scelta, LibriController libriController)
	{
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Libro> libriFiltrati = libriController.menuFiltraLibri(true);
				
				return libriController.selezionaLibro(libriFiltrati);
			}
				
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Libro>libriPrestabili = libriController.libriPrestabili();
				
				return libriController.selezionaLibro(libriPrestabili);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
}

