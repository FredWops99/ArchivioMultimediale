package menus.utenti;

import controller.ArchivioController;
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

	
	public static void show(ArchivioController archivioController, Prestiti prestiti)
	{
		MyMenu menuPersonale=new MyMenu(MENU_INTESTAZIONE, MENU_PERSONALE_SCELTE, true);
		continuaMenuPersonale=true;
		do
		{
			gestisciMenuPersonale(menuPersonale.scegli(), archivioController, prestiti);
		}
		while(continuaMenuPersonale);
	}
	
	/**
	 * menu che compare dopo che un fruitore esegue il login
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuPersonale(int scelta, ArchivioController archivioController, Prestiti prestiti) 
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
				Main.getUtenteLoggato().rinnovo();
				
				continuaMenuPersonale = true;
				break;
			}
			case 2:	//VISUALIZZA INFO PERSONALI
			{
				Main.getUtenteLoggato().stampaDati();
				
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
				prestiti.rinnovaPrestito(Main.getUtenteLoggato());
				
				continuaMenuPersonale = true;
				break;
			}
			case 6: //VISUALIZZA PRESTITI IN CORSO
			{
				prestiti.stampaPrestitiAttiviDi(Main.getUtenteLoggato());
				
				continuaMenuPersonale = true;
				break;
			}
			case 7://TERMINA PRESTITI
			{
				MenuTerminaPrestiti.show(prestiti, archivioController);
				
				continuaMenuPersonale = true;
				break;
			}
		}
	}
	
}
