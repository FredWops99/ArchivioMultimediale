package menus.utenti;

import controller.ArchivioController;
import menus.risorse.MenuAggiungiRisorsa;
import menus.risorse.MenuCercaRisorsa;
import menus.risorse.MenuElencoRisorse;
import menus.storico.MenuStorico;
import model.Fruitori;
import model.Prestiti;
import myLib.MyMenu;
import model.Main;
import view.MessaggiSistemaView;

public class MenuOperatore 
{
	
	private static final String[] CATEGORIE = {"Libri","Film"};
	private static final String[] MENU_OPERATORE_SCELTE = {"Visualizza fruitori","Aggiungi una risorsa","Rimuovi una risorsa","Visualizza l'elenco delle risorse",
			"Cerca una risorsa", "Visualizza tutti i prestiti attivi","Visualizza storico"};
	private static final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
	
	private static boolean continuaMenuOperatore;

	public static void show(Fruitori fruitori, Prestiti prestiti, ArchivioController archivioController)
	{
		MessaggiSistemaView.accessoEseguito();
		
		MyMenu menuOperatore = new MyMenu(MENU_INTESTAZIONE, MENU_OPERATORE_SCELTE, true);
		continuaMenuOperatore=true;
		do
		{
			gestisciMenuOperatore(menuOperatore.scegli(), fruitori, prestiti, archivioController);
		}
		while(continuaMenuOperatore);
	}
	
	/**
	 * menu che compare una volta che si esegue l'accesso come operatore
	 * @param scelta la scelta selezionata dall'utente
	 */
	private static void gestisciMenuOperatore(int scelta, Fruitori fruitori, Prestiti prestiti, ArchivioController archivioController) 
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
				//fruitori.stampaFruitoriAttivi();
				fruitori.stampaDati();
				
				continuaMenuOperatore=true;
				break;
			}
			case 2://AGGIUNGI RISORSA
			{
//				archivio.getLibri diventerà libriController?
				MenuAggiungiRisorsa.show(CATEGORIE, archivioController);
				
				Main.salvaArchivio();
//				GestoreSalvataggi.salvaArchivio(archivio);
				
				continuaMenuOperatore=true;
				break;
			}
			case 3://RIMUOVI RISORSA
			{
				String idRimosso = archivioController.rimuoviRisorsa(CATEGORIE);
//				se utente annulla procedura ritorna "-1"
				if(!idRimosso.equals("-1"))
				{
					prestiti.annullaPrestitiConRisorsa(idRimosso);
					Main.salvaArchivio();
//					GestoreSalvataggi.salvaArchivio(archivio);
				}
				
				continuaMenuOperatore=true;
				break;
			}
			case 4://VISUALIZZA ELENCO RISORSE
			{
				MenuElencoRisorse.show(CATEGORIE, archivioController);
				
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
				prestiti.stampaPrestitiAttivi();
				
				continuaMenuOperatore = true;
				break;
			}
			case 7://VISUALIZZA STORICO
			{
				MenuStorico.show(prestiti, archivioController, fruitori);
				
				continuaMenuOperatore = true;
				break;
			}
		}
	}
}
