
import java.io.*;
import java.util.Vector;



public class ServizioFileVector implements Serializable{
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	private final static String MSG_NO_FILE = "ATTENZIONE: NON TROVO IL FILE ";
	private final static String MSG_NO_LETTURA = "ATTENZIONE: PROBLEMI CON LA LETTURA DEL FILE ";
	private final static String MSG_NO_SCRITTURA = "ATTENZIONE: PROBLEMI CON LA SCRITTURA DEL FILE ";
	private final static String MSG_NO_CHIUSURA ="ATTENZIONE: PROBLEMI CON LA CHIUSURA DEL FILE ";
		
		
	public static Vector caricaSingoloOggetto(File file){
	 
		Vector letto = null;
		ObjectInputStream ingresso = null;
		
		try
		{
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Vector lista = (Vector) ois.readObject();
			ois.close();
			
			return lista;
		}
		catch (FileNotFoundException excNotFound)
		{
			System.out.println(MSG_NO_FILE + file.getName() );
		}
		 catch (IOException excLettura)
		{
		 System.out.println(MSG_NO_LETTURA + file.getName() );
		}
		 catch (ClassNotFoundException excLettura)
		{
			System.out.println(MSG_NO_LETTURA + file.getName() );
		}
		finally
		{
			if (ingresso != null)
			{
				try 
				{
				  ingresso.close();
				}
				catch (IOException excChiusura)
				{
			 		System.out.println(MSG_NO_CHIUSURA + file.getName() );
				}
			}
		}
	
	 return letto;
}

	public static void salvaSingoloOggetto (File file, Vector daSalvare)
	{
		ObjectOutputStream uscita = null;
		
		 try
		{
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(file)); 
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(daSalvare);
			oos.close();
		}
		 catch (IOException excScrittura)
		{
		 System.out.println(MSG_NO_SCRITTURA + file.getName() );
		}
		 
		    finally
		    {
		    	if (uscita != null)
		    	{
					try 
					{
						uscita.close();
					}
					catch (IOException excChiusura)
					{
						System.out.println(MSG_NO_CHIUSURA + file.getName() );
					}
		    	}
			} 
	 }
}

