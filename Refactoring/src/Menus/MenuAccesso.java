package Menus;

import model.Archivio;
import model.Fruitori;
import model.Prestiti;
import myLib.BelleStringhe;
import myLib.InputDati;
import myLib.MyMenu;

public class MenuAccesso 
{
	private static final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
	private static final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	
	private static final String MESSAGGIO_ADDIO = "\nGrazie per aver usato ArchivioMultimediale!";
	private static final String MESSAGGIO_PASSWORD = "Inserire la password per accedere all'area riservata agli operatori: ";	
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";
	
	private static boolean continuaMenuAccesso;
	private static boolean continuaMenuFruitore;

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
				System.out.println(BelleStringhe.incornicia(MESSAGGIO_ADDIO));
				continuaMenuAccesso=false;
				break;
			}
			case 1://accesso FRUITORE
			{
				do
				{
					MenuFruitore.show(fruitori, archivio, prestiti);
				}
				while(continuaMenuFruitore);
				
				continuaMenuAccesso=true;
				break;
			}
			case 2://accesso OPERATORE
			{
				String passwordOperatore = InputDati.leggiStringaNonVuota(MESSAGGIO_PASSWORD);
				if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
				{
					MenuOperatore.show(fruitori, archivio, prestiti);
				}
				else
				{
					System.out.println("Password errata!");
				}
				
				continuaMenuAccesso=true;
				break;
			}
		}
	}
}
