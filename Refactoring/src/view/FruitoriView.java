package view;

import java.util.GregorianCalendar;
import java.util.Vector;
import model.Fruitore;
import myLib.GestioneDate;
import myLib.InputDati;

/**
 * Fa un po' da view e da controller?
 * il model chiede a questa classe, che gli restituisce il risultato che gli serve e stampa anche all'utente.
 * se per esempio l'input-dati restasse nel model, il controller sarebbe assieme ad esso(?)
 * @author Stefano
 *
 */

public class FruitoriView 
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
	public static void stampaDatiFruitore(Fruitore f) 
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
	public static void stampaDatiFruitore(Vector<Fruitore> fruitori)
	{
		
		System.out.println("Numero fruitori: " + fruitori.size());
		for(Fruitore fruitore : fruitori)
		{
			if(!fruitore.isDecaduto())
			{
				stampaDatiFruitore(fruitore);
			}
		}
	}

	public static String chiediNome() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il tuo nome: ");
	}

	public static String chiediCognome() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il tuo cognome: ");
	}

	public static GregorianCalendar chiediDataNascita() 
	{
		return GestioneDate.creaDataGuidataPassata("inserisci la tua data di nascita: ", 1900);
	}

	public static void messaggioUtenteMinorenne() 
	{
		System.out.println("Ci dispiace, per accedere devi essere maggiorenne");
	}

	public static String chiediUsername() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci il tuo username: ");
	}

	public static void UsernameNonDisponibile() 
	{
		System.out.println("Nome utente non disponibile!");		
	}

	public static String chiediPassword() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci la password: ");
	}

	public static String confermaPassword() 
	{
		return InputDati.leggiStringaNonVuota("Inserisci nuovamente la password: ");
	}

	public static void passwordNonCoincidono() 
	{
		System.out.println("Le due password non coincidono, riprova");
	}

	public static boolean confermaDati() 
	{
		return InputDati.yesOrNo("Confermi l'iscrizione con questi dati?");
	}

	public static void confermaIscrizione() 
	{
		System.out.println("Registrazione avvenuta con successo!");		
	}

	public static void nonConfermaIscrizione() 
	{
		System.out.println("Non hai confermato l'iscrizione");	
	}

	public static void utentiRimossi(int rimossi) 
	{
		System.out.println("\nIscrizioni scadute (utenti rimossi): " + rimossi);		
	}

	public static void iscrizioneRinnovata() 
	{
		System.out.println("la tua iscrizione è stata rinnovata");	
	}

	public static void iscrizioneNonRinnovata(GregorianCalendar dataInizioRinnovo, GregorianCalendar dataScadenza) 
	{
		System.out.println("Al momento la tua iscrizione non può essere rinnovata:");
		System.out.println("Potrai effettuare il rinnovo tra il " + GestioneDate.visualizzaData(dataInizioRinnovo) + 
				" e il " + GestioneDate.visualizzaData(dataScadenza));
	}

	
}
