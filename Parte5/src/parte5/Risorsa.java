package parte5;

public abstract class Risorsa 
{
	/**
	 * Override del metodo equals di Object: in Object il metodo confronta gli oggetti tramite hashcode, ma salvando e caricando da file esso cambia e il programma
	 * non li riconosce più come stesso oggetto (in prestiti).
	 * @param r la risorsa con cui confrontare l'oggetto
	 * @return true se le due risorse hanno lo stesso id
	 */
	public abstract boolean equals(Risorsa r);
	public abstract void stampaDati(boolean perPrestito);
	public abstract String getTitolo();
	public abstract String getId();	
	public abstract int getInPrestito();
	public abstract boolean isPrestabile();
	public abstract void setPrestabile(boolean prestabile);
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
