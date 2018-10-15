package handler;

import controllerMVC.RisorseController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
import interfaces.ISavesManager;
import myLib.MyMenu;
import view.MessaggiSistemaView;

public class OperatoreHandler 
{
	private final String[] CATEGORIE = {"Libri","Film"};

	FruitoriController fruitoriController;
	RisorseController risorseController;
	PrestitiController prestitiController;
	StoricoController storicoController;
	ISavesManager gestoreSalvataggi;
	private ManageRisorseHandler manageRisorseHandler;
	
	public OperatoreHandler(FruitoriController fruitoriController, RisorseController archivioController,PrestitiController prestitiController, 
								StoricoController storicoController, ISavesManager gestoreSalvataggi)
	{
		this.fruitoriController = fruitoriController;
		this.risorseController = archivioController;
		this.prestitiController = prestitiController;
		this.storicoController = storicoController;
		this.gestoreSalvataggi = gestoreSalvataggi;
		this.manageRisorseHandler = new ManageRisorseHandler(archivioController);
	}
	/**
	 * menu che compare una volta che si esegue l'accesso come operatore
	 */
	public void menuOperatore() 
	{
		final String[] MENU_OPERATORE_SCELTE = {"Visualizza fruitori","Aggiungi una risorsa","Rimuovi una risorsa","Visualizza l'elenco delle risorse",
				"Cerca una risorsa", "Visualizza tutti i prestiti attivi","Visualizza storico"};
		final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
		
		MessaggiSistemaView.accessoEseguito();
		
		MyMenu menuOperatore = new MyMenu(MENU_INTESTAZIONE, MENU_OPERATORE_SCELTE, true);
		boolean terminato;
		int scelta;
		do
		{
			scelta = menuOperatore.scegli();
			terminato = menuOperatore(scelta);
		}
		while(!terminato);
	}
	
	public boolean menuOperatore(int scelta) 
	{
		boolean terminato;
		switch(scelta)
		{
			case 0://EXIT
			{
				terminato = true;
				break;
			}
			case 1://VISUALIZZA FRUITORI
			{
				fruitoriController.stampaDatiFruitori();
				terminato = false;
				break;
			}
			case 2://AGGIUNGI RISORSA
			{
				risorseController.menuAggiungiRisorsa();
				
				gestoreSalvataggi.salvaRisorse();
				terminato = false;
				break;
			}
			case 3://RIMUOVI RISORSA
			{
				String idRimosso = risorseController.scegliRisorsaDaRimuovere();
				//se utente annulla procedura ritorna "-1"
				if(!idRimosso.equals("-1"))
				{
					prestitiController.annullaPrestitiConRisorsa(idRimosso);
					gestoreSalvataggi.salvaRisorse();
				}
				terminato = false;
				break;
			}
			case 4://VISUALIZZA ELENCO RISORSE
			{
				risorseController.menuVisualizzaElencoRisorse();
				terminato = false;
				break;
			}
			case 5://CERCA RISORSA
			{
				final String INTESTAZIONE = "scegli la categoria: ";
				MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
				int sceltaRisorsa = menu.scegliBase();
				
				manageRisorseHandler.cercaRisorsa(sceltaRisorsa, CATEGORIE);				
				terminato = false;
				break;
			}
			case 6://VIUSALIZZA TUTTI I PRESTITI ATTIVI
			{
				prestitiController.stampaPrestitiAttivi();
				terminato = false;
				break;
			}
			case 7://VISUALIZZA STORICO
			{
				storicoController.menuStorico();
				terminato = false;
				break;
			}
			default:
			{
				terminato = true;
			}
		}
		return terminato;
	}
}
