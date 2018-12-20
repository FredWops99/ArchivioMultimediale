package command;

import handler.FruitoreHandler;
import myLib.MyMenu;

public class AccessoFruitoreCommand implements ICommand
{
	private FruitoreHandler handler;
	
	public AccessoFruitoreCommand(FruitoreHandler handler)
	{
		this.handler = handler;
	}
	
	@Override
	public void gestisciAccesso()
	{
		final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
		final String[] MENU_SCELTE = {"Registrazione", "Area personale (Login)"};
		MyMenu menuFruitore=new MyMenu(MENU_INTESTAZIONE, MENU_SCELTE, true);
		
		boolean finito = false;
		int sceltaFruitore;
		do
		{
			sceltaFruitore = menuFruitore.scegli();
			finito = handler.entryMenuFruitore(sceltaFruitore);
		}
		while(!finito);
	}
	
}
