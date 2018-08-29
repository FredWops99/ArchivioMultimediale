package testing;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import controller.FilmsController;
import model.Film;
import model.Films;

public class TestFilm 
{
	Films films = new Films();
	FilmsController filmsController = new FilmsController(films);
	
	@Test
	public void filmEsistente()
	{
		Film film = creaFilm("Titolo", "Avventura");
		films.addFilm(film);
		boolean esistente = films.filmEsistente(film);
		assertEquals(true, esistente);
	}
	
	@Test
//	aggiunge un film non presente
	public void filmAggiunto() 
	{
		boolean riuscito = films.addFilm(creaFilm("Titolo", "Avventura"));
		assertEquals(true, riuscito);
	}

	@Test
//	aggiunge un film già presente
	public void filmNonAggiunto() 
	{
		films.addFilm(creaFilm("Titolo", "Avventura"));
		boolean riuscito = films.addFilm(creaFilm("Titolo", "Avventura"));
		assertEquals(false, riuscito);
	}
	
	@Test
	public void aggiungiFilmPerSottoCategoria()
	{
		films.addFilm(creaFilm("Titolo1", "Avventura"));
		films.addFilm(creaFilm("Titolo1", "Azione"));
		films.addFilm(creaFilm("Titolo2", "Avventura"));
		films.addFilm(creaFilm("Titolo2", "Azione"));

		
		StringBuilder sb = new StringBuilder();
		for(Film film : films.getFilms())
		{
			sb.append(film.getSottoCategoria() + "-");
		}
		
		assertEquals("Avventura-Avventura-Azione-Azione-", sb.toString());
	}
	
	@Test
	public void dimensione()
	{
		films.addFilm(creaFilm("titolo", "Avventura"));
		films.addFilm(creaFilm("titolo1", "Avventura"));
		films.addFilm(creaFilm("titolo2", "Avventura"));

		assertEquals(3, films.getFilms().size());
		
		films.addFilm(creaFilm("titolo", "Avventura"));
		films.addFilm(creaFilm("titolo1", "Avventura"));
		films.addFilm(creaFilm("titolo2", "Avventura"));
		
		assertEquals(3, films.getFilms().size());
	}
	
	@Test
	public void creaEAggiungiFilm()
	{
		filmsController.addFilm("Azione", "titolo", "regista", 100, 2000, "italiano", 20);
		
		assertEquals(1, films.getFilms().size());
	}
	
	@Test
	public void rimuoviFilm()
	{
		films.addFilm(creaFilm("titolo", "Fantasy"));
		filmsController.removeFilm("titolo");
		
		assertEquals(false, films.getFilms().get(0).isPrestabile());
	}
	
	@Test
	public void lastId()
	{
		filmsController.addFilm("Azione", "titolo", "regista", 100, 2000, "italiano", 20);
		filmsController.addFilm("Fantasy", "titolo", "regista", 100, 2000, "italiano", 20);

		assertEquals(films.getFilms().get(0).getId(), "F0");
		assertEquals(films.getFilms().get(1).getId(), "F1");

		assertEquals(2, filmsController.getLastId());
	}
	
	public static Film creaFilm(String titolo, String sottoCategoria)
	{
		return new Film("F0", sottoCategoria, titolo, "regista", 120, 1900, "italiano", 10);
	}
}
