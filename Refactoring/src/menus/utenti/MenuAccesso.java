package menus.utenti;

import controller.ArchivioController;
import controller.FruitoriController;
import controller.StoricoController;
import model.Prestiti;
import myLib.MyMenu;
import view.FruitoriView;
import view.MessaggiSistemaView;

public class MenuAccesso 
{
	private static final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
	private static final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";
	
	private static boolean continuaMenuAccesso;

	public static void show(Prestiti prestiti, ArchivioController ac, FruitoriController fc, StoricoController sc)
	{
		MyMenu menuAccesso = new MyMenu(MENU_ACCESSO, MENU_ACCESSO_SCELTE);
		continuaMenuAccesso=true;
		do
		{
			gestisciMenuAccesso(menuAccesso.scegli(), prestiti, ac, fc, sc);
		}
		while(continuaMenuAccesso);	
	}
	
	/**
	 * menu iniziale: si sceglie se si vuole accedere come fruitore (1) o come operatore (2)
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuAccesso(int scelta, Prestiti prestiti, ArchivioController archivioController, 
											FruitoriController fruitoriController, StoricoController storicoController) 
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
				MenuFruitore.show(archivioController, fruitoriController, prestiti);
				
				continuaMenuAccesso=true;
				break;
			}
			case 2://accesso OPERATORE
			{
				String passwordOperatore = MessaggiSistemaView.chiediPasswordOperatore();
				if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
				{
					MenuOperatore.show(storicoController, prestiti, archivioController, fruitoriController);
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
