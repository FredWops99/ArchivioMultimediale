package handler.utenti;

import controller.ArchivioController;
import controller.FruitoriController;
import controller.PrestitiController;
import model.Fruitore;
import model.Main;

public class AccessoFruitoreHandler 
{
	public static boolean gestisciAccessoFruitore(Fruitore utenteLoggato, int scelta, ArchivioController archivioController, 
													FruitoriController fruitoriController, PrestitiController prestitiController)
	{
		boolean terminato;
		switch(scelta)
		{
			case 0:	//EXIT
			{
				terminato = true;
				break;
			}
			case 1:	//registrazione nuovo fruitore
			{
				fruitoriController.addFruitore();
				Main.salvaFruitori();

				terminato = false;//torna al menu
				break;				
			}
			case 2:	//login
			{
				utenteLoggato = fruitoriController.login();
				
				if(!(utenteLoggato == null))
				{
					fruitoriController.menuFruitore(utenteLoggato, archivioController, prestitiController);
				}

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
