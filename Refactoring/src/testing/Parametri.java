package testing;

import controller.FilmController;
import model.Archivio;

public class Parametri {

	public static void main(String[] args) {

		Archivio archivio = new Archivio();
		FilmController fc = new FilmController(archivio.getFilms());
		fc.addFilm();
		FilmController fc2 = new FilmController(archivio.getFilms());
		fc2.addFilm();
		
		System.out.println(archivio.getFilms().getFilms().size());
	}

}
