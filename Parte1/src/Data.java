import java.io.Serializable;
import java.util.Calendar;

// ho messo il gg e mm e aaaa come stringhe così che l'utente possa inserire sia 1/1/2000 sia che 01/01/2000

public class Data implements Serializable
{	
	private static final long serialVersionUID = 1L;
	private String giorno;
	private String mese;
	private String anno;

	public Data()
	{ // Costruttore 1
		this.giorno = "01";
		this.mese = "01";
		this.anno = "2000"; 
	}
	
	 // METODI //.....................................................................
	
	public int getGiorno()
	{
		return Integer.parseInt(this.giorno);
	}
	
	public int getMese()
	{
		return  Integer.parseInt(this.mese);
	}
	
	public String getMeseEsteso()
	{
		final String[] mese = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno",
								"Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
		
		int posizione = Integer.parseInt(this.mese); 
		return mese[posizione];
	}
	
	public int getAnno()
	{
		return  Integer.parseInt(this.anno);
	}
	
	public void setData(String gg, String mm, String aaaa)
	{
		
		this.giorno = gg;
		this.mese = mm;
		this.anno = aaaa; 
		
	}
	
	public String stampaData()
	{
		return this.getGiorno()+"/"+this.getMese()+"/"+this.getAnno();
	}
	public short età()
	{
		
		Calendar cal = Calendar.getInstance();
		short data;
		
		int giornoCorrente = cal.get(Calendar.DATE);
		int meseCorrente = cal.get(Calendar.MONTH);
		int annoCorrente = cal.get(Calendar.YEAR);
		int giornoNascita = this.getGiorno();
		int meseNascita = this.getMese();
		int annoNascita = this.getAnno();
		
		if (meseNascita < meseCorrente) {
			 data = (short) (annoCorrente - annoNascita);
			 return data;
		}
		if (meseNascita == meseCorrente && giornoNascita < giornoCorrente){
			 data = (short) (annoCorrente - annoNascita);
			 return data;
		}
		else{
			 data = (short) (annoCorrente - annoNascita);
			 data = (short) (data - 1);
			 return data;
		}
	}
	
	
	public Boolean controllo(String gg,String mm,String aaaa)
	{ // controlla che le date siano date esistenti
		
		int g = Integer.parseInt(gg); 
		int m = Integer.parseInt(mm); 
		int a = Integer.parseInt(aaaa); 
		
		if(g<=31 && g>= 1 && m<=12 && m>= 1 && a<=2100 && a>=1900)
		{					
			return true;
		}
		else
		{
			return false;
		}					
	}
	
	public static Boolean comparaDate(Data data1,Data data2)
	{	
		if (Integer.parseInt(data1.anno) >= Integer.parseInt(data2.anno) + 5) 
		{
			if (Integer.parseInt(data1.mese) >= Integer.parseInt(data2.mese)) 
			{
				if (Integer.parseInt(data1.giorno) > Integer.parseInt(data2.giorno)) 
				{
					return true;
				}
			}
		}
		return false;
	}
}