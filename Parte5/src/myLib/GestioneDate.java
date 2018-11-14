package myLib;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GestioneDate 
{
	private static final String INSERISCI_ANNO = "Inserire l'anno: ";
	private static final String INSERISCI_MESE = "Inserire il mese: ";
	private static final String INSERISCI_GIORNO = "Inserire il giorno: ";
	private static final int MESE_GIORNO_MIN = 1;
	private static final int MESE_MAX = 12;
	private static final int GIORNO_MAX_31 = 31;
	private static final int GIORNO_MAX_30 = 30;
	private static final int GIORNO_MAX_29 = 29;
	private static final int GIORNO_MAX_28 = 28; 
	
	private static final int ORARIO_MIN = 0;
	private static final int SECONDI_MINUTI_MAX = 59;
	private static final int ORE_MAX = 23;
	
//	campi della data come interi
	public static final GregorianCalendar DATA_CORRENTE = (GregorianCalendar)GregorianCalendar.getInstance();
	public static final int ANNO_CORRENTE = GregorianCalendar.getInstance().get(Calendar.YEAR);
	public static final int MESE_CORRENTE = GregorianCalendar.getInstance().get(Calendar.MONTH) + 1;	//+1 perchè parte da 0
	public static final int GIORNO_CORRENTE = GregorianCalendar.getInstance().get(Calendar.DAY_OF_MONTH);
	public static final int	ORA_CORRENTE = GregorianCalendar.getInstance().get(Calendar.HOUR_OF_DAY);
	public static final int MINUTO_CORRENTE = GregorianCalendar.getInstance().get(Calendar.MINUTE);
	public static final int SECONDO_CORRENTE = GregorianCalendar.getInstance().get(Calendar.SECOND);
	
//	campi della data come stringhe (così c'è lo 0 davanti)
	public static String giorno_corrente = (GIORNO_CORRENTE < 10) ? "0"+GIORNO_CORRENTE : ""+GIORNO_CORRENTE;
	public static String mese_corrente = (MESE_CORRENTE < 10) ? "0"+MESE_CORRENTE : ""+MESE_CORRENTE;
	public static String ora_corrente = (ORA_CORRENTE < 10) ? "0"+ORA_CORRENTE : ""+ORA_CORRENTE;
	public static String minuto_corrente = (MINUTO_CORRENTE < 10) ? "0"+MINUTO_CORRENTE : ""+MINUTO_CORRENTE;	
	public static String secondo_corrente = (SECONDO_CORRENTE < 10) ? "0"+SECONDO_CORRENTE : ""+SECONDO_CORRENTE;
	
	private static final String INSERISCI_ORA = "Inserire l'ora: ";
	private static final String INSERISCI_MINUTO = "Inserire i minuti: ";
	private static final String INSERISCI_SECONDI = "Inserire i secondi: ";
		 

	public static boolean isBisestile(int anno)
	{
		boolean bisestile = (anno%400==0) || (anno%4==0 && anno%100!=0);
		return bisestile;
	}
	
	public static GregorianCalendar creaDataGuidata(int annoMin, int annoMax, boolean conOrario, boolean conSecondi)
	{
		GregorianCalendar data;
		
		int anno = InputDati.leggiIntero(INSERISCI_ANNO, annoMin, annoMax);
		int mese = InputDati.leggiIntero(INSERISCI_MESE, MESE_GIORNO_MIN, MESE_MAX);
		
		int giornoMax;
		
		if((mese == 1) || (mese == 3) || (mese == 5) || (mese == 7) || (mese == 8) || (mese == 10) || (mese == 12))
		{
			giornoMax = GIORNO_MAX_31;
		}
		else if(mese == 2)
		{
			if(isBisestile(anno))
			{
				giornoMax = GIORNO_MAX_29;
			}
			else
			{
				giornoMax = GIORNO_MAX_28;
			}
		}
		else
		{
			giornoMax = GIORNO_MAX_30;
		}
		int giorno = InputDati.leggiIntero(INSERISCI_GIORNO, MESE_GIORNO_MIN, giornoMax);
		
		int ora = 0;
		int minuti = 0;
		int secondi = 0;		
		
		if(conOrario == true)
		{
			ora = InputDati.leggiIntero(INSERISCI_ORA, ORARIO_MIN, ORE_MAX);
			
			minuti = InputDati.leggiIntero(INSERISCI_MINUTO, ORARIO_MIN, SECONDI_MINUTI_MAX);	
			
			if(conSecondi == true)
			{
				secondi = InputDati.leggiIntero(INSERISCI_SECONDI, ORARIO_MIN, SECONDI_MINUTI_MAX);
			}
		}
		data = new GregorianCalendar(anno, mese -1 , giorno, ora, minuti, secondi);
		
		return data;		
	}
	
	public static GregorianCalendar creaDataGuidata(int annoMin, int annoMax)
	{
		GregorianCalendar data = creaDataGuidata(annoMin, annoMax, false, false);
		return data;
	}
	
	public static GregorianCalendar creaDataGuidata(String messaggioIniziale, int annoMin, int annoMax, boolean conOrario, boolean conSecondi)
	{
		System.out.println(messaggioIniziale);
		
		GregorianCalendar data = creaDataGuidata(annoMin, annoMax, conOrario, conSecondi);
		return data;
	}
	
	public static GregorianCalendar creaDataGuidata(String messaggioIniziale, int annoMin, int annoMax)
	{
		System.out.println(messaggioIniziale);
		
		GregorianCalendar data = creaDataGuidata(annoMin, annoMax);
		return data;
	}
	
	public static GregorianCalendar creaDataGuidataPassata(String messaggioIniziale, int annoMin, boolean conOrario, boolean conSecondi)
	{
		System.out.println(messaggioIniziale);
		
		int anno = InputDati.leggiIntero(INSERISCI_ANNO, annoMin, ANNO_CORRENTE);
		
		int meseMax;		
		if(anno == ANNO_CORRENTE)
		{
			meseMax = MESE_CORRENTE;
		}
		else
		{
			meseMax = MESE_MAX;
		}
		int mese = InputDati.leggiIntero(INSERISCI_MESE, MESE_GIORNO_MIN, meseMax);
		
		int giornoMax;
		
		if(anno == ANNO_CORRENTE && mese == MESE_CORRENTE)
		{
			giornoMax = GIORNO_CORRENTE;
		}
		else
		{
			if((mese == 1) || (mese == 3) || (mese == 5) || (mese == 7) || (mese == 8) || (mese == 10) || (mese == 12))
			{
				giornoMax = GIORNO_MAX_31;
			}
			else if(mese == 2)
			{
				if(isBisestile(anno))
				{
					giornoMax = GIORNO_MAX_29;
				}
				else
				{
					giornoMax = GIORNO_MAX_28;
				}
			}
			else
			{
				giornoMax = GIORNO_MAX_30;
			}
		}
		
		int giorno = InputDati.leggiIntero(INSERISCI_GIORNO, MESE_GIORNO_MIN, giornoMax);
		
		int ora = 0;
		int minuti = 0;
		int secondi = 0;
		
		if(conOrario == true)
		{
			int oreMax;
			int minutiMax;
			int secondiMax;
			
			if(anno == ANNO_CORRENTE && mese == MESE_CORRENTE && giorno == GIORNO_CORRENTE)
			{
				oreMax = ORA_CORRENTE;
			}
			else
			{
				oreMax = ORE_MAX;
			}
			
			ora = InputDati.leggiIntero(INSERISCI_ORA, ORARIO_MIN, oreMax);
			
			if(ora == ORA_CORRENTE)
			{
				minutiMax = MINUTO_CORRENTE;
			}
			else
			{
				minutiMax = SECONDI_MINUTI_MAX;
			}
			
			minuti = InputDati.leggiIntero(INSERISCI_MINUTO, ORARIO_MIN, minutiMax);
			
			if(conSecondi == true)
			{
				if(minuti == MINUTO_CORRENTE)
				{
					secondiMax = SECONDO_CORRENTE;
				}
				else
				{
					secondiMax = SECONDI_MINUTI_MAX;
				}
				
				secondi = InputDati.leggiIntero(INSERISCI_SECONDI, ORARIO_MIN, secondiMax);
			}
		}
		
		GregorianCalendar data = new GregorianCalendar(anno, mese -1 , giorno, ora, minuti, secondi);
		return data;
		
	}
	
	public static GregorianCalendar creaDataGuidataPassata(String messaggioIniziale, int annoMin)
	{
		GregorianCalendar data = creaDataGuidataPassata(messaggioIniziale, annoMin, false, false);
		return data;
	}
	
	
	public static GregorianCalendar creaDataGuidataConRange(String messaggioIniziale, GregorianCalendar dataMin, GregorianCalendar dataMax) //senza orario
	{
		System.out.println(messaggioIniziale);
		
		int anno = InputDati.leggiIntero(INSERISCI_ANNO, dataMin.get(Calendar.YEAR), dataMax.get(Calendar.YEAR));
		
		int meseMax;	
		int meseMin;
		if(anno == dataMin.get(Calendar.YEAR) && anno == dataMax.get(Calendar.YEAR))
		{
			meseMax = dataMax.get(Calendar.MONTH) + 1;
			meseMin = dataMin.get(Calendar.MONTH) + 1;
		}
		
		else if(anno == dataMax.get(Calendar.YEAR))
		{
			meseMax = dataMax.get(Calendar.MONTH) + 1;
			meseMin = MESE_GIORNO_MIN;
		}
		else if(anno == dataMin.get(Calendar.YEAR))
		{
			meseMin = dataMin.get(Calendar.MONTH) + 1;
			meseMax = MESE_MAX;
		}
		else
		{
			meseMax = MESE_MAX;
			meseMin = MESE_GIORNO_MIN;
		}
		int mese = InputDati.leggiIntero(INSERISCI_MESE, meseMin, meseMax);
		
		Integer giornoMax = null;
		Integer giornoMin = null;
		
		if(anno == ANNO_CORRENTE && mese == dataMax.get(Calendar.MONTH) + 1)
		{
			giornoMax = dataMax.get(Calendar.DAY_OF_MONTH);
			giornoMin = MESE_GIORNO_MIN;
			
		}
		else if(mese == dataMin.get(Calendar.MONTH) + 1)
		{
			giornoMin = dataMin.get(Calendar.DAY_OF_MONTH);
		}	
		
		if(giornoMax == null)
		{
			if((mese == 1) || (mese == 3) || (mese == 5) || (mese == 7) || (mese == 8) || (mese == 10) || (mese == 12))
			{
				giornoMax = GIORNO_MAX_31;
			}
			else if(mese == 2)
			{
				if(isBisestile(anno))
				{
					giornoMax = GIORNO_MAX_29;
				}
				else
				{
					giornoMax = GIORNO_MAX_28;
				}
			}
			else
			{
				giornoMax = GIORNO_MAX_30;
			}
		}
		
		if(giornoMin == null)
		{
			giornoMin = MESE_GIORNO_MIN;
		}
		
		int giorno = InputDati.leggiIntero(INSERISCI_GIORNO, giornoMin, giornoMax);
		
		GregorianCalendar data = new GregorianCalendar(anno, mese -1 , giorno);
		
		return data;
		
	}
	
	public static GregorianCalendar creaDataGuidataFutura(String messaggioIniziale, int annoMax, boolean conOrario, boolean conSecondi)
	{
		System.out.println(messaggioIniziale);
		
		int anno = InputDati.leggiIntero(INSERISCI_ANNO, ANNO_CORRENTE, annoMax);
		
		int meseMin;		
		if(anno == ANNO_CORRENTE)
		{
			meseMin = MESE_CORRENTE;
		}
		else
		{
			meseMin = MESE_GIORNO_MIN;
		}
		int mese = InputDati.leggiIntero(INSERISCI_MESE, meseMin, MESE_MAX);
		
		int giornoMin;
		int giornoMax;
		
		if(anno == ANNO_CORRENTE && mese == MESE_CORRENTE)
		{
			giornoMin = GIORNO_CORRENTE;
		}
		else
		{
			giornoMin = MESE_GIORNO_MIN;
		}
		
		if((mese == 1) || (mese == 3) || (mese == 5) || (mese == 7) || (mese == 8) || (mese == 10) || (mese == 12))
		{
			giornoMax = GIORNO_MAX_31;
		}
		else if(mese == 2)
		{
			if(isBisestile(anno))
			{
				giornoMax = GIORNO_MAX_29;
			}
			else
			{
				giornoMax = GIORNO_MAX_28;
			}
		}
		else
		{
			giornoMax = GIORNO_MAX_30;
		}
		
		int giorno = InputDati.leggiIntero(INSERISCI_GIORNO, giornoMin, giornoMax);
		
		int ore = 0;
		int minuti = 0;
		int secondi = 0;
		
		if(conOrario == true)
		{
			int oraMin;
			int minutoMin;
			int secondoMin;
			
			if(anno == ANNO_CORRENTE && mese == MESE_CORRENTE && giorno == GIORNO_CORRENTE)
			{
				oraMin = ORA_CORRENTE;
			}
			else
			{
				oraMin = ORARIO_MIN;
			}
			
			ore = InputDati.leggiIntero(INSERISCI_ORA, oraMin, ORE_MAX);
			
			if(ore == ORA_CORRENTE)
			{
				minutoMin = MINUTO_CORRENTE;
			}
			else
			{
				minutoMin = ORARIO_MIN;
			}
			
			minuti = InputDati.leggiIntero(INSERISCI_MINUTO, minutoMin, SECONDI_MINUTI_MAX);
			
			if(conSecondi == true)
			{
				if(minuti == MINUTO_CORRENTE)
				{
					secondoMin = SECONDO_CORRENTE;
				}
				else 
				{
					secondoMin = ORARIO_MIN;
				}
				
				secondi = InputDati.leggiIntero(INSERISCI_SECONDI, secondoMin, SECONDI_MINUTI_MAX);
			}
		}
		
		GregorianCalendar data = new GregorianCalendar(anno, mese, giorno, ore, minuti, secondi);
		return data;	
	}
	
	public static GregorianCalendar creaDataGuidataFutura(String messaggioIniziale, int annoMax)
	{
		GregorianCalendar data = creaDataGuidataFutura(messaggioIniziale, annoMax, false, false);
		return data;
	}
	
	public static String visualizzaData(GregorianCalendar data, boolean conOrario, boolean conSecondi)
	{
		String s = "";
		if(data.get(Calendar.DAY_OF_MONTH) < 10)
		{
			s = s + "0";
		}
		s = s + data.get(Calendar.DAY_OF_MONTH) + "-";
		
		if(data.get(Calendar.MONTH) + 1 < 10)
		{
			s = s + "0";
		}
		s = s + (data.get(Calendar.MONTH) + 1) +"-"+ data.get(Calendar.YEAR);		
		
		if(conOrario == true)
		{
			s = s + "  " + visualizzaOrario(data, conSecondi);
		}
		
		return s;
	}
	
	public static String visualizzaData(GregorianCalendar data)
	{
		String s = visualizzaData(data, false, false);
		return s;
	}
	
	public static String visualizzaOrario(GregorianCalendar data, boolean conSecondi)
	{
		String s = "";
		
		if(data.get(Calendar.HOUR_OF_DAY) < 10)
		{
			s = s + "0";
		}
		s = s +  data.get(Calendar.HOUR_OF_DAY) + ":";
		
		if(data.get(Calendar.MINUTE) < 10)
		{
			s = s + "0";
		}
		s = s + data.get(Calendar.MINUTE);
		
		if(conSecondi == true)
		{
			s = s + ":";
			
			if(data.get(Calendar.SECOND) < 10)
			{
				s = s + "0";
			}
			s = s + data.get(Calendar.SECOND);
		}
		
		return s;
	}
	
	public static int differenzaAnniDaOggi(GregorianCalendar date)
	{		
		
		int giornoCorrente = DATA_CORRENTE.get(GregorianCalendar.DAY_OF_MONTH);
		int meseCorrente = DATA_CORRENTE.get(GregorianCalendar.MONTH);
		int annoCorrente = DATA_CORRENTE.get(GregorianCalendar.YEAR);
		int giorno = date.get(GregorianCalendar.DAY_OF_MONTH);
		int mese = date.get(GregorianCalendar.MONTH);
		int anno = date.get(GregorianCalendar.YEAR);
		
		
		int data;
		if (mese < meseCorrente)
		{
			 data = annoCorrente - anno;
			 return data;
		}
		if (mese == meseCorrente && giorno < giornoCorrente)
		{
			 data = annoCorrente - anno;
			 return data;
		}
		else
		{
			 data = annoCorrente - anno;
			 data = data - 1;
			 return data;
		}
	}	
}
