package testing;

import java.util.GregorianCalendar;
import org.junit.Test;
import exceptions.RaggiunteRisorseMaxException;
import exceptions.RisorsaGi‡Posseduta;
import model.Film;
import model.Fruitore;
import model.Prestiti;

public class TestEccezioni 
{
	@Test(expected = RisorsaGi‡Posseduta.class)
	public void risorsaGi‡Posseduta() throws RaggiunteRisorseMaxException, RisorsaGi‡Posseduta 
	{
		Prestiti prestiti = new Prestiti();
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");

		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		prestiti.addPrestito(fruitore, film);
//		gi‡ aggiunto: lancia eccezione
		prestiti.addPrestito(fruitore, film);
	}
	
	@Test(expected = RaggiunteRisorseMaxException.class)
	public void raggiunteRisorseMaxException() throws RaggiunteRisorseMaxException, RisorsaGi‡Posseduta
	{
		Prestiti prestiti = new Prestiti();
		GregorianCalendar dataNascita = new GregorianCalendar(2000, 0, 1);
		GregorianCalendar dataIscrizione = new GregorianCalendar(2020, 0, 1);
		Fruitore fruitore = new Fruitore("nome", "cognome", dataNascita, dataIscrizione, "user", "psw");

		Film film = TestFilm.creaFilm("Titolo", "Avventura");
		prestiti.addPrestito(fruitore, film);
		Film film1 = TestFilm.creaFilm("Titolo1", "Avventura");
		prestiti.addPrestito(fruitore, film1);
		Film film2 = TestFilm.creaFilm("Titolo2", "Avventura");
		prestiti.addPrestito(fruitore, film2);
		Film film3 = TestFilm.creaFilm("Titolo3", "Avventura");
		prestiti.addPrestito(fruitore, film3);
//		non posso prenotare questo libro perchË supero il limite: lancia eccezione
		Film film4 = TestFilm.creaFilm("Titolo4", "Avventura");
		prestiti.addPrestito(fruitore, film4);		
	}

}
