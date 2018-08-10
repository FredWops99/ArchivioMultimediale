package view;

import java.util.Vector;

import model.Fruitore;

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
public class FruitoriView 
{
	public void stampaDati(Vector<Fruitore> fruitori)
	{
		
		System.out.println("Numero fruitori: " + fruitori.size());
		for(Fruitore fruitore : fruitori)
		{
			if(!fruitore.isDecaduto())
			{
				fruitore.stampaDati();
			}
		}
	}
}
