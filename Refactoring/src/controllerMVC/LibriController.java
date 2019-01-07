package controllerMVC;

import java.util.Vector;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.Risorse;
import myLib.MyMenu;
import viewInterfaces.ILibriView;

/**
 * CONTROLLER interagisce con view e modifica il MODEL
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class LibriController 
{
	private Risorse model;
	private ILibriView libriView;
	private ManageRisorseHandler manageRisorseHandler;	
	
	public LibriController(Risorse risorse, ManageRisorseHandler manageRisorseHandler)
	{
		this.model = risorse;
//		per libriView: sennò Controller dipenderebbe da LibriView, a causa dell'instanziamento. così solo Interface
		try 
		{
			this.libriView = (ILibriView)Class.forName(System.getProperty("LibriView")).newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}	
		this.manageRisorseHandler = manageRisorseHandler;
	}

	public ILibriView getLibriView() 
	{
		return libriView;
	}
	
	public Risorse getModel() 
	{
		return model;
	}
	
	public Vector<Risorsa> libriPrestabili()
	{
		Vector<Risorsa>libriPrestabili = new Vector<>();
		for(Risorsa risorsa : model.getRisorse())
		{
			if(risorsa.getId().charAt(0)=='L' && risorsa.isPrestabile())
			{
				libriPrestabili.add(risorsa);
			}
		}
		return libriPrestabili;
	}
	
	/**
	 * presenta all'utente il menu per decidere se scegliere un libro dall'archivio completo o filtrando la ricerca.
	 * in base alla scelta, delegherà ad un handler la gestione del caso d'uso
	 * @return
	 */
	public Risorsa menuScegliLibro() 
	{
		final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";
		final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};
		
		MyMenu menuSceltaLibro = new MyMenu(INTESTAZIONE_MENU, SCELTE, true); 
		int scelta = menuSceltaLibro.scegliBase();
		
		return manageRisorseHandler.scegliLibro(scelta);
	}	
}