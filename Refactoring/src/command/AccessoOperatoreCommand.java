package command;

import java.lang.reflect.InvocationTargetException;

import handler.OperatoreHandler;
import myLib.MyMenu;
import viewInterfaces.IMessaggiSistemaView;

public class AccessoOperatoreCommand implements ICommand 
{
	private static final String PASSWORD_ACCESSO_OPERATORE = "operatore";	
	private OperatoreHandler handler;
	private IMessaggiSistemaView messaggiSistemaView;

	
	public AccessoOperatoreCommand(OperatoreHandler handler) 
	{
		this.handler = handler;
		try 
		{	//Singleton, istanziato da proprietà di sistema
			this.messaggiSistemaView = (IMessaggiSistemaView)Class
										.forName(System.getProperty("MessaggiSistemaView"))
										.getMethod("getInstance")
										.invoke(null, (Object[])null);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
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
