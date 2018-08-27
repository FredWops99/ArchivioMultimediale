package menus.utenti;

import controller.ArchivioController;
import controller.FruitoriController;
import controller.PrestitiController;
import main.Main;
import model.Fruitore;
import myLib.MyMenu;

public class MenuFruitore 
{
	private static final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Area personale (Login)"};
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	
	private static boolean continuaMenuFruitore;
	
	public static void show(Fruitore utenteLoggato, ArchivioController ac, FruitoriController fc, PrestitiController pc)
	{
		MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE, true);
		continuaMenuFruitore=true;
		do
		{
			gestisciMenuFruitore(utenteLoggato, menuFruitore.scegli(), ac, fc, pc);
		}
		while(continuaMenuFruitore);
	}
	
	/**
	 * menu che compare una volta che si esegue l'accesso come fruitore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuFruitore(Fruitore utenteLoggato, int scelta, ArchivioController archivioController, 
													FruitoriController fruitoriController, PrestitiController prestitiController)
	{
		continuaMenuFruitore=true;
		
		switch(scelta)
		{
			case 0:	//EXIT
			{
				continuaMenuFruitore=false;
				break;
			}
			case 1:	//registrazione nuovo fruitore
			{
				fruitoriController.addFruitore();
				Main.salvaFruitori();

				continuaMenuFruitore=true;//torna al menu
				break;				
			}
			case 2:	//login
			{
				boolean loginRiuscito = fruitoriController.login(utenteLoggato);
				if(loginRiuscito)
				{
					MenuPersonale.show(utenteLoggato, archivioController, fruitoriController, prestitiController);
				}

				continuaMenuFruitore=true;
				break;
			}
		}	
	}
}
