package interfaces;

/**
 * classe astratta che rappresenta una generica risorsa dell'archivio multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public interface Risorsa 
{
	/**
	 * Override del metodo equals di Object: in Object il metodo confronta gli oggetti tramite hashcode, ma salvando e caricando da file esso cambia e il programma
	 * non li riconosce più come stesso oggetto (in prestiti). Confronto quindi gli id delle due risorse
	 * @param r la risorsa con cui confrontare l'oggetto
	 * @return true se le due risorse hanno lo stesso id
	 */
	public abstract boolean equals(Risorsa r);
	/**
	 * restituisce una stringa contenente le informazioni riguardanti la risorsa
	 * @param perPrestito indica se le informazioni vanno visualizzate nella schermata dei prestiti o no (vengono mostrate info diverse)
	 * @return
	 */
	public abstract String toString(boolean perPrestito);
	
	public abstract String getCategoria();
	
	/**
	 * @return il titolo della risorsa
	 */
	public abstract String getTitolo();
	
	public abstract String getSottoCategoria();
	
	/**
	 * @return l'id univoco della risorsa
	 */
	public abstract String getId();	
	/**
	 * @return il numero di copie della risorsa attualmente in prestito
	 */
	public abstract int getInPrestito();
	
	public abstract int getAnnoDiUscita();
	/**
	 * @return true se la risorsa è ancora disponibile al prestito
	 */
	public abstract boolean isPrestabile();
	/**
	 * @return quanti giorni la risorsa può stare in prestito
	 */
	public abstract void setPrestabile(boolean prestabile);
	public abstract int getGiorniDurataPrestito();
	/**
	 * @return di quanto viene prorogato il prestito della risorsa
	 */
	public abstract int getGiorniDurataProroga();
	/**
	 * @return quanti giorni prima della scadenza del prestito se ne può richiedere la proroga
	 */
	public abstract int getGiorniPrimaPerProroga();
	/**
	 * @return il numero massimo di risorse che un fruitore può chiedere in prestito, per ogni categoria
	 */
	public abstract int getPrestitiMax();
//	specificano cosa succede quanto una risorsa viene mandata in prestito/torna dal prestito (es. modificare il numero di copie in prestito)
	/**
	 * precondizione: ci sono copie della risorsa disponibili per il prestito
	 * aggiorna il numero di copie disponibili e in prestito della risorsa
	 */
	public abstract void mandaInPrestito();
	/**
	 * precondizione: ci sono copie della risorsa attualmente in prestito
	 * aggiorna il numero di copie disponibili e in prestito della risorsa
	 */
	public abstract void tornaDalPrestito();
	
	public abstract boolean stessiAttributi(Risorsa r);
	public abstract int getNLicenze();
}