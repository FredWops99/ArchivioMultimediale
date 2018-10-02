package handler.utenti;

import controllerMVC.ArchivioController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
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
	
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";	
	
	public AccessoHandler(Fruitore utenteLoggato, ArchivioController archivioController,FruitoriController fruitoriController,
							StoricoController storicoController, PrestitiController prestitiController)
	{
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
				menuAccessoFruitore(utenteLoggato, archivioController, prestitiController);
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
	public void menuAccessoFruitore(Fruitore utenteLoggato, ArchivioController archivioController, PrestitiController prestitiController) 
	{
		final String[] MENU_INIZIALE_SCELTE={"Registrazione", "Area personale (Login)"};
		final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
		
		MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_INIZIALE_SCELTE, true);
		
		int scelta;
		boolean terminato;
		do
		{
			scelta = menuFruitore.scegli();
			terminato = AccessoFruitoreHandler.gestisciAccessoFruitore(utenteLoggato, scelta, archivioController, 
																		fruitoriController, prestitiController);
		}
		while(!terminato);
	}
}
