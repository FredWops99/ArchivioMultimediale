package testing;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.FilmController;
import controller.LibriController;
import model.Film;
import model.Films;
import model.Libri;
import model.Libro;

public class TestStampaDati 
{
	private LibriController libriController = new LibriController(new Libri());
	private FilmController filmController = new FilmController(new Films());

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
		filmController.stampaDatiFilm(film, false);
		
		assertEquals(film.toString(false).trim(), outContent.toString().trim());
	}
	
	@Test
	public void filmAggiunto() 
	{
		Libro libro = TestLibri.creaLibro("titolo", "Azione");
		libriController.stampaDatiLibro(libro, false);
		
		assertEquals(libro.toString(false).trim(), outContent.toString().trim());
	}
	
	@Test
	public void libroAggiuntoPerPrestito() 
	{
		Film film = TestFilm.creaFilm("titolo", "Avventura");
		filmController.stampaDatiFilm(film, true);
		
		assertEquals(film.toString(true).trim(), outContent.toString().trim());
	}
	
	@Test
	public void filmAggiuntoPerPrestito() 
	{
		Libro libro = TestLibri.creaLibro("titolo", "Azione");
		libriController.stampaDatiLibro(libro, true);
		
		assertEquals(libro.toString(true).trim(), outContent.toString().trim());
	}
}
