package testing;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.Film;
import model.Libro;
import view.FilmsView;
import view.LibriView;
import view.RisorseView;

/**
 * testcase creato per controllare se gli output su Console coincidono con quelli desiderati.
 * specialmente nei casi in cui viene passata una classe al metodo
 * per poter usare assertEquals con i println si usa un PrintStream che salvi la stringa che comparirebbe in console.
 * Prima si setta tale printStream (@After) e dopo si riassegna al System.out
 * @author Stefano Prandini
 * @author Federico Landi
 */
public class TestOutput 
{
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
	public void FilmAggiunto() 
	{
		RisorseView.aggiuntaRiuscita(Film.class);
		assertEquals("Film aggiunto con successo!", outContent.toString().trim());//trim toglie whitespaces iniziali/finali
	}
	
	@Test
	public void filmNonAggiunto()
	{
		RisorseView.aggiuntaNonRiuscita(Film.class);
		assertEquals("Il film è già presente in archivio", outContent.toString().trim());
	}
	
	@Test
	public void libroNonAggiunto()
	{
		RisorseView.aggiuntaNonRiuscita(Libro.class);
		assertEquals("Il libro è già presente in archivio", outContent.toString().trim());
	}

	@Test
	public void LibroAggiunto() 
	{
		RisorseView.aggiuntaRiuscita(Libro.class);
		assertEquals("Libro aggiunto con successo!", outContent.toString().trim());
	}
	
	@Test
	public void piùLibriStessoTitolo()
	{
		LibriView.piùRisorseStessoTitolo("Titolo");
		assertEquals("Sono presenti più libri dal titolo \"Titolo\":", outContent.toString().trim());
	}
	
	@Test
	public void piùFilmStessoTitolo()
	{
		FilmsView.piùRisorseStessoTitolo("Titolo");
		assertEquals("Sono presenti più films dal titolo \"Titolo\":", outContent.toString().trim());
	}
}
