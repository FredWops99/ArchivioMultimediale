package Menus;

import Utility.GestoreSalvataggi;
import model.Archivio;
import model.Prestiti;
import myLib.MyMenu;
import myLib.ServizioFile;

public class MenuPrestiti 
{

	public static void show(Prestiti prestiti, Archivio archivio) 
	{
		MyMenu menuPrestiti = new MyMenu("Vuoi eliminare tutti i prestiti o solo uno?", new String[] {"tutti","solo uno"}, true);
		
		switch (menuPrestiti.scegliBase()) 
		{
		case 0://indietro
		{
			break;
		}
		case 1://elimina tutti i prestiti
		{
			prestiti.terminaTuttiPrestitiDi(utenteLoggato);
			
			GestoreSalvataggi.salvaPrestiti(prestiti);
			GestoreSalvataggi.salvaArchivio(archivio);
			
			break;
		}
		case 2://elimina un solo prestito (sceglie l'utente)
		{
			prestiti.terminaPrestitoDi(utenteLoggato);
			
			GestoreSalvataggi.salvaPrestiti(prestiti);
			GestoreSalvataggi.salvaArchivio(archivio);
			
			break;
		}
		}		
	}
	

}
