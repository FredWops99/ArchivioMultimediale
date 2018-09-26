package service;

import controllerMVC.ArchivioController;
import controllerMVC.FruitoriController;
import controllerMVC.PrestitiController;
import controllerMVC.StoricoController;
import handler.utenti.AccessoHandler;
import model.Fruitore;
import myLib.MyMenu;

//POTREBBE ESSERE SINGLETON

public class MainFacade 
{
	public void begin(Fruitore utenteLoggato, ArchivioController archivioController, FruitoriController fruitoriController,
							StoricoController storicoController, PrestitiController prestitiController) 
	{
		mostraMenuAccesso(utenteLoggato, archivioController, fruitoriController, storicoController, prestitiController);
	}
	
	private void mostraMenuAccesso(Fruitore utenteLoggato, ArchivioController archivioController, FruitoriController fruitoriController, 
			StoricoController storicoController, PrestitiController prestitiController) 
	{
		final String MENU_ACCESSO = "Scegliere la tipologia di utente con cui accedere: ";
		final String[] MENU_ACCESSO_SCELTE = {"Fruitore", "Operatore"};
		
		MyMenu menuAccesso = new MyMenu(MENU_ACCESSO, MENU_ACCESSO_SCELTE);
		
		boolean fine;
		do
		{
			int scelta = menuAccesso.scegli();
			fine = AccessoHandler.gestisciAccesso(utenteLoggato, scelta, archivioController, fruitoriController, storicoController, prestitiController);
		}
	while(!fine);	
	}
	

}
