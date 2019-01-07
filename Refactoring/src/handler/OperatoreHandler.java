package handler;

import controllerMVC.RisorseController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
import interfaces.ISavesManager;
import myLib.MyMenu;
/**
 * Handler che gestisce le operazioni di sistema, delegando alle opportune classi, in base alla scelta dell'utente all'interno di un menu.
 * qui in particolare vengono gestite le opzioni del menu personale di un operatore
 * in questa classe non è più richiesta l'interazione con l'utente
 * @author Stefano Prandini
 * @author Federico Landi
 *
 */
public class OperatoreHandler 
{
	private final String[] CATEGORIE = {"Libri","Films"};

	private FruitoriController fruitoriController;
	private RisorseController risorseController;
	private PrestitiController prestitiController;
	private StoricoController storicoController;
	private ISavesManager gestoreSalvataggi;
	private ManageRisorseHandler manageRisorseHandler;
	
	public OperatoreHandler(FruitoriController fruitoriController, RisorseController risorseController,PrestitiController prestitiController, 
								StoricoController storicoController, ISavesManager gestoreSalvataggi)
	{
		this.fruitoriController = fruitoriController;
		this.risorseController = risorseController;
		this.prestitiController = prestitiController;
		this.storicoController = storicoController;
		this.gestoreSalvataggi = gestoreSalvataggi;
		this.manageRisorseHandler = new ManageRisorseHandler(risorseController);
	}
	
	public FruitoriController getFruitoriController() 
	{
		return fruitoriController;
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
