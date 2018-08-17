package menus.risorse.libri;

import myLib.MyMenu;

public class MenuSottoCategoriaLibri 
{
	private static final String[] SOTTOCATEGORIE = {"Romanzo","Fumetto","Poesia"}; //le sottocategorie della categoria LIBRO (Romanzo, fumetto, poesia,...)
	
	public static String show()
	{
		MyMenu menu = new MyMenu("scegli la sottocategoria del libro: ", SOTTOCATEGORIE, true);
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
