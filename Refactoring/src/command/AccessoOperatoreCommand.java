package command;

import handler.OperatoreHandler;
import myLib.MyMenu;
import viewInterfaces.IMessaggiSistemaView;

public class AccessoOperatoreCommand implements ICommand 
{
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";	
	private OperatoreHandler handler;
	private IMessaggiSistemaView messaggiSistemaView;

	
	public AccessoOperatoreCommand(OperatoreHandler handler, IMessaggiSistemaView messaggiSistemaView) 
	{
		this.handler = handler;
		this.messaggiSistemaView = messaggiSistemaView;
	}
	
	@Override
	public void gestisciAccesso() 
	{
		String passwordOperatore = messaggiSistemaView.chiediPasswordOperatore();
		if(passwordOperatore.equals(PASSWORD_ACCESSO_OPERATORE))
		{
			final String[] MENU_OPERATORE_SCELTE = {"Visualizza fruitori","Aggiungi una risorsa","Rimuovi una risorsa","Visualizza l'elenco delle risorse",
					"Cerca una risorsa", "Visualizza tutti i prestiti attivi","Visualizza storico"};
			final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
			
			messaggiSistemaView.accessoEseguito();
			
			MyMenu menuOperatore = new MyMenu(MENU_INTESTAZIONE, MENU_OPERATORE_SCELTE, true);
			boolean terminatoOperatore;
			int sceltaOperatore;
			do
			{
				sceltaOperatore = menuOperatore.scegli();
				terminatoOperatore = handler.menuOperatore(sceltaOperatore);
			}
			while(!terminatoOperatore);				
		}
		else
		{
			handler.getFruitoriController().getFruitoriView().passwordErrata();
		}
	}
}
