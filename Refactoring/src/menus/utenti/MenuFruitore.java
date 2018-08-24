package menus.utenti;

import controller.ArchivioController;
import controller.FruitoriController;
import model.Main;
import model.Prestiti;
import myLib.MyMenu;

public class MenuFruitore 
{
	private static final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Area personale (Login)"};
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	
	private static boolean continuaMenuFruitore;
	
	public static void show(ArchivioController archivioController, FruitoriController fruitoriController, Prestiti prestiti)
	{
		MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE, true);
		continuaMenuFruitore=true;
		do
		{
			gestisciMenuFruitore(menuFruitore.scegli(), archivioController, fruitoriController, prestiti);
		}
		while(continuaMenuFruitore);
	}
	
	/**
	 * menu che compare una volta che si esegue l'accesso come fruitore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuFruitore(int scelta, ArchivioController archivioController, FruitoriController fruitoriController, Prestiti prestiti)
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
				boolean loginRiuscito = fruitoriController.login();
				if(loginRiuscito)
				{
					MenuPersonale.show(archivioController, fruitoriController, prestiti);
				}

				continuaMenuFruitore=true;
				break;
			}
		}	
	}
}
