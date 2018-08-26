package menus.utenti;

import controller.ArchivioController;
import controller.FruitoriController;
import controller.PrestitiController;
import menus.prestiti.MenuRichiediPrestito;
import menus.prestiti.MenuTerminaPrestiti;
import menus.risorse.MenuCercaRisorsa;
import model.Main;
import model.Prestiti;
import myLib.MyMenu;

public class MenuPersonale 
{
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	private static final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali", "Cerca una risorsa",
														"Richiedi un prestito", "Rinnova un prestito", "Visualizza prestiti in corso", "Termina prestiti"};
	private static final String[] CATEGORIE = {"Libri","Film"};
	
	private static boolean continuaMenuPersonale;

	
	public static void show(ArchivioController archivioController, FruitoriController fruitoriController, Prestiti prestiti,PrestitiController pc)
	{
		MyMenu menuPersonale=new MyMenu(MENU_INTESTAZIONE, MENU_PERSONALE_SCELTE, true);
		continuaMenuPersonale=true;
		do
		{
			gestisciMenuPersonale(menuPersonale.scegli(), archivioController, fruitoriController, prestiti,pc);
		}
		while(continuaMenuPersonale);
	}
	
	/**
	 * menu che compare dopo che un fruitore esegue il login
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuPersonale(int scelta, ArchivioController archivioController, FruitoriController fruitoriController, Prestiti prestiti, PrestitiController pc) 
	{
		continuaMenuPersonale=true;
		
		switch(scelta)
		{
			case 0:	//TORNA A MENU PRINCIPALE
			{
				continuaMenuPersonale=false;
				break;
			}
			case 1:	//RINNOVA ISCRIZIONE
			{
				fruitoriController.rinnovo(Main.getUtenteLoggato());
//				Main.getUtenteLoggato().rinnovo();
				
				continuaMenuPersonale = true;
				break;
			}
			case 2:	//VISUALIZZA INFO PERSONALI
			{
				fruitoriController.stampaDatiFruitore(Main.getUtenteLoggato());
				
				continuaMenuPersonale = true;
				break;
			}
			case 3://CERCA UNA RISORSA
			{
				MenuCercaRisorsa.show(CATEGORIE, archivioController);				
				continuaMenuPersonale=true;
				break;
			}
			case 4: //RICHIEDI PRESTITO (non in prestiti perchè devo poter accedere alle risorse)
			{
				MenuRichiediPrestito.show(prestiti, archivioController);
				
				continuaMenuPersonale = true;
				break;
			}
			case 5: //RINNOVA PRESTITO
			{
				pc.rinnovaPrestito(Main.getUtenteLoggato());
				
				continuaMenuPersonale = true;
				break;
			}
			case 6: //VISUALIZZA PRESTITI IN CORSO
			{
				pc.stampaPrestitiAttiviDi(Main.getUtenteLoggato());
				
				continuaMenuPersonale = true;
				break;
			}
			case 7://TERMINA PRESTITI
			{
				MenuTerminaPrestiti.show(prestiti, archivioController, pc);
				
				continuaMenuPersonale = true;
				break;
			}
		}
	}
	
}
