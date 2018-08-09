package myLib;

public class VerificaDati 
{
	public static boolean isNumeroTelefono(String numero)
	{
		if(numero.matches("^[0-9]{3,15}$")) 	//espressione regolare ($ fine)
		{
			return true;
		}
		else return false;
	}
	
	public static boolean isEMail(String eMail)
	{
		if(eMail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
		{
			return true;
		}
		else return false;
	}
	
	public static boolean isCodiceFiscale(String codice)
	{
		if(codice.toUpperCase().matches("^[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}$"))
		{
			return true;
		}
		else return false;				
	}
	
	public static boolean isGruppoSanguigno(String gruppoSanguigno)
	{
		if(gruppoSanguigno.toUpperCase().matches("^(A|B|AB|0)[+-]$"))
		{
			return true;
		}
		else return false;
	}

}
