package myLib;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu

*/
public class MyMenu
{
  final private static String CORNICE = BelleStringhe.CORNICE;
  final private static String VOCE_USCITA = "0\tEsci";
  final private static String VOCE_INDIETRO = "0\tIndietro";
  final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata:\n--> ";
  final private static String SEI_SICURO="Sei sicuro? S=sì N=no";
  final private static String SEI_SICURO_CARATTERI="SN";
  
  private String titolo;
  private String [] voci;
  private boolean indietro;

	
  public MyMenu (String titolo, String [] voci)
  {
	this.titolo = titolo;
	this.voci = voci;
	indietro = false;
  }
  
  public MyMenu (String titolo, String [] voci, boolean isSottoMenu)
  {
	  this.titolo = titolo;
	  this.voci = voci;
	  if(!isSottoMenu)
	  {
		  indietro = false;
	  }
	  else
	  {
		  indietro = true;
	  }
  }

  public int scegli()
  {
	  stampaMenu();
	  return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);	 
  }
  
  public int scegliBase()
  {
	  stampaMenuBase(true);
	  return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);	 
  }
  
  public int scegliNoIndietro()
  {
	  stampaMenuBase(false);
	  return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 1, voci.length);
  }
  
  public int scegliNoPrintMenu()
  {
	  return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);	 
  }
  
  public char seiSicuro()
  {
	  return InputDati.leggiUpperChar(SEI_SICURO, SEI_SICURO_CARATTERI);
  }
		
  public void stampaMenu()
  {
	System.out.println();
	System.out.println(CORNICE);
	System.out.println(titolo);
	System.out.println(CORNICE);
    for (int i=0; i<voci.length; i++)
    {
    	System.out.println( (i+1) + "\t" + voci[i]);
    }
    System.out.println();
	if(indietro)
	{
		System.out.println(VOCE_INDIETRO);
	}
	else
	{
		System.out.println(VOCE_USCITA);
	}
    System.out.println();
  }
  
  public void stampaMenuPlus (String cornice)
  {
	  System.out.println(cornice);
	  System.out.println(titolo);
	  System.out.println(cornice);
	  for (int i=0; i<voci.length; i++)
	  {
		  System.out.println( (i+1) + ")\t" + voci[i]);
	  }
	  System.out.println();
	  System.out.println(VOCE_USCITA);
	  System.out.println();
	}
  
  public void stampaMenuBase(boolean conIndietro)
  {
	  System.out.println(titolo);
	  for (int i=0; i<voci.length; i++)
	  {
		  System.out.println( (i+1) + ")\t" + voci[i]);
	  }
	  System.out.println();
	  if(conIndietro)
	  {
		  if(indietro)
			{
				System.out.println("0)\tIndietro");
			}
			else
			{
				System.out.println("0)\tEsci");
			}
		    System.out.println();
	  }
	  
	}
  
  public String toString()
  {
	  String stringa=CORNICE + "\n"+ titolo + "\n" + CORNICE+"\n";
	  for (int i=0; i<voci.length; i++)
	  {
		  stringa +=( (i+1) + "\t" + voci[i] + "\n");
	  }
	  stringa += VOCE_USCITA + "\n";
	  
	  return stringa;
  }
  
}