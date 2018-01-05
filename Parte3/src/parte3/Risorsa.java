package parte3;

public abstract class Risorsa 
{
	public abstract void stampaDati(boolean perPrestito);
	public abstract String getNome();
	public abstract int getId();	
	public abstract int getInPrestito();
	public abstract int getGiorniDurataPrestito();
	public abstract int getGiorniDurataProroga();
	public abstract int getGiorniPrimaPerProroga();
	public abstract int getPrestitiMax();
//	specificano cosa succede quanto una risorsa viene mandata in prestito/torna dal prestito (es. modificare il numero di copie in prestito)
	public abstract void mandaInPrestito();
	public abstract void tornaDalPrestito();
}
