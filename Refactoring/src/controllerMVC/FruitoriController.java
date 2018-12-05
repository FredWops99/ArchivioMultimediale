package controllerMVC;

import java.util.GregorianCalendar;
import java.util.Vector;
import model.Fruitore;
import model.Fruitori;
import myLib.GestioneDate;
import view.FruitoriView;
import viewInterfaces.IFruitoriView;

public class FruitoriController 
{
	private Fruitori model;
	private IFruitoriView fruitoriView;
	
	public FruitoriController(Fruitori fruitori)
	{
		model = fruitori;
		fruitoriView = new FruitoriView();
	}
	
	public IFruitoriView getFruitoriView() 
	{
		return fruitoriView;
	}

	/**
	 * Crea e aggiunge un Fruitore, se maggiorenne al vettore "fruitori"
	 */
	public void addFruitore()
	{
		String nome = fruitoriView.chiediNome();
		String cognome = fruitoriView.chiediCognome();
		GregorianCalendar dataNascita = fruitoriView.chiediDataNascita();
		
		//controllo che l'utente sia maggiorenne
		if(GestioneDate.differenzaAnniDaOggi(dataNascita) < 18)
		{
			fruitoriView.messaggioUtenteMinorenne();
			return;
		}
		
		String user;
		do
		{
			user = fruitoriView.chiediUsername();
			if(!model.usernameDisponibile(user))
			{
				fruitoriView.UsernameNonDisponibile();
			}
		}
		while(!model.usernameDisponibile(user));
				
		String password1;
		String password2;
		boolean corretta = false;
		do
		{
			password1 = fruitoriView.chiediPassword();
			password2 = fruitoriView.confermaPassword();
			
			if(password1.equals(password2)) 
			{
				corretta = true;
			}
			else
			{
				fruitoriView.passwordNonCoincidono();
			}
		}
		while(!corretta);
		
		GregorianCalendar dataIscrizione = GestioneDate.DATA_CORRENTE;

		if(fruitoriView.confermaDati())
		{
//			creo il nuovo fruitore
			Fruitore f = new Fruitore(nome, cognome, dataNascita, dataIscrizione, user, password1); 
//			aggiungo al vector fruitori il nuovo fruitore
			model.addFruitore(f);
			fruitoriView.confermaIscrizione();
		}
		else
		{
			fruitoriView.nonConfermaIscrizione();
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
		fruitoriView.utentiRimossi(rimossi);
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
			fruitoriView.utentiRimossi(rimossi);
		}
		return utentiRimossi;
	}
	
	/**
	 * interagisce con l'utente chiedendogli le credenziali.
	 * @return true se il login è andato a buon fine
	 */
	public Fruitore login() 
	{
		String user = fruitoriView.chiediUsername();
		String password = fruitoriView.chiediPassword();
		
		Fruitore utenteLoggato = model.trovaUtente(user, password);
		
		if(utenteLoggato == null)
		{
			fruitoriView.utenteNonTrovato();
			return null;
		}
		else // -> utente trovato
		{
			fruitoriView.benvenuto(utenteLoggato.getNome());
			return utenteLoggato;
		}	
	}
	
	public void stampaDatiFruitori()
	{
		fruitoriView.stampaDati(model.getFruitori());
	}
	
	public void stampaDatiFruitore(Fruitore f)
	{
		fruitoriView.stampaDati(f);
	}
	
	public void rinnovo(Fruitore fruitore)
	{
		boolean riuscito = fruitore.rinnovo();
		if(riuscito)
		{
			fruitoriView.iscrizioneRinnovata();
		}
		else
		{
			fruitoriView.iscrizioneNonRinnovata(fruitore.getDataInizioRinnovo(), fruitore.getDataScadenza());
		}
	}	
}
