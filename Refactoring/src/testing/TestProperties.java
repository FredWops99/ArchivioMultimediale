package testing;

import java.io.FileInputStream;
import java.io.IOException;
import viewInterfaces.IFruitoriView;


/**
 * testo come funziona la lettura di proprietà di sistema, per PROGETTAZIONE GUIDATA DAI DATI
 * @author Stefano Prandini
 * @author Federico Landi
 *
 */
public class TestProperties {

	public static void main(String[] args) 
	{
		try 
		{
			System.getProperties().load(new FileInputStream("config.properties"));
			System.out.println("ok");
			
//			nel file config.properties indico il FULLY-QUALIFIED NAME della classe (es. view.FruitoriView): è il file .class
			String className = System.getProperty("FruitoriView");
			System.out.println(className);
			
			IFruitoriView fruitoriView = (IFruitoriView)Class.forName(className).newInstance();
//			frase a caso per vedere se l'oggetto funziona
			fruitoriView.messaggioUtenteMinorenne();
		} 
		catch (IOException e) //file not found
		{
			e.printStackTrace();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) 
		{
			System.out.println("error");
			e.printStackTrace();
		} 
	}

}
