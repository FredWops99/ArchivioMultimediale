package testing;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import controllerMVC.FilmsController;
import controllerMVC.RisorseController;
import handler.ManageRisorseHandler;
import interfaces.Risorsa;
import model.Film;
import model.Risorse;

public class TestFilm 
{
	Risorse risorse = new Risorse();
	RisorseController risorseController = new RisorseController(risorse);
	ManageRisorseHandler mr = new ManageRisorseHandler(risorseController);
	FilmsController filmsController = new FilmsController(risorse,mr);
	
	@Test
	public void filmEsistente()
	{
		Film film = creaFilm("Titolo", "Avventura");
		risorse.addRisorsa(film);
		boolean esistente = risorse.risorsaEsistente(film);
		assertEquals(true, esistente);
	}
	
	@Test
//	aggiunge un film non presente
	public void filmAggiunto() 
	{
		boolean riuscito = risorse.addRisorsa(creaFilm("Titolo", "Avventura"));
		assertEquals(true, riuscito);
	}

	@Test
//	aggiunge un film già presente
	public void filmNonAggiunto() 
	{
		risorse.addRisorsa(creaFilm("Titolo", "Avventura"));
		boolean riuscito = risorse.addRisorsa(creaFilm("Titolo", "Avventura"));
		assertEquals(false, riuscito);
	}
	
	@Test
	public void aggiungiFilmPerSottoCategoria()
	{
		risorse.addRisorsa(creaFilm("Titolo1", "Avventura"));
		risorse.addRisorsa(creaFilm("Titolo1", "Azione"));
		risorse.addRisorsa(creaFilm("Titolo2", "Avventura"));
		risorse.addRisorsa(creaFilm("Titolo2", "Azione"));

		
		StringBuilder sb = new StringBuilder();
		for(Risorsa film : risorse.getRisorse())
		{
			sb.append(film.getSottoCategoria() + "-");
		}
		
		assertEquals("Avventura-Avventura-Azione-Azione-", sb.toString());
	}
	
	@Test
	public void dimensione()
	{
		risorse.addRisorsa(creaFilm("titolo", "Avventura"));
		risorse.addRisorsa(creaFilm("titolo1", "Avventura"));
		risorse.addRisorsa(creaFilm("titolo2", "Avventura"));

		assertEquals(3, risorse.getRisorse().size());
		
		risorse.addRisorsa(creaFilm("titolo", "Avventura"));
		risorse.addRisorsa(creaFilm("titolo1", "Avventura"));
		risorse.addRisorsa(creaFilm("titolo2", "Avventura"));
		
		assertEquals(3, risorse.getRisorse().size());
	}
	
	@Test
	public void creaEAggiungiFilm()
	{
		filmsController.addFilm("Azione", "titolo", "regista", 100, 2000, "italiano", 20);
		
		assertEquals(1, risorse.getRisorse().size());
	}
	
	@Test
	public void lastId()
	{
		filmsController.addFilm("Azione", "titolo", "regista", 100, 2000, "italiano", 20);
		filmsController.addFilm("Fantasy", "titolo", "regista", 100, 2000, "italiano", 20);

		assertEquals(risorse.getRisorse().get(0).getId(), "F0");
		assertEquals(risorse.getRisorse().get(1).getId(), "F1");

		assertEquals(2, filmsController.getLastId());
	}
	
	public static Film creaFilm(String titolo, String sottoCategoria)
	{
		return new Film(titolo, sottoCategoria, titolo, "regista", 120, 1900, "italiano", 10);
	}
}
