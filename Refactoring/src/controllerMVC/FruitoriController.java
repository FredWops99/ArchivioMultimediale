package controllerMVC;

import java.util.GregorianCalendar;
import java.util.Vector;

import handler.MenuUtentiHandler;
import model.Fruitore;
import model.Fruitori;
import myLib.GestioneDate;
import myLib.MyMenu;
import view.FruitoriView;
import view.MessaggiSistemaView;

public class FruitoriController 
{
	private Fruitori model;
	
	public FruitoriController(Fruitori fruitori)
	{
		model = fruitori;
	}
	
	/**
	 * Crea e aggiunge un Fruitore, se maggiorenne al vettore "fruitori"
	 */
	public void addFruitore()
	{
		String nome = FruitoriView.chiediNome();
		String cognome = FruitoriView.chiediCognome();
		GregorianCalendar dataNascita = FruitoriView.chiediDataNascita();
		
		//controllo che l'utente sia maggiorenne
		if(GestioneDate.differenzaAnniDaOggi(dataNascita) < 18)
		{
			FruitoriView.messaggioUtenteMinorenne();
			return;
		}
		
		String user;
		do
		{
			user = FruitoriView.chiediUsername();
			if(!model.usernameDisponibile(user))
			{
				FruitoriView.UsernameNonDisponibile();
			}
		}
		while(!model.usernameDisponibile(user));
				
		String password1;
		String password2;
		boolean corretta = false;
		do
		{
			password1 = FruitoriView.chiediPassword();
			password2 = FruitoriView.confermaPassword();
			
			if(password1.equals(password2)) 
			{
				corretta = true;
			}
			else
			{
				FruitoriView.passwordNonCoincidono();
			}
		}
		while(!corretta);
		
		GregorianCalendar dataIscrizione = GestioneDate.DATA_CORRENTE;

		if(FruitoriView.confermaDati())
		{
//			creo il nuovo fruitore
			Fruitore f = new Fruitore(nome, cognome, dataNascita, dataIscrizione, user, password1); 
//			aggiungo al vector fruitori il nuovo fruitore
			model.addFruitore(f);
			FruitoriView.confermaIscrizione();
		}
		else
		{
			FruitoriView.nonConfermaIscrizione();
		}
	}
	
	/**
	 * Controllo se sono passati 5 anni dala data di iscrizione. Se sono passati i 5 anni assegna al fruitore lo status di "decaduto"
	 * @return un vettore contenente gli utenti eliminati: verrà poi utilizzato per rimuovere i prestiti di questi utenti decaduti
	 */
	public Vector<Fruitore> controlloIscrizioni()
	{
		Vector<Fruitore>utentiRimossi = new Vector<>();
		int rimossi = 0;
		for(Fruitore fruitore : model.getFruitori()) 
		{
			if((!fruitore.isDecaduto()) && fruitore.getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)//se dataScadenza è precedente a oggi ritorna -1
			{
				fruitore.setDecaduto(true);
				utentiRimossi.add(fruitore);
				rimossi++;
			}
		}
		FruitoriView.utentiRimossi(rimossi);
		return utentiRimossi;
	}
	
	/**
	 * metodo per il test, così non stampa in console
	 * @param test se true non stampa in console
	 */
	public Vector<Fruitore> controlloIscrizioni(boolean test)
	{
		Vector<Fruitore>utentiRimossi = new Vector<>();
		int rimossi = 0;
		for(Fruitore fruitore : model.getFruitori()) 
		{
			if((!fruitore.isDecaduto()) && fruitore.getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)//se dataScadenza è precedente a oggi ritorna -1
			{
				fruitore.setDecaduto(true);
				utentiRimossi.add(fruitore);
				rimossi++;
			}
		}
		if(!test)
		{
			FruitoriView.utentiRimossi(rimossi);
		}
		return utentiRimossi;
	}
	
	public void stampaDatiFruitori()
	{
		FruitoriView.stampaDati(model.getFruitori());
	}
	
	public void stampaDatiFruitore(Fruitore f)
	{
		FruitoriView.stampaDati(f);
	}
	
	public void rinnovo(Fruitore fruitore)
	{
		boolean riuscito = fruitore.rinnovo();
		if(riuscito)
		{
			FruitoriView.iscrizioneRinnovata();
		}
		else
		{
			FruitoriView.iscrizioneNonRinnovata(fruitore.getDataInizioRinnovo(), fruitore.getDataScadenza());
		}
	}

	/**
	 * interagisce con l'utente chiedendogli le credenziali.
	 * @return true se il login è andato a buon fine
	 */
	public Fruitore login() 
	{
		String user = FruitoriView.chiediUsername();
		String password = FruitoriView.chiediPassword();
		
		Fruitore utenteLoggato = model.trovaUtente(user, password);
		
		if(utenteLoggato == null)
		{
			FruitoriView.utenteNonTrovato();
			return null;
		}
		else // -> utente trovato
		{
			FruitoriView.benvenuto(utenteLoggato.getNome());
			return utenteLoggato;
		}	
	}

	

	/**
	 * menu che compare una volta che si esegue l'accesso come operatore
	 * @param scelta la scelta selezionata dall'utente
	 */
	public void menuOperatore(StoricoController storicoController, ArchivioController archivioController, PrestitiController prestitiController) 
	{
		final String[] MENU_OPERATORE_SCELTE = {"Visualizza fruitori","Aggiungi una risorsa","Rimuovi una risorsa","Visualizza l'elenco delle risorse",
				"Cerca una risorsa", "Visualizza tutti i prestiti attivi","Visualizza storico"};
		final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
		
		MessaggiSistemaView.accessoEseguito();
		
		MyMenu menuOperatore = new MyMenu(MENU_INTESTAZIONE, MENU_OPERATORE_SCELTE, true);
		boolean terminato;
		int scelta;
		do
		{
			scelta = menuOperatore.scegli();
			terminato = MenuUtentiHandler.menuOperatore(scelta, storicoController, archivioController, this, prestitiController);
		}
		while(!terminato);
	}

	/**
	 * menu che compare dopo che un fruitore esegue il login
	 * @param scelta la scelta selezionata dall'utente
	 */
	public void menuFruitore(Fruitore utenteLoggato, ArchivioController archivioController, PrestitiController prestitiController) 
	{
		final String MENU_INTESTAZIONE="Scegli l'opzione desiderata:";
		final String[] MENU_PERSONALE_SCELTE = {"Rinnova iscrizione", "Visualizza informazioni personali", "Cerca una risorsa",
															"Richiedi un prestito", "Rinnova un prestito", "Visualizza prestiti in corso", "Termina prestiti"};
		
		MyMenu menuPersonale=new MyMenu(MENU_INTESTAZIONE, MENU_PERSONALE_SCELTE, true);
		
		int scelta;
		boolean terminato;
		do
		{
			scelta = menuPersonale.scegli();
			terminato = MenuUtentiHandler.menuFruitore(utenteLoggato, scelta, archivioController, this, prestitiController);
		}
		while(!terminato);
	}
}
