package testing;

import static org.junit.Assert.assertEquals;
import java.util.Vector;
import org.junit.Test;
import controllerMVC.LibriController;
import controllerMVC.RisorseController;
import handler.ManageRisorseHandler;
import model.Libro;
import model.Risorsa;
import model.Risorse;

public class TestLibri
{
	Risorse risorse = new Risorse();
	RisorseController risorseController = new RisorseController(risorse);
	ManageRisorseHandler mr = new ManageRisorseHandler(risorseController);
	LibriController libriController = new LibriController(risorse,mr);
	
	@Test
	public void libroEsistente()
	{
		Libro libro = creaLibro("titolo", "Romanzo");
		risorse.addRisorsa(libro);
		boolean esistente = risorse.risorsaEsistente(libro);
		assertEquals(true, esistente);
	}
	
	@Test
//	aggiunge un libro non presente
	public void libroAggiunto() 
	{
		boolean riuscito = risorse.addRisorsa(creaLibro("titolo", "Romanzo"));
		assertEquals(true, riuscito);
		assertEquals(1, risorse.getRisorse().size());
	}

	@Test
//	aggiunge un libro già presente
	public void libroNonAggiunto() 
	{
		risorse.addRisorsa(creaLibro("titolo", "Romanzo"));
		boolean riuscito = risorse.addRisorsa(creaLibro("titolo", "Romanzo"));
		assertEquals(false, riuscito);
		assertEquals(1, risorse.getRisorse().size());
	}
	
	@Test
	public void aggiungiLibroPerSottoCategoria()
	{
		risorse.addRisorsa(creaLibro("titolo1", "Romanzo"));
		risorse.addRisorsa(creaLibro("titolo1", "Fumetto"));
		risorse.addRisorsa(creaLibro("titolo2", "Romanzo"));
		risorse.addRisorsa(creaLibro("titolo2", "Fumetto"));
		
		StringBuilder sb = new StringBuilder();
		for(Risorsa libro : risorse.getRisorse())
		{
			sb.append(libro.getSottoCategoria() + "-");
		}
		
		assertEquals("Romanzo-Romanzo-Fumetto-Fumetto-", sb.toString());
	}
	
	@Test
	public void dimensione()
	{
		risorse.addRisorsa(creaLibro("titolo", "Azione"));
		risorse.addRisorsa(creaLibro("titolo1", "Azione"));
		risorse.addRisorsa(creaLibro("titolo2", "Azione"));
		
		assertEquals(3, risorse.getRisorse().size());
		
		risorse.addRisorsa(creaLibro("titolo", "Azione"));
		risorse.addRisorsa(creaLibro("titolo1", "Azione"));
		risorse.addRisorsa(creaLibro("titolo2", "Azione"));
			
		assertEquals(3, risorse.getRisorse().size());
	}
	
	@Test
	public void creaEAggiungiLibro()
	{
		Vector<String>autori = new Vector<>();
		autori.add("autore");
		libriController.addLibro("Romanzo", "titolo", autori, 10, 200, "casa", "italiano", "avventura", 10);
		
		assertEquals(1, risorse.getRisorse().size());
	}
	
	@Test
	public void lastId()
	{
		Vector<String>autori = new Vector<>();
		autori.add("autore");
		libriController.addLibro("Romanzo", "titolo", autori, 10, 200, "casa", "italiano", "avventura", 10);
		libriController.addLibro("Fumetto", "fumetto", autori, 10, 200, "casa", "italiano", "avventura", 10);

		assertEquals(risorse.getRisorse().get(0).getId(), "L0");
		assertEquals(risorse.getRisorse().get(1).getId(), "L1");

		assertEquals(2, libriController.getLastId());
	}
	
	public static Libro creaLibro(String titolo, String sottoCategoria)
	{	
		Vector<String> autori = new Vector<>();
		autori.add("autore");
		return new Libro("L0", sottoCategoria, titolo, autori, 100, 100, "casa", "italiano", "Fantasy", 10);
	}
}
