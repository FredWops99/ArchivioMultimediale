package model;

import java.io.Serializable;
import java.util.Vector;

import view.LibriView;

/**
 * Classe che rappresenta la descrizione di una risorsa multimediale di tipo Libro
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Libro extends Risorsa implements Serializable
{	
	private static final long serialVersionUID = 1L;

	/********************************************************************
	 * ogni categoria ha i suoi vincoli per quanto riguarda i PRESTITI: *
	 ********************************************************************/
	/**
	 * quanto tempo un Libro può restare in prestito ad un fruitore
	 */
	public static final int GIORNI_DURATA_PRESTITO = 20;
	/**
	 * quanto dura una proroga del prestito di un Libro
	 */
	public static final int GIORNI_DURATA_PROROGA = 20;
	/**
	 * quanti giorni prima della scadenza si può chiedere una proroga del prestito del Libro
	 */
	public static final int GIORNI_PRIMA_PER_PROROGA = -5;
	/**
	 * quanti Libri possono essere in prestito contemporaneamente allo stesso fruitore
	 */
	public static final int PRESTITI_MAX = 3;
	/**
	 * ID univoco del libro
	 */
	private String id;
	/**
	 * sottocategorie della categoria LIBRO: Romanzo, Fumetto, Poesia...
	 */
	private String sottoCategoria;
	private String titolo;
	private Vector<String> autori = new Vector<>();
	private int pagine;
	private int annoPubblicazione;
	private String casaEditrice;
	private String lingua;
	private String genere;
	private int nLicenze;
	private boolean prestabile;
	/**
	 * quante copie di questo libro sono già in prestito (<= nLicenze)
	 */
	private int inPrestito;
	
	/**
	 * Costuttore della classe libro
	 * @param id l'id univoco del libro
	 * @param sottoCategoria la sottocategoria della categoria LIBRO (es. Romanzo, Fumetto, Poesia...)
	 * @param titolo il titolo del libro
	 * @param autori il vector degli autori del libro
	 * @param pagine il numero di pagine
	 * @param annoPubblicazione l'anno di pubblicazione
	 * @param casaEditrice la casa editrice
	 * @param lingua la lingua del testo
	 * @param genere il genere del libro ( "-" se il genere non ha sottogeneri)
	 * @param nLicenze il numero di licenze disponibili
	 */
	public Libro(String id, String sottoCategoria, String titolo, Vector<String> autori, int pagine, int annoPubblicazione, String casaEditrice,
			String lingua, String genere, int nLicenze) 
	{
		this.setId(id);
		this.setSottoCategoria(sottoCategoria);
		this.setTitolo(titolo);
		this.setAutori(autori);
		this.setPagine(pagine);
		this.setAnnoPubblicazione(annoPubblicazione);
		this.setCasaEditrice(casaEditrice);
		this.setLingua(lingua);
		this.setGenere(genere);
		this.setnLicenze(nLicenze);
		this.setInPrestito(0);
		this.prestabile = true;
	}
	
	public boolean equals(Risorsa r)
	{
		if(this.id.equals(r.getId()))
		{
			return true;
		}
		else return false;
	}
	
	
	@Override
	public String toString(boolean perPrestito) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Categoria-----------------: Libro");
		sb.append("Sottocategoria------------: " + sottoCategoria);
		sb.append("Titolo--------------------: " + titolo);
		sb.append("Autori--------------------:");
		for(int i = 0; i < autori.size(); i++)
		{
			sb.append(" " + autori.elementAt(i));
			if(i < autori.size()-1)
			{
				sb.append(",");
			}
			else sb.append("");
		}
		if(!genere.equals("-"))
		{
			sb.append("Genere--------------------: " + genere);
		}
		sb.append("Numero pagine-------------: " + pagine);
		sb.append("Anno di pubblicazione-----: " + annoPubblicazione);
		sb.append("Casa editrice-------------: " + casaEditrice);
		sb.append("Lingua--------------------: " + lingua);
		if(!perPrestito)//dati utili all'operatore
		{
			sb.append("Numero licenze------------: " + nLicenze);
			sb.append("In prestito---------------: " + inPrestito);
		}
		else//dati utili al fruitore
		{
			sb.append("Copie disponibili---------: " + (nLicenze - inPrestito));
		}

		return sb.toString();
	}

//	va messo nel suo controller
	@Override
	public void stampaDati(boolean perPrestito)
	{
		LibriView.stampaDati(this, perPrestito);
	}
	

	public String getId() 
	{
		return id;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public String getTitolo()
	{
		return titolo;
	}
	public void setTitolo(String titolo) 
	{
		this.titolo = titolo;
	}
	public Vector<String> getAutori() 
	{
		return autori;
	}
	public void setAutori(Vector<String> autori) 
	{
		this.autori = autori;
	}
	public int getPagine()
	{
		return pagine;
	}
	public void setPagine(int pagine) 
	{
		this.pagine = pagine;
	}
	public int getAnnoPubblicazione() 
	{
		return annoPubblicazione;
	}
	public void setAnnoPubblicazione(int annoPubblicazione) 
	{
		this.annoPubblicazione = annoPubblicazione;
	}
	public String getCasaEditrice() 
	{
		return casaEditrice;
	}
	public void setCasaEditrice(String casaEditrice) 
	{
		this.casaEditrice = casaEditrice;
	}
	public String getLingua() 
	{
		return lingua;
	}
	public void setLingua(String lingua) 
	{
		this.lingua = lingua;
	}
	public String getSottoCategoria() 
	{
		return sottoCategoria;
	}
	public void setSottoCategoria(String sottoCategoria) 
	{
		this.sottoCategoria = sottoCategoria;
	}
	public String getGenere() 
	{
		return genere;
	}
	public void setGenere(String sottoGenere) 
	{
		this.genere = sottoGenere;
	}
	public int getnLicenze() 
	{
		return nLicenze;
	}
	public void setnLicenze(int nLicenze) 
	{
		this.nLicenze = nLicenze;
	}
	public int getInPrestito() 
	{
		return inPrestito;
	}
	public void setInPrestito(int inPrestito)
	{
		this.inPrestito = inPrestito;
	}
	public boolean isPrestabile() 
	{
		return prestabile;
	}
	public void setPrestabile(boolean prestabile) 
	{
		this.prestabile = prestabile;
	}
	public void mandaInPrestito() 
	{
		inPrestito++;
	}
		public void tornaDalPrestito()
	{
		inPrestito--;
	}
	public int getGiorniDurataPrestito() 
	{
		return Libro.GIORNI_DURATA_PRESTITO;
	}
	public int getGiorniDurataProroga() 
	{
		return Libro.GIORNI_DURATA_PROROGA;
	}
	public int getGiorniPrimaPerProroga() 
	{
		return Libro.GIORNI_PRIMA_PER_PROROGA;
	}
	public int getPrestitiMax() 
	{
		return Libro.PRESTITI_MAX;
	}
}
