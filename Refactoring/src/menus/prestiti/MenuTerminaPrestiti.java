package menus.prestiti;

import controller.ArchivioController;
import controller.PrestitiController;
import model.Fruitore;
import model.Main;
import myLib.MyMenu;

public class MenuTerminaPrestiti 
{
	static String[] scelte = new String[] {"tutti","solo uno"};
	static String messaggioEliminaPrestiti = "Vuoi eliminare tutti i prestiti o solo uno?";

	public static void show(Fruitore utenteLoggato, ArchivioController mainController, PrestitiController prestitiController) 
	{
		MyMenu menuPrestiti = new MyMenu(messaggioEliminaPrestiti, scelte, true);
		
		switch (menuPrestiti.scegliBase()) 
		{
			case 0://indietro
			{
				break;
			}
			case 1://elimina tutti i prestiti
			{
				prestitiController.terminaTuttiPrestitiDi(utenteLoggato);
				
				Main.salvaPrestiti();
				Main.salvaArchivio();
				
				break;
			}
			case 2://elimina un solo prestito (sceglie l'utente)
			{
				prestitiController.terminaPrestitoDi(utenteLoggato);
				
				Main.salvaPrestiti();
				Main.salvaArchivio();
				
				break;
			}
		}		
	}
}
