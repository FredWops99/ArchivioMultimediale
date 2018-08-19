package menus.risorse.libri;

import myLib.MyMenu;

public class MenuScegliGenereLibro 
{
	private static final String[] GENERI = {"Fantascienza","Fantasy","Avventura","Horror","Giallo"};
	
	public static String show(String sottoCategoria)
	{
		if(sottoCategoria.equals("Romanzo") || sottoCategoria.equals("Fumetto")) //se si aggiunge un genere va aggiunto anche qui !
		{
			MyMenu menu = new MyMenu("scegli un genere: ", GENERI);
			return GENERI[menu.scegliNoIndietro() - 1];	
		}
		else
		{
			return "-";
		}
	}
}
