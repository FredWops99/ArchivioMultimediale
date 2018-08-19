package model;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import menuStorico.MenuStorico;
import myLib.GestioneDate;
import view.StoricoView;

/**
 * Classe con metodi statici (non serve istanziare un oggetto Storico) che serve per gestire le informazioni storiche dell'archivio
 * @author Prandini Stefano
 * @author Landi Federico
 */
public class Storico implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che conta i prestiti che sono avvenuti nell'anno solare inserito dall'utente
	 * @param prestiti l'elenco di tutti i prestiti in archivio
	 * @return il numero dei prestiti avvenuti durante l'anno inserito dall'utente
	 */
	public static int prestitiAnnoSolare(Prestiti prestiti)
	{
		int contatorePrestiti = 0;
		int annoSelezionato = StoricoView.AnnoSelezionato();
		
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
				contatorePrestiti++;
		}
		
		return contatorePrestiti;
	}

	/**
	 * (precondizione: prestiti != null)
	 * Metodo che conta tutte le proroghe che sono avvenute nell'anno solare inserito dall'utente
	 * @param prestiti l'elenco di tutti i prestiti in archivio
	 * @return il numero di proroghe avvenute durante l'anno inserito dall'utente
	 */
	public static int prorogheAnnoSolare(Prestiti prestiti)
	{
		int contatoreProroghe = 0;
		int annoSelezionato = StoricoView.AnnoSelezionato();
		
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.isProrogato() && prestito.getDataRitorno().get(GregorianCalendar.YEAR) == annoSelezionato)
				contatoreProroghe++;
		}
		return contatoreProroghe;
	}
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che individua la risorsa che ha avuto più prestiti nell'anno solare inserito dall'utente
	 * @param prestiti l'elenco di tutti i prestiti in archivio
	 */
	public static void risorsaPiùInPrestito(Prestiti prestiti)
	{
		int maxGenerale = 0;
		String titoloRisorsaMax = "";
		int annoSelezionato = StoricoView.AnnoSelezionato();

		Vector<Risorsa> risorseAnnue = new Vector<Risorsa>();
		//seleziono solo le risorse che sono state prenotate nell'anno corrente
		for (Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
			{
				risorseAnnue.add(prestito.getRisorsa());
			}
		}
		//da qui avviene il conteggio
		for(int i = 0; i < risorseAnnue.size(); i++)
		{
			int maxRisorsa = 1;
			String idInConsiderazione = risorseAnnue.get(i).getId();
			for (int j = risorseAnnue.size()-1; j > i; j--) 
			{
				if(idInConsiderazione.equals(risorseAnnue.get(j).getId()))
				{
					maxRisorsa++;
					risorseAnnue.remove(j);
				}
			}
			if(maxRisorsa > maxGenerale) 
			{
				maxGenerale = maxRisorsa;
				titoloRisorsaMax = risorseAnnue.get(i).getTitolo();
			}
		}
		//qui avviene la stampa
		if(maxGenerale==0)
		{
			StoricoView.noPrestitiInAnnoSelezionato();
		}
		else
		{
			StoricoView.risorsaConPiùPrenotazioniInAnnoSelezionato(titoloRisorsaMax, maxGenerale);
		}
	}
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che mostra tutti i prestiti annui effettuati da ogni fruitore
	 * @param prestiti l'elenco di tutti i prestiti in archivio
	 */
	public static void prestitiAnnuiPerFruitore(Prestiti prestiti)
	{
		Vector<Prestito> prestitiAnnui = new Vector<Prestito>();
		int annoSelezionato = StoricoView.AnnoSelezionato();
		//seleziono solo i prestiti che sono stati effettuati nell'anno indicato
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == annoSelezionato)
			{
				prestitiAnnui.add(prestito);
			}
		}
		if(prestitiAnnui.isEmpty())
		{
			StoricoView.noPrestitiInAnnoSelezionato();
		}
		else
		{
			for(int j = 0; j < prestitiAnnui.size(); j++)
			{
				int nPrestiti = 0;
				Fruitore fruitoreInConsiderazione = prestiti.getPrestiti().get(j).getFruitore();
				//conteggio al contrario così quando elimino non salto elementi
				for (int i = prestitiAnnui.size()-1; i >= j; i--)
				{
					if(fruitoreInConsiderazione.equals(prestitiAnnui.get(i).getFruitore()))
					{
						nPrestiti++;
						prestitiAnnui.remove(i);
					}
				}
				StoricoView.numeroPrestitiPerUtente(fruitoreInConsiderazione.getNome(), nPrestiti);
			}
		}
	}
	
	/**
	 * (precondizione: archivio != null)
	 * Metodo che mostra tutte le risorse che erano prestabili e che ora non lo sono più, distinguendo 
	 * tra film e libri
	 * @param archivio tutte le risorse in archivio
	 */
	public static void risorsePrestabili(Archivio archivio)
	{
		Vector<Libro> libri = archivio.getLibri().getLibri();
		Vector<Film>  films = archivio.getFilms().getfilms();
		
		StoricoView.libriPrestabiliInPassato();
		int numL = 0;
		for(int i = 0; i < libri.size(); i++)
		{
			if(!libri.get(i).isPrestabile())
			{
				StoricoView.libroPrestabileInPassato(libri.get(i).getTitolo());
				numL++;
			}
		}
		if(numL == 0)
		{
			StoricoView.nessuno();
		}
		StoricoView.newLine();
		StoricoView.filmsPrestabiliInPassato();
		int numF = 0;
		for(int i = 0; i < films.size(); i++)
		{
			if(!films.get(i).isPrestabile())
			{
				StoricoView.filmPrestabileInPassato(films.get(i).getTitolo());
				numF++;
			}
		}
		if(numF == 0)
		{
			StoricoView.nessuno();
		}
	}
	
	/**
	 * (precondizione: fruitori != null)
	 * Metodo che mostra tutti i fruitorori che non sono più iscritti al sistema multimediale
	 * @param fruitori l'elenco di tutti i fruitori
	 */
	public static void fruitoriDecaduti(Fruitori fruitori)
	{
		int num = 0;
		for (int i = 0; i < fruitori.getFruitori().size(); i++) 
		{
			if(fruitori.getFruitori().get(i).isDecaduto())
			{
				//lo stampa solo al primo fruitori con iscrizione decaduta che trova
				if(num == 0)
				{
					StoricoView.fruitoriDecaduti();
				}
				StoricoView.fruitoreDecaduto(fruitori.getFruitori().get(i).getNome(), 
											 fruitori.getFruitori().get(i).getCognome(), 
											 GestioneDate.visualizzaData(fruitori.getFruitori().get(i).getDataScadenza()));
				num++;
			}
		}
		if(num == 0)
		{
			StoricoView.noIscrizioniDecadute();
		}
	}
	
	/**
	 * (precondizione: fruitori != null)
	 * Metodo che mostra tutte le date nelle quali i fruitori hanno rinnovato la loro iscrizione
	 * al sistema multimediale
	 * @param fruitori l'elenco di tutti i fruitori
	 */
	public static void fruitoriRinnovati(Fruitori fruitori)
	{
		int num = 0;
		for (int i = 0; i < fruitori.getFruitori().size(); i++) 
		{
			if(!fruitori.getFruitori().get(i).getRinnovi().isEmpty())
			{
				//lo stampa solo al primo fruitori con rinnovi che trova
				if(num == 0)
				{
					StoricoView.iscrizioniRinnovate();
				}
				num++;
				
				for (int j = 0; i < fruitori.getFruitori().get(i).getRinnovi().size(); j++) 
				{
					StoricoView.dateRinnovo(GestioneDate.visualizzaData(fruitori.getFruitori().get(i).getRinnovi().get(j)));
				}
			}
		}
		if(num == 0)
		{
			StoricoView.nessunFruitoreHaRinnovato();
		}
	}
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che mostra tutti i prestiti che sono stati soggetti a proroga
	 * @param prestiti l'elenco di tutti i prestiti
	 */
	public static void prestitiProrogati(Prestiti prestiti)
	{
		int num = 0;
		for (int i = 0; i < prestiti.getPrestiti().size(); i++) 
		{
			if(prestiti.getPrestiti().get(i).isProrogato())
			{
				if(num == 0)
				{
					//lo stampa solo al primo prestito rinnovato che trova
					StoricoView.prestitiProrogati();
				}
				num++;
				StoricoView.prestitoProrogato(prestiti.getPrestiti().get(i).getRisorsa().getTitolo(),
												GestioneDate.visualizzaData(prestiti.getPrestiti().get(i).getDataPerRichiestaProroga()));  
			}
		}
		if(num == 0)
		{
			StoricoView.noPrestitiRinnovvati();
		}
	}
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che mostra tutti i prestiti che sono terminati.
	 * Viene mostrata la risorsa con il relativo fruitore che l'ha richiesta
	 * e la data in cui è avvenuta la terminazione del prestito
	 * @param prestiti l'elenco di tutti i prestiti
	 */
	public static void prestitiTerminati(Prestiti prestiti)
	{
		int num = 0;
		for (int i = 0; i < prestiti.getPrestiti().size(); i++) 
		{
			if(prestiti.getPrestiti().get(i).isTerminato())
			{
//				lo stampa solo al primo prestito terminato che trova
				if(num == 0)
				{
					StoricoView.prestitiTerminati();
					
				}
				num++;
				String risorsa = prestiti.getPrestiti().get(i).getRisorsa().getClass().getSimpleName();
				StoricoView.prestitoTerminato(risorsa,prestiti.getPrestiti().get(i).getRisorsa().getTitolo(),
												prestiti.getPrestiti().get(i).getFruitore().getUser(),
												GestioneDate.visualizzaData(prestiti.getPrestiti().get(i).getDataRitorno()));
			}
		}
		if(num == 0)
		{
			StoricoView.noPrestitiTerminati();
		}
	}
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che mostra tutti i prestiti che sono stati terminati in anticipo
	 * rispetto alla data di terminazione del prestito
	 * @param prestiti l'elenco di tutti i prestiti
	 */
	public static void prestitiTerminatiInAnticipo(Prestiti prestiti) 
	{
		int num = 0;
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.isTerminato() && prestito.getDataRitorno().before(prestito.getDataScadenza()))
			{
				if(num == 0)
				{
					StoricoView.prestitiTerminatiInAnticipo();
				}
				num++;
				String risorsa = prestito.getRisorsa().getClass().getSimpleName();
				StoricoView.prestitoTerminatoInAnticipo(risorsa, 
														prestito.getRisorsa().getTitolo(), 
														prestito.getFruitore().getUser(),
														GestioneDate.visualizzaData(prestito.getDataRitorno()),
														GestioneDate.visualizzaData(prestito.getDataScadenza()));
			}
		}
		if(num == 0)
		{
			StoricoView.noPrestitiTerminatiInAnticipo();
		}
	}
}