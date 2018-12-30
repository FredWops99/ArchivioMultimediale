package testing;

import static org.junit.Assert.assertEquals;
import java.util.Vector;
import org.junit.Test;
import interfaces.Risorsa;
import model.Libro;
import model.Risorse;

public class TestLibri
{
	Risorse risorse = new Risorse();
	
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
		
		Libro l = new Libro("L0", "Romanzo", "titolo", autori, 10, 200, "casa", "italiano", "avventura", 10);
		
		risorse.addRisorsa(l);
		assertEquals(1, risorse.getRisorse().size());
	}
	
	public static Libro creaLibro(String titolo, String sottoCategoria)
	{	
		Vector<String> autori = new Vector<>();
		autori.add("autore");
		return new Libro("L0", sottoCategoria, titolo, autori, 100, 100, "casa", "italiano", "Fantasy", 10);
	}
}
