package handler;

import controllerMVC.RisorseController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import interfaces.ISavesManager;
import model.Fruitore;
import myLib.MyMenu;

public class FruitoreHandler 
{
	private final String[] CATEGORIE = {"Libri","Film"};

	private Fruitore utenteLoggato;
	private FruitoriController fruitoriController;
	private RisorseController risorseController;
	private PrestitiController prestitiController;
	private ISavesManager gestoreSalvataggi;
	private ManagePrestitiHandler managePrestitiHandler;
	private ManageRisorseHandler manageRisorseHandler;
	
	public FruitoreHandler(FruitoriController fruitoriController, RisorseController risorseController,
											PrestitiController prestitiController, ISavesManager gestoreSalvataggi)
	{
		this.fruitoriController = fruitoriController;
		this.risorseController = risorseController;
		this.prestitiController = prestitiController;
		this.gestoreSalvataggi = gestoreSalvataggi;
		this.manageRisorseHandler = new ManageRisorseHandler(risorseController);
	}
	
	public boolean entryMenuFruitore(int scelta)
	{
		boolean terminato = false;
			
		switch(scelta)
		{
			case 0:	//EXIT
			{
				terminato = true;
				break;
			}
			case 1:	//registrazione nuovo fruitore
			{
				fruitoriController.addFruitore();
				gestoreSalvataggi.salvaFruitori();
				
				terminato = false;
				break;				
			}
			case 2:	//login
			{
				utenteLoggato = fruitoriController.login();
				
				if(!(utenteLoggato == null))
				{
					menuFruitore();
				}
				
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

	/**
	 * menu che compare dopo che un fruitore esegue il login
	 */
	public void menuFruitore() 
	{
//		lo creo qui perchè mi serve l'utenteLoggato
		managePrestitiHandler = new ManagePrestitiHandler(utenteLoggato, prestitiController, risorseController, gestoreSalvataggi);
		
		final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
		final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali", "Cerca una risorsa",
															"Richiedi un prestito", "Rinnova un prestito", "Visualizza prestiti in corso", "Termina prestiti"};
		
		MyMenu menuPersonale=new MyMenu(MENU_INTESTAZIONE, MENU_PERSONALE_SCELTE, true);
		
		int scelta;
		boolean terminato;
		do
		{
			scelta = menuPersonale.scegli();
			terminato = menuFruitore(scelta);
		}
		while(!terminato);
	}
	
	public boolean menuFruitore(int scelta) 
	{
		boolean terminato;
		
		switch(scelta)
		{
			case 0:	//TORNA A MENU PRINCIPALE
			{
				terminato = true;
				break;
			}
			case 1:	//RINNOVA ISCRIZIONE
			{
				fruitoriController.rinnovo(utenteLoggato);	
				gestoreSalvataggi.salvaFruitori();
				terminato = false;
				break;
			}
			case 2:	//VISUALIZZA INFO PERSONALI
			{
				fruitoriController.stampaDatiFruitore(utenteLoggato);
				terminato = false;
				break;
			}
			case 3://CERCA UNA RISORSA
			{
				final String INTESTAZIONE = "scegli la categoria: ";
				MyMenu menu = new MyMenu(INTESTAZIONE, CATEGORIE, true);
				int sceltaRisorsa = menu.scegliBase();
				
				manageRisorseHandler.cercaRisorsa(sceltaRisorsa, CATEGORIE);
				terminato=false;
				break;
			}
			case 4: //RICHIEDI PRESTITO
			{
				final String SCELTA_CATEGORIA = "scegli la categoria di risorsa: ";
				MyMenu menu = new MyMenu(SCELTA_CATEGORIA, CATEGORIE);
				int sceltaRisorsa = menu.scegliBase();
				
				managePrestitiHandler.richiediPrestito(sceltaRisorsa);
				terminato = false;
				
				break;
			}
			case 5: //RINNOVA PRESTITO
			{
				prestitiController.rinnovaPrestito(utenteLoggato);
				terminato = false;
				break;
			}
			case 6: //VISUALIZZA PRESTITI IN CORSO
			{
				prestitiController.stampaPrestitiAttiviDi(utenteLoggato);
				terminato = false;
				break;
			}
			case 7://TERMINA PRESTITI
			{
				String[] scelte = new String[] {"tutti","solo uno"};
				String messaggioEliminaPrestiti = "Vuoi eliminare tutti i prestiti o solo uno?";
				MyMenu menuPrestiti = new MyMenu(messaggioEliminaPrestiti, scelte, true);
				int sceltaPrestiti = menuPrestiti.scegliBase();
				
				managePrestitiHandler.terminaPrestiti(sceltaPrestiti);
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
