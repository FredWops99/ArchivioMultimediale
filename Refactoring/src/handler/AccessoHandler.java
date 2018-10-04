package handler;

import controllerMVC.ArchivioController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
import interfaces.ISavesManager;
import model.Fruitore;
import myLib.MyMenu;
import view.FruitoriView;
import view.MessaggiSistemaView;

public class AccessoHandler 
{
	private Fruitore utenteLoggato;
	private ArchivioController archivioController;
	private FruitoriController fruitoriController;
	private StoricoController storicoController;
	private PrestitiController prestitiController;
	private ISavesManager gestoreSalvataggi;
	
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";	
	
	public AccessoHandler(ISavesManager gestoreSalvataggi, Fruitore utenteLoggato, ArchivioController archivioController,FruitoriController fruitoriController,
							StoricoController storicoController, PrestitiController prestitiController)
	{
		this.gestoreSalvataggi = gestoreSalvataggi;
		this.utenteLoggato = utenteLoggato;
		this.archivioController = archivioController;
		this.fruitoriController = fruitoriController;
		this.storicoController = storicoController;
		this.prestitiController = prestitiController;
	}
	/**
	 * menu iniziale: si sceglie se si vuole accedere come fruitore (1) o come operatore (2)
	 * @param scelta la scelta selezionata dall'utente
	 */
	public boolean gestisciAccesso(int scelta) 
	{
		boolean terminato;

		switch(scelta)
		{
			case 0://EXIT
			{
				MessaggiSistemaView.stampaAddio();
				terminato = true;
				break;
			}
			case 1://accesso FRUITORE
			{
				accessoFruitore();
//				FruitoreHandler.show(utenteLoggato, archivioController, fruitoriController, prestitiController);
				
				terminato = false;
				break;
			}
			case 2://accesso OPERATORE
			{
				String passwordOperatore = MessaggiSistemaView.chiediPasswordOperatore();
				if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
				{
					fruitoriController.menuOperatore(storicoController, archivioController, prestitiController);
//					MenuOperatore.show(storicoController, archivioController, fruitoriController, prestitiController);
				}
				else
				{
					FruitoriView.pswErrata();
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
	 * menu che compare una volta che si esegue l'accesso come fruitore
	 * @param scelta la scelta selezionata dall'utente
	 */
	public void accessoFruitore() 
	{
		final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Area personale (Login)"};
		final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
		
		MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE, true);
		
		int scelta;
		boolean terminato;
		do
		{
			scelta = menuFruitore.scegli();
			terminato = gestisciAccessoFruitore(scelta);
		}
		while(!terminato);
	}
	
	public boolean gestisciAccessoFruitore(int scelta)
	{
		boolean terminato;
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
				
				terminato = false;//torna al menu
				break;				
			}
			case 2:	//login
			{
				utenteLoggato = fruitoriController.login();
				
				if(!(utenteLoggato == null))
				{
					fruitoriController.menuFruitore(utenteLoggato, archivioController, prestitiController);
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
}
