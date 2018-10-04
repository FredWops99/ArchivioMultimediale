package testing;

import static org.junit.Assert.assertEquals;
import java.util.Vector;
import org.junit.Test;

import controllerMVC.LibriController;
import model.Libri;
import model.Libro;
import model.Risorsa;

public class TestLibri
{
	Libri libri = new Libri();
	LibriController libriController = new LibriController(libri);
	
	@Test
	public void libroEsistente()
	{
		Libro libro = creaLibro("titolo", "Romanzo");
		libri.addRisorsa(libro);
		boolean esistente = libri.risorsaEsistente(libro);
		assertEquals(true, esistente);
	}
	
	@Test
//	aggiunge un libro non presente
	public void libroAggiunto() 
	{
		boolean riuscito = libri.addRisorsa(creaLibro("titolo", "Romanzo"));
		assertEquals(true, riuscito);
		assertEquals(1, libri.getRisorse().size());
	}

	@Test
//	aggiunge un libro già presente
	public void libroNonAggiunto() 
	{
		libri.addRisorsa(creaLibro("titolo", "Romanzo"));
		boolean riuscito = libri.addRisorsa(creaLibro("titolo", "Romanzo"));
		assertEquals(false, riuscito);
		assertEquals(1, libri.getRisorse().size());
	}
	
	@Test
	public void aggiungiLibroPerSottoCategoria()
	{
		libri.addRisorsa(creaLibro("titolo1", "Romanzo"));
		libri.addRisorsa(creaLibro("titolo1", "Fumetto"));
		libri.addRisorsa(creaLibro("titolo2", "Romanzo"));
		libri.addRisorsa(creaLibro("titolo2", "Fumetto"));
		
		StringBuilder sb = new StringBuilder();
		for(Risorsa libro : libri.getRisorse())
		{
			sb.append(libro.getSottoCategoria() + "-");
		}
		
		assertEquals("Romanzo-Romanzo-Fumetto-Fumetto-", sb.toString());
	}
	
	@Test
	public void dimensione()
	{
		libri.addRisorsa(creaLibro("titolo", "Azione"));
		libri.addRisorsa(creaLibro("titolo1", "Azione"));
		libri.addRisorsa(creaLibro("titolo2", "Azione"));
		
		assertEquals(3, libri.getRisorse().size());
		
		libri.addRisorsa(creaLibro("titolo", "Azione"));
		libri.addRisorsa(creaLibro("titolo1", "Azione"));
		libri.addRisorsa(creaLibro("titolo2", "Azione"));
			
		assertEquals(3, libri.getRisorse().size());
	}
	
	@Test
	public void creaEAggiungiLibro()
	{
		Vector<String>autori = new Vector<>();
		autori.add("autore");
		libriController.addLibro("Romanzo", "titolo", autori, 10, 200, "casa", "italiano", "avventura", 10);
		
		assertEquals(1, libri.getRisorse().size());
	}
	
	@Test
	public void rimuoviLibro()
	{
		libri.addRisorsa(creaLibro("titolo", "Romanzo"));
		libriController.removeLibro("titolo");
		
		assertEquals(false, libri.getRisorse().get(0).isPrestabile());
	}
	
	@Test
	public void lastId()
	{
		Vector<String>autori = new Vector<>();
		autori.add("autore");
		libriController.addLibro("Romanzo", "titolo", autori, 10, 200, "casa", "italiano", "avventura", 10);
		libriController.addLibro("Fumetto", "fumetto", autori, 10, 200, "casa", "italiano", "avventura", 10);

		assertEquals(libri.getRisorse().get(0).getId(), "L0");
		assertEquals(libri.getRisorse().get(1).getId(), "L1");

		assertEquals(2, libriController.getLastId());
	}
	
	public static Libro creaLibro(String titolo, String sottoCategoria)
	{	
		Vector<String> autori = new Vector<>();
		autori.add("autore");
		return new Libro("L0", sottoCategoria, titolo, autori, 100, 100, "casa", "italiano", "Fantasy", 10);
	}
}
