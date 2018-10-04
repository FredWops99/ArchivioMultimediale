package testing;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import controllerMVC.FilmsController;
import model.Film;
import model.Films;
import model.Risorsa;

public class TestFilm 
{
	Films films = new Films();
	FilmsController filmsController = new FilmsController(films);
	
	@Test
	public void filmEsistente()
	{
		Film film = creaFilm("Titolo", "Avventura");
		films.addRisorsa(film);
		boolean esistente = films.risorsaEsistente(film);
		assertEquals(true, esistente);
	}
	
	@Test
//	aggiunge un film non presente
	public void filmAggiunto() 
	{
		boolean riuscito = films.addRisorsa(creaFilm("Titolo", "Avventura"));
		assertEquals(true, riuscito);
	}

	@Test
//	aggiunge un film già presente
	public void filmNonAggiunto() 
	{
		films.addRisorsa(creaFilm("Titolo", "Avventura"));
		boolean riuscito = films.addRisorsa(creaFilm("Titolo", "Avventura"));
		assertEquals(false, riuscito);
	}
	
	@Test
	public void aggiungiFilmPerSottoCategoria()
	{
		films.addRisorsa(creaFilm("Titolo1", "Avventura"));
		films.addRisorsa(creaFilm("Titolo1", "Azione"));
		films.addRisorsa(creaFilm("Titolo2", "Avventura"));
		films.addRisorsa(creaFilm("Titolo2", "Azione"));

		
		StringBuilder sb = new StringBuilder();
		for(Risorsa film : films.getRisorse())
		{
			sb.append(film.getSottoCategoria() + "-");
		}
		
		assertEquals("Avventura-Avventura-Azione-Azione-", sb.toString());
	}
	
	@Test
	public void dimensione()
	{
		films.addRisorsa(creaFilm("titolo", "Avventura"));
		films.addRisorsa(creaFilm("titolo1", "Avventura"));
		films.addRisorsa(creaFilm("titolo2", "Avventura"));

		assertEquals(3, films.getRisorse().size());
		
		films.addRisorsa(creaFilm("titolo", "Avventura"));
		films.addRisorsa(creaFilm("titolo1", "Avventura"));
		films.addRisorsa(creaFilm("titolo2", "Avventura"));
		
		assertEquals(3, films.getRisorse().size());
	}
	
	@Test
	public void creaEAggiungiFilm()
	{
		filmsController.addFilm("Azione", "titolo", "regista", 100, 2000, "italiano", 20);
		
		assertEquals(1, films.getRisorse().size());
	}
	
	@Test
	public void rimuoviFilm()
	{
		films.addRisorsa(creaFilm("titolo", "Fantasy"));
		filmsController.removeFilm("titolo");
		
		assertEquals(false, films.getRisorse().get(0).isPrestabile());
	}
	
	@Test
	public void lastId()
	{
		filmsController.addFilm("Azione", "titolo", "regista", 100, 2000, "italiano", 20);
		filmsController.addFilm("Fantasy", "titolo", "regista", 100, 2000, "italiano", 20);

		assertEquals(films.getRisorse().get(0).getId(), "F0");
		assertEquals(films.getRisorse().get(1).getId(), "F1");

		assertEquals(2, filmsController.getLastId());
	}
	
	public static Film creaFilm(String titolo, String sottoCategoria)
	{
		return new Film(titolo, sottoCategoria, titolo, "regista", 120, 1900, "italiano", 10);
	}
}
