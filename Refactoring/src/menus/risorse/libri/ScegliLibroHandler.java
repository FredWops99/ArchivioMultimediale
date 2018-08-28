package menus.risorse.libri;

import java.util.Vector;
import controller.LibriController;
import model.Libro;
import myLib.MyMenu;

public class ScegliLibroHandler 
{	
	private static final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";
	private static final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};

	public static Libro show(LibriController libriController)
	{
		MyMenu menuSceltaLibro = new MyMenu(INTESTAZIONE_MENU, SCELTE, true); 
		int scelta = menuSceltaLibro.scegliBase();
		switch(scelta)
		{
			case 0://INDIETRO
			{
				return null;
			}
			case 1://FILTRA RICERCA
			{
				Vector<Libro> libriFiltrati = MenuFiltroLibri.show(true, libriController);
				
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

