package parte2;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;
import myLib.GestioneDate;
import myLib.InputDati;

public class Fruitori implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Vector<Fruitore> fruitori;
	
	
	public Fruitori()
	{
		this.fruitori = new Vector<Fruitore>();
	}	

	public Vector<Fruitore> getFruitori() 
	{
		return fruitori;
	}
	public void setFruitori(Vector<Fruitore> fruitori) 
	{
		this.fruitori = fruitori;
	}

	/**
	 * Aggiunge un Fruitore nel vettore "fruitori"
	 */
	public void addFruitore()
	{
		String nome = InputDati.leggiStringaNonVuota("Inserisci il tuo nome: ");
		String cognome = InputDati.leggiStringaNonVuota("Inserisci il tuo cognome: ");
		GregorianCalendar dataNascita = GestioneDate.creaDataGuidataPassata("inserisci la tua data di nascita: ", 1900);
		
		//controllo che l'utente sia maggiorenne
		if(GestioneDate.differenzaAnniDaOggi(dataNascita) < 18)
		{
			System.out.println("Ci dispiace, per accedere devi essere maggiorenne");
			return;
		}
		
		String user = InputDati.leggiStringaNonVuota("Inserisci il tuo username: ");
		String password1;
		String password2;
		//crea Passowrd + controllo password
		boolean corretta = false;
		do
		{
			password1 = InputDati.leggiStringaNonVuota("Inserisci la password: ");
			password2 = InputDati.leggiStringaNonVuota("Inserisci nuovamente la password: ");
			
			if(password1.equals(password2)) 
			{
				corretta = true;
			}
			else
			{
				System.out.println("Le due password non coincidono, riprova");
			}
		}
		while(!corretta);
		
		GregorianCalendar dataIscrizione = GestioneDate.DATA_CORRENTE;
		//creo il nuovo fruitore
		Fruitore f = new Fruitore(nome, cognome, dataNascita, dataIscrizione, user, password1); 
		//aggiungo al vector fruitori il nuovo fruitore
		fruitori.add(f);
	}
	
	/**
	 * Stampa tutti i fruitori:
	 * 	- Nome
	 *	- Cognome
	 *	- Username
	 *	- SData di nascita
	 *	- Data di iscrizione
	 *	- Data scadenza iscrizione
	 *	- Rinnovo iscrizione dal
	 *	
	 */
	public void stampaFruitori()
	{
		System.out.println("Numero fruitori: " + fruitori.size());
		for(int i = 0; i<fruitori.size(); i++)
		{
			fruitori.get(i).stampaDati();
		}
	}
	
	/**
	 * Controllo se sono passati 5 anni dala data di iscrizione. Se sono passati
	 * i 5 anni elimina il fruitore dal vettore "fruitori"
	 */
	public void controlloIscrizioni()
	{
		for (int i=0; i<fruitori.size(); i++) 
		{
			if(fruitori.get(i).getDataScadenza().compareTo(GestioneDate.DATA_CORRENTE) < 0)	//se dataScadenza è precedente a oggi ritorna -1
			{
				fruitori.remove(i);
			}
		}
	}
	
	/**
	 * Restituisce un fruitore prendendo in ingresso un username e una password
	 * @return Fruitore
	 */
	public Fruitore trovaUtente(String username, String password)
	{
		for(int i = 0; i < fruitori.size(); i++) 
		{
			if(fruitori.get(i).getUser().equals(username) && fruitori.get(i).getPassword().equals(password))
			{
				return fruitori.get(i);
			}
		}
		return null;	//se non è presente
	}	
}
