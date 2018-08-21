package testing;

import model.Fruitori;

public class Parametri {

	public static void main(String[] args) {

		Fruitori fruitori = new Fruitori();
		fruitori.addFruitore();
		fruitori.stampaDati();
		
		rimuoviFruitori(fruitori);
		
		fruitori.stampaDati();
	}

	private static void rimuoviFruitori(Fruitori fruitori) 
	{
		int size = fruitori.getFruitori().size();
		fruitori.getFruitori().remove(size - 1);
	}

}
