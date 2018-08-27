package menus.risorse.libri;

import java.util.Vector;
import controller.LibriController;
import model.Libri;
import model.Libro;
import myLib.MyMenu;
import view.LibriView;
import view.MessaggiSistemaView;

public class MenuScegliLibro 
{	
	private static final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";
	private static final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};

	public static Libro show(LibriController libriController)
	{
		Vector<Libro> libri = libriController.getModel().getLibri();

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
				
				return selezionaLibro(libriFiltrati, libriController);
			}
				
			case 2://VISUALIZZA ARCHIVIO
			{
				Vector<Libro>libriPrestabili = new Vector<>();
				for(Libro libro : libri)
				{
					if(libro.isPrestabile())
					{
						libriPrestabili.add(libro);
					}
				}
				
				if(libriPrestabili.isEmpty())
				{
					LibriView.noRisorseDisponibili(Libri.class);
					return null;
				}
				
				LibriView.risorseInArchivio(Libri.class);
				
				return selezionaLibro(libriPrestabili, libriController);
			}
		}
//		DEFAULT: qua non arriva mai
		return null;
	}
	private static Libro selezionaLibro(Vector<Libro> libri, LibriController libriController) 
	{
		if(!libri.isEmpty())
		{
			for(int i = 0; i < libri.size(); i++)
			{
				MessaggiSistemaView.stampaPosizione(i);
				MessaggiSistemaView.cornice();
				libriController.stampaDatiLibro(libri.get(i), true);
				MessaggiSistemaView.cornice();
			}
			
			int selezione;
			do
			{
				MessaggiSistemaView.cornice();
				selezione = LibriView.selezionaRisorsa(libri.size(), Libro.class);
				if(selezione == 0)
				{
					return null;
				}
				else if(libri.get(selezione-1).getInPrestito() < libri.get(selezione-1).getNLicenze())
				{
					return libri.get(selezione-1);
				}
				else
				{
					LibriView.copieTutteInPrestito(libri.get(selezione-1).getTitolo());
				}
			}
			while(true);
		}
		else//nessuna corrispondenza: vettore vuoto
		{
			LibriView.nessunaCorrispondenza(Libro.class);
			return null;
		}
	}	
}

