package testing;

import static org.junit.Assert.assertEquals;
import java.util.Vector;
import org.junit.Test;
import controller.LibriController;
import model.Libri;
import model.Libro;

public class TestLibri
{
	Libri libri = new Libri();
	LibriController libriController = new LibriController(libri);
	
	@Test
	public void libroEsistente()
	{
		Libro libro = creaLibro("titolo", "Romanzo");
		libri.addLibro(libro);
		boolean esistente = libri.libroEsistente(libro);
		assertEquals(true, esistente);
	}
	
	@Test
//	aggiunge un libro non presente
	public void libroAggiunto() 
	{
		boolean riuscito = libri.addLibro(creaLibro("titolo", "Romanzo"));
		assertEquals(true, riuscito);
		assertEquals(1, libri.getLibri().size());
	}

	@Test
//	aggiunge un libro già presente
	public void libroNonAggiunto() 
	{
		libri.addLibro(creaLibro("titolo", "Romanzo"));
		boolean riuscito = libri.addLibro(creaLibro("titolo", "Romanzo"));
		assertEquals(false, riuscito);
		assertEquals(1, libri.getLibri().size());
	}
	
	@Test
	public void aggiungiLibroPerSottoCategoria()
	{
		libri.addLibro(creaLibro("titolo1", "Romanzo"));
		libri.addLibro(creaLibro("titolo1", "Fumetto"));
		libri.addLibro(creaLibro("titolo2", "Romanzo"));
		libri.addLibro(creaLibro("titolo2", "Fumetto"));
		
		StringBuilder sb = new StringBuilder();
		for(Libro libro : libri.getLibri())
		{
			sb.append(libro.getSottoCategoria() + "-");
		}
		
		assertEquals("Romanzo-Romanzo-Fumetto-Fumetto-", sb.toString());
	}
	
	@Test
	public void dimensione()
	{
		libri.addLibro(creaLibro("titolo", "Azione"));
		libri.addLibro(creaLibro("titolo1", "Azione"));
		libri.addLibro(creaLibro("titolo2", "Azione"));
		
		assertEquals(3, libri.getLibri().size());
		
		libri.addLibro(creaLibro("titolo", "Azione"));
		libri.addLibro(creaLibro("titolo1", "Azione"));
		libri.addLibro(creaLibro("titolo2", "Azione"));
			
		assertEquals(3, libri.getLibri().size());
	}
	
	@Test
	public void creaEAggiungiLibro()
	{
		Vector<String>autori = new Vector<>();
		autori.add("autore");
		libriController.addLibro("Romanzo", "titolo", autori, 10, 200, "casa", "italiano", "avventura", 10);
		
		assertEquals(1, libri.getLibri().size());
	}
	
	@Test
	public void rimuoviLibro()
	{
		libri.addLibro(creaLibro("titolo", "Romanzo"));
		libriController.removeLibro("titolo");
		
		assertEquals(false, libri.getLibri().get(0).isPrestabile());
	}
	
	@Test
	public void lastId()
	{
		Vector<String>autori = new Vector<>();
		autori.add("autore");
		libriController.addLibro("Romanzo", "titolo", autori, 10, 200, "casa", "italiano", "avventura", 10);
		libriController.addLibro("Fumetto", "fumetto", autori, 10, 200, "casa", "italiano", "avventura", 10);

		assertEquals(libri.getLibri().get(0).getId(), "L0");
		assertEquals(libri.getLibri().get(1).getId(), "L1");

		assertEquals(2, libriController.getLastId());
	}
	
	public static Libro creaLibro(String titolo, String sottoCategoria)
	{	
		Vector<String> autori = new Vector<>();
		autori.add("autore");
		return new Libro("L0", sottoCategoria, titolo, autori, 100, 100, "casa", "italiano", "Fantasy", 10);
	}
}
