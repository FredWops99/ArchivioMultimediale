package testing;

import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import org.junit.Test;

import controller.FruitoriController;
import model.Fruitore;
import model.Fruitori;

public class TestFruitori 
{
	@Test
	public void dataScandenza() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
//		5 anni dopo l'iscrizione
		GregorianCalendar dataScandenza = new GregorianCalendar(2025, 0, 1);
		assertEquals(dataScandenza, fruitore.getDataScadenza());
	}

	@Test
	public void dataRinnovo() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
//		10 giorni prima della scadenza
		GregorianCalendar dataInizioRinnovo = new GregorianCalendar(2024, 11, 22);
		assertEquals(dataInizioRinnovo, fruitore.getDataInizioRinnovo());
	}
	
	@Test
	public void fruitoreNonRinnovabile() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		assertEquals(false, fruitore.fruitoreRinnovabile());
	}
	
	@Test
	public void fruitoreRinnovabile() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		assertEquals(true, fruitore.fruitoreRinnovabile());
	}
	
	@Test
	public void rinnovoAvvenuto() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		assertEquals(true, fruitore.fruitoreRinnovabile());
		boolean rinnovo = fruitore.rinnovo();
		assertEquals(true, rinnovo);
	}
	
	@Test
	public void rinnovoAvvenuto2() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		assertEquals(true, fruitore.fruitoreRinnovabile());
		fruitore.rinnovo();
		assertEquals(false, fruitore.fruitoreRinnovabile());
	}
	
	@Test
	public void addFruitore() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		Fruitori fruitori = new Fruitori();
		fruitori.addFruitore(fruitore);
		assertEquals(1, fruitori.getFruitori().size());
	}
	
	@Test
	public void usernameDisponibile() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		Fruitori fruitori = new Fruitori();
		fruitori.addFruitore(fruitore);
		assertEquals(false, fruitori.usernameDisponibile("user"));
	}
	
	@Test
	public void trovaUtente() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		Fruitori fruitori = new Fruitori();
		fruitori.addFruitore(fruitore);
		assertEquals(fruitore, fruitori.trovaUtente("user", "psw"));
	}
	
	@Test
	public void controlloIscrizioni() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		Fruitori fruitori = new Fruitori();
		fruitori.addFruitore(fruitore);
		assertEquals(1, fruitori.getFruitori().size());
		FruitoriController fc = new FruitoriController(fruitori);
		fc.controlloIscrizioni(true);
		assertEquals(true, fruitori.trovaUtente("user", "psw").isDecaduto());

	}
	
}
