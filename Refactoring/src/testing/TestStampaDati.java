package testing;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import controllerMVC.FruitoriController;
import model.Film;
import model.Fruitore;
import model.Fruitori;
import model.Libro;
import model.Risorse;
import view.RisorseView;

public class TestStampaDati 
{
	Risorse risorse = new Risorse();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@Before
	public void setUpStreams() 
	{
	    System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void restoreStreams() 
	{
	    System.setOut(originalOut);
	}
	
	@Test
	public void libroAggiunto() 
	{
		Film film = TestFilm.creaFilm("titolo", "Avventura");
		RisorseView.stampaDati(film, false);
		
		assertEquals(film.toString(false).trim(), outContent.toString().trim());
	}
	
	@Test
	public void filmAggiunto() 
	{
		Libro libro = TestLibri.creaLibro("titolo", "Azione");
		RisorseView.stampaDati(libro, false);
		
		assertEquals(libro.toString(false).trim(), outContent.toString().trim());
	}
	
	@Test
	public void libroAggiuntoPerPrestito() 
	{
		Film film = TestFilm.creaFilm("titolo", "Avventura");
		RisorseView.stampaDati(film, true);
		
		assertEquals(film.toString(true).trim(), outContent.toString().trim());
	}
	
	@Test
	public void filmAggiuntoPerPrestito() 
	{
		Libro libro = TestLibri.creaLibro("titolo", "Azione");
		RisorseView.stampaDati(libro, true);
		
		assertEquals(libro.toString(true).trim(), outContent.toString().trim());
	}
	
	@Test
	public void stampaDatiFruitore() 
	{
		GregorianCalendar dataNascita = new GregorianCalendar(1900, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2000, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");
		FruitoriController fruitoriController = new FruitoriController(new Fruitori());
		
		fruitoriController.stampaDatiFruitore(fruitore);
		
		assertEquals(fruitore.toString().trim(), outContent.toString().trim());
	}

}
