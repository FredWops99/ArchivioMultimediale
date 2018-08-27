package menus.prestiti;

import controller.ArchivioController;
import controller.PrestitiController;
import main.Main;
import model.Fruitore;
import model.Risorsa;
import myLib.MyMenu;

public class MenuRichiediPrestito 
{
	private static final String[] CATEGORIE = {"Libri","Film"};
	private static final String SCELTA_CATEGORIA = "scegli la categoria di risorsa: ";
	
	public static void show(Fruitore utenteLoggato, PrestitiController prestitiController, ArchivioController archivioController) 
	{
		MyMenu menu = new MyMenu(SCELTA_CATEGORIA, CATEGORIE);
		
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
						
			if(prestitiController.raggiunteRisorseMassime(utenteLoggato, categoria))
			{
				return;
			}
			
			Risorsa risorsa = archivioController.scegliRisorsa(categoria);
			if(risorsa != null)
			{
				prestitiController.effettuaPrestito(utenteLoggato, risorsa);
			}

			Main.salvaPrestiti();
			Main.salvaArchivio();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
