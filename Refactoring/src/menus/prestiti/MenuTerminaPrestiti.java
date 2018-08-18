package menus.prestiti;

import model.Archivio;
import model.Main;
import model.Prestiti;
import myLib.MyMenu;
import utility.GestoreSalvataggi;

public class MenuTerminaPrestiti 
{
	static String[] scelte = new String[] {"tutti","solo uno"};
	static String messaggioEliminaPrestiti = "Vuoi eliminare tutti i prestiti o solo uno?";

	
	public static void show(Prestiti prestiti, Archivio archivio) 
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
			prestiti.terminaTuttiPrestitiDi(Main.getUtenteLoggato());
			
			GestoreSalvataggi.salvaPrestiti(prestiti);
			GestoreSalvataggi.salvaArchivio(archivio);
			
			break;
		}
		case 2://elimina un solo prestito (sceglie l'utente)
		{
			prestiti.terminaPrestitoDi(Main.getUtenteLoggato());
			
			GestoreSalvataggi.salvaPrestiti(prestiti);
			GestoreSalvataggi.salvaArchivio(archivio);
			
			break;
		}
		}		
	}
	

}
