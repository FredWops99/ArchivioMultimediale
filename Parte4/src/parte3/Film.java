package parte3;

import java.io.Serializable;

public class Film extends Risorsa implements Serializable
{
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
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID univoco del Film
	 */
	private int id;
	
	private String sottoCategoria; //azione, avventura, horror ...
	private String nome;
	private String regista;
	private int durata;
	private int annoDiUscita;
	private String lingua;
	private int nLicenze;
	/**
	 * quante copie di questo Film sono già in prestito (<= nLicenze)
	 */
	private int inPrestito;
	
	/**
	 * Costuttore della classe Film
	 * @param id
	 * @param sottoCategoria
	 * @param nome
	 * @param regista
	 * @param durata
	 * @param annoDiUscita
	 * @param lingua
	 * @param nLicenze
	 * @param inPrestito
	 */
	public Film(int id, String sottoCategoria, String nome, String regista, int durata, int annoDiUscita, String lingua,
			int nLicenze) 
	{
		this.setId(id);
		this.setSottoCategoria(sottoCategoria);
		this.setNome(nome);
		this.setRegista(regista);
		this.setDurata(durata);
		this.setAnnoDiUscita(annoDiUscita);
		this.setLingua(lingua);
		this.setnLicenze(nLicenze);
		this.setInPrestito(0);
	}
	
	public void stampaDati(boolean perPrestito)
	{
//		System.out.println(BelleStringhe.CORNICE);
		System.out.println("Categoria-----------------: Film");
//		System.out.println("ID------------------------: " + id);
//		System.out.println("Hashcode------------------: " + hashCode());
		System.out.println("Sottocategoria------------: " + sottoCategoria);
		System.out.println("Titolo--------------------: " + nome);
		System.out.println("Numero pagine-------------: " + durata);
		System.out.println("Anno di pubblicazione-----: " + annoDiUscita);
		System.out.println("Lingua--------------------: " + lingua);
		if(!perPrestito)
		{
			System.out.println("Numero licenze------------: " + nLicenze);
			System.out.println("In prestito---------------: " + inPrestito);
		}
		else
		{
			System.out.println("Copie disponibili---------: " + (nLicenze - inPrestito));
		}
	}

	public int getId() 
	{
		return id;
	}
	public String getSottoCategoria() 
	{
		return sottoCategoria;
	}
	public String getNome() 
	{
		return nome;
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
	public void setId(int id) 
	{
		this.id = id;
	}
	public void setSottoCategoria(String sottoCategoria) 
	{
		this.sottoCategoria = sottoCategoria;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
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
	
	/**
	 * precondizione: ci sono copie del Film disponibili per il prestito
	 */
	public void mandaInPrestito() 
	{
		inPrestito++;
	}
	public void tornaDalPrestito()
	{
		inPrestito--;
	}

	@Override
	public int getGiorniDurataPrestito() 
	{
		return Libro.GIORNI_DURATA_PRESTITO;
	}

	@Override
	public int getGiorniDurataProroga() 
	{
		return Libro.GIORNI_DURATA_PROROGA;
	}

	@Override
	public int getGiorniPrimaPerProroga() 
	{
		return Libro.GIORNI_PRIMA_PER_PROROGA;
	}

	@Override
	public int getPrestitiMax() 
	{
		return Libro.PRESTITI_MAX;
	}
	
}
