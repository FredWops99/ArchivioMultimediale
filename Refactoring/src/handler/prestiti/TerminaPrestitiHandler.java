package handler.prestiti;

import controllerMVC.PrestitiController;
import model.Fruitore;
import service.Main;

public class TerminaPrestitiHandler 
{
	public static void terminaPrestiti(int scelta, Fruitore utenteLoggato, PrestitiController prestitiController) 
	{
		switch (scelta) 
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
