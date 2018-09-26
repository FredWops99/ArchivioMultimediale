package handler.utenti;

import controllerMVC.ArchivioController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import model.Fruitore;

public class FruitoreHandler 
{
	public static boolean gestisciFruitore(Fruitore utenteLoggato, int scelta, ArchivioController archivioController, 
												FruitoriController fruitoriController, PrestitiController prestitiController) 
	{
		boolean terminato;
		
		switch(scelta)
		{
			case 0:	//TORNA A MENU PRINCIPALE
			{
				terminato = true;
				break;
			}
			case 1:	//RINNOVA ISCRIZIONE
			{
				fruitoriController.rinnovo(utenteLoggato);				
				terminato = false;
				break;
			}
			case 2:	//VISUALIZZA INFO PERSONALI
			{
				fruitoriController.stampaDatiFruitore(utenteLoggato);
				terminato = false;
				break;
			}
			case 3://CERCA UNA RISORSA
			{
				archivioController.menuCercaRisorsa();
				terminato=false;
				break;
			}
			case 4: //RICHIEDI PRESTITO (non in prestiti perchè devo poter accedere alle risorse)
			{
				prestitiController.menuRichiediPrestito(utenteLoggato, archivioController);
				terminato = false;
				break;
			}
			case 5: //RINNOVA PRESTITO
			{
				prestitiController.rinnovaPrestito(utenteLoggato);
				terminato = false;
				break;
			}
			case 6: //VISUALIZZA PRESTITI IN CORSO
			{
				prestitiController.stampaPrestitiAttiviDi(utenteLoggato);
				terminato = false;
				break;
			}
			case 7://TERMINA PRESTITI
			{
				prestitiController.menuTerminaPrestiti(utenteLoggato, archivioController);
				terminato = false;
				break;
			}
			default:
			{
				terminato = true;
			}
		}
		return terminato;
	}
}