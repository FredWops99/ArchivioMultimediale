package testing;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import viewInterfaces.IFruitoriView;


/**
 * testo come funziona la lettura di proprietà di sistema, per PROGETTAZIONE GUIDATA DAI DATI
 * @author Stefano Prandini
 * @author Federico Landi
 *
 */
public class TestProperties 
{
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Before
	public void setUpStreams() 
	{
	    System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void restoreStreams() 
	{
	    System.setOut(originalOut);
	}
	
	@Test
	public void properties()
	{
		try 
		{
			System.getProperties().load(new FileInputStream("config.properties"));
			
//			nel file config.properties indico il FULLY-QUALIFIED NAME della classe (es. view.FruitoriView): è il file .class
			String className = System.getProperty("FruitoriView");
//			System.out.println(className);
			
			IFruitoriView fruitoriView = (IFruitoriView)Class.forName(className).newInstance();
//			frase a caso per vedere se l'oggetto funziona
			fruitoriView.messaggioUtenteMinorenne();
			
//			controllo che l'output sia quello atteso
			assertEquals("Ci dispiace, per accedere devi essere maggiorenne", outContent.toString().trim());
			
			
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
