package menus.utenti;

import controller.ArchivioController;
import controller.FruitoriController;
import controller.PrestitiController;
import controller.StoricoController;
import model.Fruitore;
import myLib.MyMenu;
import view.FruitoriView;
import view.MessaggiSistemaView;

public class MenuAccesso 
{
	private static final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
	private static final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";
	
	private static boolean continuaMenuAccesso;

	public static void show(Fruitore utenteLoggato, ArchivioController ac, FruitoriController fc, StoricoController sc, PrestitiController pc)
	{
		MyMenu menuAccesso = new MyMenu(MENU_ACCESSO, MENU_ACCESSO_SCELTE);
		continuaMenuAccesso=true;
		do
		{
			gestisciMenuAccesso(utenteLoggato, menuAccesso.scegli(), ac, fc, sc,pc);
		}
		while(continuaMenuAccesso);	
	}
	
	/**
	 * menu iniziale: si sceglie se si vuole accedere come fruitore (1) o come operatore (2)
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuAccesso(Fruitore utenteLoggato, int scelta, ArchivioController archivioController, FruitoriController fruitoriController, 
						StoricoController storicoController,PrestitiController prestitiController) 
	{
		continuaMenuAccesso=true;

		switch(scelta)
		{
			case 0://EXIT
			{
				MessaggiSistemaView.stampaAddio();
				continuaMenuAccesso=false;
				break;
			}
			case 1://accesso FRUITORE
			{
				MenuFruitore.show(utenteLoggato, archivioController, fruitoriController, prestitiController);
				
				continuaMenuAccesso=true;
				break;
			}
			case 2://accesso OPERATORE
			{
				String passwordOperatore = MessaggiSistemaView.chiediPasswordOperatore();
				if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
				{
					MenuOperatore.show(storicoController, archivioController, fruitoriController, prestitiController);
				}
				else
				{
					FruitoriView.pswErrata();
				}
				
				continuaMenuAccesso=true;
				break;
			}
		}
	}
}
