package controllerMVC;

import java.util.Vector;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.Risorse;
import myLib.MyMenu;
import viewInterfaces.IFilmsView;

/**
 * CONTROLLER interagisce con view e modifica il MODEL
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class FilmsController 
{
	private Risorse model;
	private IFilmsView filmsView;

	private ManageRisorseHandler manageRisorseHandler;

	public FilmsController(Risorse risorse, ManageRisorseHandler manageRisorseHandler) 
	{
		this.model = risorse;
		this.manageRisorseHandler = manageRisorseHandler;

//		per la view: leggo classe da proprietà di sistema (caricata dal Main) 
//		così Controller non dipende dalla classe View ma solo dalla sua interfaccia IView
		String className = System.getProperty("FilmsView"); //"FilmsView" è una chiave nel file: il valore corrispondende è il Fully-Qualified Name della classe view
		try 
		{
			this.filmsView = (IFilmsView)Class.forName(className).newInstance();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public IFilmsView getFilmsView() 
	{
		return filmsView;
	}
	
	public Risorse getModel()
	{
		return model;
	}
	
	public Vector<Risorsa> filmsPrestabili() 
	{
		Vector<Risorsa>filmPrestabili = new Vector<>();
		for(Risorsa risorsa : model.getRisorse())
		{
			if(risorsa.getId().charAt(0)=='F' && risorsa.isPrestabile())
			{
				filmPrestabili.add(risorsa);
			}
		}
		return filmPrestabili;
	}
	
	/**
	 * presenta all'utente il menu per decidere se scegliere un film dall'archivio completo o filtrando la ricerca.
	 * informerà della scelta l'handler del caso d'uso (ScegliFilmHandler)
	 * @return la risorsa selezionata dall'utente
	 */
	public Risorsa menuScegliFilm() 
	{
		final String[] SCELTE = new String[] {"Filtra ricerca", "Visualizza archivio"};
		final String INTESTAZIONE_MENU = "\nScegli come visualizzare le risorse: ";

		MyMenu menuSceltaFilm = new MyMenu(INTESTAZIONE_MENU, SCELTE, true); 
		int scelta = menuSceltaFilm.scegliBase();
		
		return manageRisorseHandler.scegliFilm(scelta);
	}
}