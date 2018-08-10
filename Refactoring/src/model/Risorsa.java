package model;

/**
 * classe astratta che rappresenta una generica risorsa dell'archivio multimediale
 * @author Prandini Stefano
 * @author Landi Federico
 */
public abstract class Risorsa 
{
	/**
	 * Override del metodo equals di Object: in Object il metodo confronta gli oggetti tramite hashcode, ma salvando e caricando da file esso cambia e il programma
	 * non li riconosce più come stesso oggetto (in prestiti). Confronto quindi gli id delle due risorse
	 * @param r la risorsa con cui confrontare l'oggetto
	 * @return true se le due risorse hanno lo stesso id
	 */
	public abstract boolean equals(Risorsa r);
	/**
	 * stampa le informazioni relative alla risorsa
	 * @param perPrestito true se la visualizzazione serve nell'elenco dei prestiti
	 */
	public abstract void stampaDati(boolean perPrestito);
	/**
	 * @return il titolo della risorsa
	 */
	public abstract String getTitolo();
	/**
	 * @return l'id univoco della risorsa
	 */
	public abstract String getId();	
	/**
	 * @return il numero di copie della risorsa attualmente in prestito
	 */
	public abstract int getInPrestito();
	/**
	 * @return true se la risorsa è ancora disponibile al prestito
	 */
	public abstract boolean isPrestabile();
	/**
	 * @return quanti giorni la risorsa può stare in prestito
	 */
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
}