package testing;

import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import org.junit.Test;
import model.Film;
import model.Fruitore;
import model.Prestiti;
import model.Prestito;
import myLib.GestioneDate;

public class TestPrestiti 
{
	@Test
	public void scadenzaPrestito() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		
		Prestito prestito = new Prestito(fruitore, film);
		
		GregorianCalendar dataScadenza = GestioneDate.DATA_CORRENTE;
		dataScadenza.add(GregorianCalendar.DAY_OF_MONTH, 30);
		
		assertEquals(GestioneDate.visualizzaData(prestito.getDataScadenza()), GestioneDate.visualizzaData(dataScadenza));
	}
	
	@Test
	public void prestitoNonRinnovabile() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		
		Prestito prestito = new Prestito(fruitore, film);
		
		assertFalse(prestito.isRinnovabile());
	}
	
	@Test
	public void prestitoRinnovabile() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		
		Prestito prestito = new Prestito(fruitore, film, dataNascita);
		
		assertTrue(prestito.isRinnovabile());
	}
	
	@Test
	public void rinnovaPrestito() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		
		Prestito prestito = new Prestito(fruitore, film, dataNascita);
		
		prestito.prorogaPrestito();
		
		assertFalse(prestito.isRinnovabile());
		
		GregorianCalendar dataScadenza = GestioneDate.DATA_CORRENTE;
		dataScadenza.add(GregorianCalendar.DAY_OF_MONTH, 30);
		
		assertEquals(GestioneDate.visualizzaData(prestito.getDataScadenza()), GestioneDate.visualizzaData(dataScadenza));
	}
	
	@Test
	public void prestitoProrogato() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		
		Prestito prestito = new Prestito(fruitore, film, dataNascita);
		
		prestito.prorogaPrestito();
				
		assertTrue(prestito.isProrogato());
		assertFalse(prestito.isRinnovabile());
	}
	
	@Test
	public void terminaPrestito() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		
		Prestito prestito = new Prestito(fruitore, film);
		
		prestito.terminaPrestito();
		assertTrue(prestito.isTerminato());
	}
	
	@Test
	public void prestitiAttivi() 
	{
		Prestiti prestiti = new Prestiti();
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		
		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		Film film2 = TestFilm.creaFilm("Titolo2", "Avventura");
		
		
		prestiti.addPrestito(fruitore, film);
		prestiti.addPrestito(fruitore, film2);

		prestiti.getPrestiti().get(0).terminaPrestito();
		
		assertEquals(1, prestiti.prestitiAttivi().size());
	}
	
	@Test
	public void prestitiAttiviDi() 
	{
		Prestiti prestiti = new Prestiti();
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		Fruitore fruitore2 = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user2", "psw");

		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		Film film2 = TestFilm.creaFilm("Titolo2", "Avventura");
		
		
		prestiti.addPrestito(fruitore, film);
		prestiti.addPrestito(fruitore2, film2);

		prestiti.getPrestiti().get(0).terminaPrestito();
		
		assertEquals(0, prestiti.prestitiAttiviDi(fruitore).size());
		assertEquals(1, prestiti.prestitiAttiviDi(fruitore2).size());
	}
	
	@Test
	public void prestitoFattibile() 
	{
		Prestiti prestiti = new Prestiti();
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");

		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		
//		già presente
		prestiti.addPrestito(fruitore, film);

		assertFalse(prestiti.prestitoFattibile(fruitore, film));
	}
}
