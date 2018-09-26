package handler.utenti;

import controllerMVC.ArchivioController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
import model.Fruitore;
import view.FruitoriView;
import view.MessaggiSistemaView;

public class AccessoHandler 
{
	
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";	
	/**
	 * menu iniziale: si sceglie se si vuole accedere come fruitore (1) o come operatore (2)
	 * @param scelta la scelta selezionata dall'utente
	 */
	public static boolean gestisciAccesso(Fruitore utenteLoggato, int scelta, ArchivioController archivioController, FruitoriController fruitoriController, 
						StoricoController storicoController,PrestitiController prestitiController) 
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
				fruitoriController.menuAccessoFruitore(utenteLoggato, archivioController, prestitiController);
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
}
