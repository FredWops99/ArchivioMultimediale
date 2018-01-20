package parte4;

public abstract class Risorsa 
{
	public abstract void stampaDati(boolean perPrestito);
	public abstract String getTitolo();
	public abstract String getId();	
	public abstract int getInPrestito();
	public abstract int getGiorniDurataPrestito();
	public abstract int getGiorniDurataProroga();
	public abstract int getGiorniPrimaPerProroga();
	public abstract int getPrestitiMax();
//	specificano cosa succede quanto una risorsa viene mandata in prestito/torna dal prestito (es. modificare il numero di copie in prestito)
	/**
	 * precondizione: ci sono copie della risorsa disponibili per il prestito
	 */
	public abstract void mandaInPrestito();
	/**
	 * precondizione: ci sono copie della risorsa attualmente in prestito
	 */
	public abstract void tornaDalPrestito();
}
