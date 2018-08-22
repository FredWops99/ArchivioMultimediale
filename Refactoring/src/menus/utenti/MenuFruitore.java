package menus.utenti;

import controller.ArchivioController;
import model.Fruitori;
import model.Main;
import model.Prestiti;
import myLib.MyMenu;
import model.GestoreSalvataggi;
import view.FruitoriView;

public class MenuFruitore 
{
	private static final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Area personale (Login)"};
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	
	private static boolean continuaMenuFruitore;
	
	public static void show(Fruitori fruitori, ArchivioController mainController, Prestiti prestiti)
	{
		MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE, true);
		continuaMenuFruitore=true;
		do
		{
			gestisciMenuFruitore(menuFruitore.scegli(), fruitori, mainController, prestiti);
		}
		while(continuaMenuFruitore);
	}
	
	/**
	 * menu che compare una volta che si esegue l'accesso come fruitore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuFruitore(int scelta, Fruitori fruitori, ArchivioController mainController, Prestiti prestiti)
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
				fruitori.addFruitore();
				GestoreSalvataggi.salvaFruitori(fruitori);

				continuaMenuFruitore=true;//torna al menu
				break;				
			}
			case 2:	//login
			{
				String user = FruitoriView.chiediUsername();
				String password = FruitoriView.chiediPassword();
				
				Main.setUtenteLoggato(fruitori.trovaUtente(user, password));
				
				if(Main.getUtenteLoggato()==null)
				{
					FruitoriView.utenteNonTrovato();
				}
				else // -> utente trovato
				{
					FruitoriView.benvenuto(Main.getUtenteLoggato().getNome());
					
					MenuPersonale.show(mainController, prestiti);
				}

				continuaMenuFruitore=true;
				break;
			}
		}	
	}
}
