package parte4;

import parte4.Risorsa;

public abstract class Risorsa 
{
	
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
	 * precondizione: ci sono copie della risorsa disponibili per il prestito.
	 * Aggiorna il numero di copie disponibili e in prestito della risorsa
	 */
	public abstract void mandaInPrestito();
	/**
	 * precondizione: ci sono copie della risorsa attualmente in prestito.
	 * Aggiorna il numero di copie disponibili e in prestito della risorsa
	 */
	public abstract void tornaDalPrestito();
}

