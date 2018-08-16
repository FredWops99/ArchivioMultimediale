package Menus;

import model.Archivio;
import model.Fruitori;
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

	public static void show(Fruitori fruitori, Archivio archivio, Prestiti prestiti)
	{
		MyMenu menuAccesso = new MyMenu(MENU_ACCESSO, MENU_ACCESSO_SCELTE);
		continuaMenuAccesso=true;
		do
		{
			gestisciMenuAccesso(menuAccesso.scegli(), fruitori, archivio, prestiti);
		}
		while(continuaMenuAccesso);	
	}
	
	/**
	 * menu iniziale: si sceglie se si vuole accedere come fruitore (1) o come operatore (2)
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuAccesso(int scelta, Fruitori fruitori, Archivio archivio, Prestiti prestiti) 
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
				MenuFruitore.show(fruitori, archivio, prestiti);
				
				continuaMenuAccesso=true;
				break;
			}
			case 2://accesso OPERATORE
			{
				String passwordOperatore = MessaggiSistemaView.chiediPasswordOperatore();
				if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
				{
					MenuOperatore.show(fruitori, archivio, prestiti);
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
