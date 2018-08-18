package menus.risorse.films;

import myLib.MyMenu;

public class MenuSottoCategoriaFilm 
{
	private static final String SCEGLI_CATEGORIA = "scegli la sottocategoria del film: ";
	private static final String[] SOTTOCATEGORIE = {"Azione","Avventura","Fantascienza"}; //le sottocategorie della categoria FILM ("Azione","Avventura","Fantascienza"...)
	
	public static String show() 
	{
		MyMenu menu = new MyMenu(SCEGLI_CATEGORIA, SOTTOCATEGORIE, true);
		try
		{
			return SOTTOCATEGORIE[menu.scegliBase() - 1];
		}
//		se l'utente selezione 0: ANNULLA -> eccezione
		catch(ArrayIndexOutOfBoundsException e)
		{
			return "annulla";
		}	
	}
}
