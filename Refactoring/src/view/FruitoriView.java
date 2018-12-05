package view;

import java.util.GregorianCalendar;
import java.util.Vector;
import model.Fruitore;
import myLib.GestioneDate;
import myLib.InputDati;
import viewInterfaces.IFruitoriView;

/**
 * Fa un po' da view e da controller?
 * il model chiede a questa classe, che gli restituisce il risultato che gli serve e stampa anche all'utente.
 * se per esempio l'input-dati restasse nel model, il controller sarebbe assieme ad esso(?)
 * @author Stefano Prandini
 * @author Federico Landi
 */

public class FruitoriView implements IFruitoriView
{
	/**
	 * Stampa:
	 * 	- Nome
	 *	- Cognome
	 *	- Username
	 *	- Data di nascita
	 *	- Data di iscrizione
	 *	- Data scadenza iscrizione
	 *	- Rinnovo iscrizione dal
	 */
	public void stampaDati(Fruitore f) 
	{
		System.out.println(f.toString());
	}
	
	
	/**
	 * Stampa per ogni fruitore:
	 * 	- Nome
	 *	- Cognome
	 *	- Username
	 *	- Data di nascita
	 *	- Data di iscrizione
	 *	- Data scadenza iscrizione
	 *	- Rinnovo iscrizione dal
	 */
	public void stampaDati(Vector<Fruitore> fruitori)
	{
		
		System.out.println("Numero fruitori: " + fruitori.size());
		for(Fruitore fruitore : fruitori)
		{
			if(!fruitore.isDecaduto())
			{
				stampaDati(fruitore);
			}
		}
	}

	public String chiediNome() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il tuo nome: ");
	}

	public String chiediCognome() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il tuo cognome: ");
	}

	public GregorianCalendar chiediDataNascita() 
	{
		return GestioneDate.creaDataGuidataPassata("inserisci la tua data di nascita: ", 1900);
	}

	public void messaggioUtenteMinorenne() 
	{
		System.out.println("Ci dispiace, per accedere devi essere maggiorenne");
	}

	public String chiediUsername() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il tuo username: ");
	}

	public void UsernameNonDisponibile() 
	{
		System.out.println("Nome utente non disponibile!");		
	}

	public String chiediPassword() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci la password: ");
	}

	public String confermaPassword() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci nuovamente la password: ");
	}

	public void passwordNonCoincidono() 
	{
		System.out.println("Le due password non coincidono, riprova");
	}

	public Boolean confermaDati() 
	{
		return InputDati.yesOrNo("Confermi l'iscrizione con questi dati?");
	}

	public void confermaIscrizione() 
	{
		System.out.println("Registrazione avvenuta con successo!");		
	}

	public void nonConfermaIscrizione() 
	{
		System.out.println("Non hai confermato l'iscrizione");	
	}

	public void utentiRimossi(int rimossi) 
	{
		System.out.println("Iscrizioni scadute (utenti rimossi): " + rimossi);		
	}

	public void iscrizioneRinnovata() 
	{
		System.out.println("la tua iscrizione è stata rinnovata");	
	}

	public void iscrizioneNonRinnovata(GregorianCalendar dataInizioRinnovo, GregorianCalendar dataScadenza) 
	{
		System.out.println("Al momento la tua iscrizione non può essere rinnovata:");
		System.out.println("Potrai effettuare il rinnovo tra il " + GestioneDate.visualizzaData(dataInizioRinnovo) + 
				" e il " + GestioneDate.visualizzaData(dataScadenza));
	}

	public void passwordErrata() 
	{
		System.out.println("Password errata!");	
	}


	public void utenteNonTrovato() 
	{
		System.out.println("Utente non trovato! ");	
	}


	public void benvenuto(String nome) 
	{
		System.out.println("Benvenuto " + nome + "!");
	}
}