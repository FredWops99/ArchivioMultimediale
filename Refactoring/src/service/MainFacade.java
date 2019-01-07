package service;

import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.RisorseController;
import controllerMVC.StoricoController;
import handler.AccessoHandler;
import interfaces.ISavesManager;
import model.Fruitori;
import model.Prestiti;
import model.Risorse;
import model.Storico;
import myLib.MyMenu;

//POTREBBE ESSERE SINGLETON?

/**
 * Classe che, ricevendo in costruzione gli oggetti del model, crea i relativi controller per l'interazione con l'utente e avvia la logica del 
 * programma
 * @author Stefano Prandini
 * @author Federico Landi
 *
 */
public class MainFacade 
{
	private final String MENU_ACCESSO_TITOLO = "Scegliere la tipologia di utente con cui accedere: ";
	private final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
	
	private MyMenu menuAccesso;
	private AccessoHandler accessoHandler;
	
//	controllers per interazione utente-vista-model. presenti nelle operazioni dei casi d'uso (interazione utente-sistema) 
//	associano model e view.
	private RisorseController risorseController;
	private FruitoriController fruitoriController;
	private PrestitiController prestitiController;
	private StoricoController storicoController;
	private ISavesManager gestoreSalvataggi;
	
	public MainFacade(Risorse risorse, Fruitori fruitori, Prestiti prestiti, Storico storico, ISavesManager gestoreSalvataggi)
	{ 
		this.risorseController = new RisorseController(risorse);
		this.fruitoriController = new FruitoriController(fruitori);
		this.prestitiController = new PrestitiController(prestiti);
		this.storicoController = new StoricoController(storico);
		this.gestoreSalvataggi = gestoreSalvataggi;
	}
	
	public void start() 
	{		
		checkPrestiti();
		mostraMenuAccesso();
	}
	
	/**
	 * vengono eliminati i prestiti dei fruitori decaduti e i prestiti scaduti
	 */
	private void checkPrestiti() 
	{
		prestitiController.terminaTuttiPrestitiDi(fruitoriController.controlloIscrizioni());
		prestitiController.controlloPrestitiScaduti();
		
		gestoreSalvataggi.salvaFruitori();
		gestoreSalvataggi.salvaPrestiti();
	}
	
	private void mostraMenuAccesso() 
	{		
		if(menuAccesso == null)
		{
			menuAccesso = new MyMenu(MENU_ACCESSO_TITOLO, MENU_ACCESSO_SCELTE);
		}
		if(accessoHandler == null)
		{
			accessoHandler = new AccessoHandler(gestoreSalvataggi, risorseController, fruitoriController, storicoController, prestitiController);
		}
		
		boolean fine;
		do
		{
			int scelta = menuAccesso.scegli();
			
			fine = accessoHandler.gestisciAccesso(scelta);
		}
		while(!fine);	
	}
}
