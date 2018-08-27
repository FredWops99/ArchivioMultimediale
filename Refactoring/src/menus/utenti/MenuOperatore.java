package menus.utenti;

import controller.ArchivioController;
import controller.FruitoriController;
import controller.PrestitiController;
import controller.StoricoController;
import main.Main;
import menus.risorse.MenuAggiungiRisorsa;
import menus.risorse.MenuCercaRisorsa;
import menus.risorse.MenuStampaElencoRisorse;
import menus.storico.MenuStorico;
import myLib.MyMenu;
import view.MessaggiSistemaView;

public class MenuOperatore 
{
	
	private static final String[] CATEGORIE = {"Libri","Film"};
	private static final String[] MENU_OPERATORE_SCELTE = {"Visualizza fruitori","Aggiungi una risorsa","Rimuovi una risorsa","Visualizza l'elenco delle risorse",
			"Cerca una risorsa", "Visualizza tutti i prestiti attivi","Visualizza storico"};
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	
	private static boolean continuaMenuOperatore;

	public static void show(StoricoController sc, ArchivioController ac, FruitoriController fc, PrestitiController pc)
	{	
		MessaggiSistemaView.accessoEseguito();
		
		MyMenu menuOperatore = new MyMenu(MENU_INTESTAZIONE, MENU_OPERATORE_SCELTE, true);
		continuaMenuOperatore=true;
		do
		{
			gestisciMenuOperatore(menuOperatore.scegli(), sc, ac, fc,pc);
		}
		while(continuaMenuOperatore);
	}
	
	/**
	 * menu che compare una volta che si esegue l'accesso come operatore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuOperatore(int scelta, StoricoController storicoController, ArchivioController archivioController, 
												FruitoriController fruitoriController,PrestitiController prestitiController) 
	{
		continuaMenuOperatore=true;
		switch(scelta)
		{
			case 0://EXIT
			{
				continuaMenuOperatore=false;
				break;
			}
			case 1://VISUALIZZA FRUITORI
			{
				fruitoriController.stampaDatiFruitori();
				continuaMenuOperatore=true;
				break;
			}
			case 2://AGGIUNGI RISORSA
			{
				MenuAggiungiRisorsa.show(CATEGORIE, archivioController);
				
				Main.salvaArchivio();
				continuaMenuOperatore=true;
				break;
			}
			case 3://RIMUOVI RISORSA
			{
				String idRimosso = archivioController.scegliRisorsaDaRimuovere();
//				se utente annulla procedura ritorna "-1"
				if(!idRimosso.equals("-1"))
				{
					prestitiController.annullaPrestitiConRisorsa(idRimosso);
					Main.salvaArchivio();
//					GestoreSalvataggi.salvaArchivio(archivio);
				}
				continuaMenuOperatore=true;
				break;
			}
			case 4://VISUALIZZA ELENCO RISORSE
			{
				MenuStampaElencoRisorse.show(CATEGORIE, archivioController);
				continuaMenuOperatore=true;
				break;
			}
			case 5://CERCA RISORSA
			{
				MenuCercaRisorsa.show(CATEGORIE, archivioController);
				continuaMenuOperatore=true;
				break;
			}
			case 6://VIUSALIZZA TUTTI I PRESTITI ATTIVI
			{
				prestitiController.stampaPrestitiAttivi();
				continuaMenuOperatore = true;
				break;
			}
			case 7://VISUALIZZA STORICO
			{
				MenuStorico.show(storicoController);
				continuaMenuOperatore = true;
				break;
			}
		}
	}
}
