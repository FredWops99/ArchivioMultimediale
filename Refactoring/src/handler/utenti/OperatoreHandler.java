package handler.utenti;

import controllerMVC.ArchivioController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
import service.Main;

public class OperatoreHandler 
{	
	public static boolean gestisciOperatore(int scelta, StoricoController storicoController, ArchivioController archivioController, 
												FruitoriController fruitoriController,PrestitiController prestitiController) 
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
				archivioController.menuAggiungiRisorsa();
				
				Main.salvaArchivio();
				terminato = false;
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
				}
				terminato = false;
				break;
			}
			case 4://VISUALIZZA ELENCO RISORSE
			{
				archivioController.menuVisualizzaElencoRisorse();
				terminato = false;
				break;
			}
			case 5://CERCA RISORSA
			{
				archivioController.menuCercaRisorsa();
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
