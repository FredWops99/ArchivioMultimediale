package menus.risorse;

import model.Films;
import model.Libri;
import myLib.MyMenu;

public class MenuRisorsePrestabili 
{
	public static void show(String[] CATEGORIE, Libri libri, Films films)
	{
		MyMenu menu = new MyMenu("scegli la categoria: ", CATEGORIE, true);
		
		try
		{
			String categoria = CATEGORIE[menu.scegliBase() - 1];	//stampa il menu (partendo da 1 e non da 0) con i generi e ritorna quello selezionato
			if(categoria == CATEGORIE[0])//LIBRI
			{
				libri.stampaLibri();
			}
			if(categoria == CATEGORIE[1])//FILMS
			{
				films.stampaFilms();
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
//			se utente seleziona 0 (INDIETRO) -> CATEGORIE[-1] dà eccezione
//			corrisponde ad ANNULLA, non va fatto nulla
		}
	}
}
