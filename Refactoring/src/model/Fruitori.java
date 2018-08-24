package model;

import java.io.Serializable;
import java.util.Vector;

/**
 * Classe rappresentante tutti i fruitori che hanno accesso all'archivio multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Fruitori implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Vector<Fruitore> fruitori;
	
	/**
	 * Costuttore della classe: inizializza l'attributo fruitori con un Vector vuoto
	 */
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
	
	public void addFruitore(Fruitore f)
	{
		fruitori.add(f);
	}	
	
	/**
	 * Controlla se lo username passato sia già in uso da un altro fruitore
	 * (precondizione: user != null)
	 * @param user lo username da verificare
	 * @return true se non è utilizzato da nessun altro (quindi è disponibile)
	 */
	public boolean usernameDisponibile(String user) 
	{
		for(Fruitore fruitore : fruitori)
		{
//			lo username non può essere nemmeno quello di un fruitore decaduto (sennò confusione con lo storico dei prestiti, username uguali)
			if(fruitore.getUser().equals(user))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Restituisce un fruitore prendendo in ingresso uno username e una password
	 * (precondizione: username != null && password)
	 * @param username lo username inserito dall'utente
	 * @param password la password inserita dall'utente
	 * @return Fruitore il fruitore corrispondente ai dati passati (null se non presente)
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
