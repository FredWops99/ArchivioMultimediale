package parte5;

import java.io.Serializable;

public class Film extends Risorsa implements Serializable
{
	private static final long serialVersionUID = 1L;

	/********************************************************************
	 * ogni categoria ha i suoi vincoli per quanto riguarda i PRESTITI: *
	 ********************************************************************/
	/**
	 * quanto tempo un Film può restare in prestito ad un fruitore
	 */
	public static final int GIORNI_DURATA_PRESTITO = 30;
	/**
	 * quanto dura una proroga del prestito di un Film
	 */
	public static final int GIORNI_DURATA_PROROGA = 30;
	/**
	 * quanti giorni prima della scadenza si può chiedere una proroga del prestito del Film
	 */
	public static final int GIORNI_PRIMA_PER_PROROGA = -7;
	/**
	 * quanti Films possono essere in prestito contemporaneamente allo stesso fruitore
	 */
	public static final int PRESTITI_MAX = 3;
	/**
	 * ID univoco del Film: Fxxx
	 */
	private String id;
	/**
	 * sottocategorie della categoria FILM: azione, avventura, horror ...
	 */
	private String sottoCategoria;
	private String titolo;
	private String regista;
	private int durata;
	private int annoDiUscita;
	private String lingua;
	private int nLicenze;
	private boolean prestabile;
	/**
	 * quante copie di questo Film sono già in prestito (<= nLicenze)
	 */
	private int inPrestito;
	
	/**
	 * Costuttore della classe Film
	 * @param id l'id univoco del film
	 * @param sottoCategoria la sottocategoria della categoria FILM (es. azione, avventura, horror ...)
	 * @param titolo il titolo del film
	 * @param regista il regista del film
	 * @param durata la durata (in minuti) del film
	 * @param annoDiUscita l'anno di uscita del film
	 * @param lingua la lingua del film
	 * @param nLicenze il numero di licenze possedute per il film
	 */
	public Film(String id, String sottoCategoria, String titolo, String regista, int durata, int annoDiUscita, String lingua, int nLicenze) 
	{
		this.setId(id);
		this.setSottoCategoria(sottoCategoria);
		this.setTitolo(titolo);
		this.setRegista(regista);
		this.setDurata(durata);
		this.setAnnoDiUscita(annoDiUscita);
		this.setLingua(lingua);
		this.setnLicenze(nLicenze);
		this.setInPrestito(0);
		this.prestabile = true;
	}
	
	/**
	 * Override del metodo equals() di object
	 */
	public boolean equals(Risorsa r)
	{
		if(this.id.equals(r.getId()))
		{
			return true;
		}
		else return false;
	}
	
	public void stampaDati(boolean perPrestito)
	{
//		System.out.println(BelleStringhe.CORNICE);
		System.out.println("Categoria-----------------: Film");
//		System.out.println("ID------------------------: " + id);
//		System.out.println("Hashcode------------------: " + hashCode());
		System.out.println("Sottocategoria------------: " + sottoCategoria);
		System.out.println("Titolo--------------------: " + titolo);
		System.out.println("Durata--------------------: " + durata + "'");
		System.out.println("Anno di uscita------------: " + annoDiUscita);
		System.out.println("Lingua--------------------: " + lingua);
		if(!perPrestito)//dati utili all'operatore
		{
			System.out.println("Numero licenze------------: " + nLicenze);
			System.out.println("In prestito---------------: " + inPrestito);
		}
		else//dati utili al fruitore
		{
			System.out.println("Copie disponibili---------: " + (nLicenze - inPrestito));
		}
	}

	//GETTER//
	
	public String getId() 
	{
		return id;
	}
	public String getSottoCategoria() 
	{
		return sottoCategoria;
	}
	public String getTitolo() 
	{
		return titolo;
	}
	public String getRegista() 
	{
		return regista;
	}
	public int getDurata() 
	{
		return durata;
	}
	public int getAnnoDiUscita() 
	{
		return annoDiUscita;
	}
	public String getLingua() 
	{
		return lingua;
	}
	public int getnLicenze() 
	{
		return nLicenze;
	}
	public int getInPrestito() 
	{
		return inPrestito;
	}
	public int getGiorniDurataPrestito() 
	{
		return Film.GIORNI_DURATA_PRESTITO;
	}

	public int getGiorniDurataProroga() 
	{
		return Film.GIORNI_DURATA_PROROGA;
	}

	public int getGiorniPrimaPerProroga() 
	{
		return Film.GIORNI_PRIMA_PER_PROROGA;
	}

	public int getPrestitiMax() 
	{
		return Film.PRESTITI_MAX;
	}	
	
	//SETTER//
	
	public void setId(String id) 
	{
		this.id = id;
	}
	public void setSottoCategoria(String sottoCategoria) 
	{
		this.sottoCategoria = sottoCategoria;
	}
	public void setTitolo(String titolo) 
	{
		this.titolo = titolo;
	}
	public void setRegista(String regista) 
	{
		this.regista = regista;
	}
	public void setDurata(int durata) 
	{
		this.durata = durata;
	}
	public void setAnnoDiUscita(int annoDiUscita) 
	{
		this.annoDiUscita = annoDiUscita;
	}
	public void setLingua(String lingua) 
	{
		this.lingua = lingua;
	}
	public void setnLicenze(int nLicenze)
	{
		this.nLicenze = nLicenze;
	}
	public void setInPrestito(int inPrestito) 
	{
		this.inPrestito = inPrestito;
	}
	public void setPrestabile(boolean prestabile) 
	{
		this.prestabile = prestabile;
	}
	
	
	/**
	 * precondizione: ci sono copie del Film disponibili per il prestito
	 */
	public void mandaInPrestito() 
	{
		inPrestito++;
	}
	/**
	 * precondizione: ci sono copie del film attualmente in prestito
	 */
	public void tornaDalPrestito()
	{
		inPrestito--;
	}
	/**
	 * mi dice se una risorsa può essere prestata 
	 */
	public boolean isPrestabile() 
	{
		return prestabile;
	}
}
