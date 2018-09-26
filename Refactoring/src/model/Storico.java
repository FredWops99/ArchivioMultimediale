package model;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import myLib.GestioneDate;

public class Storico implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	private Prestiti prestiti;
	private Fruitori fruitori;
	private Archivio archivio;
	
	public Storico(Prestiti prestiti, Fruitori fruitori, Archivio archivio)
	{
		this.prestiti = prestiti;
		this.fruitori = fruitori;
		this.archivio = archivio;
	}
	
	public Prestiti getPrestiti() 
	{
		return prestiti;
	}

	public Fruitori getFruitori() 
	{
		return fruitori;
	}

	public Archivio getArchivio() 
	{
		return archivio;
	}
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che conta i prestiti che sono avvenuti nell'anno solare inserito dall'utente
	 * @param anno selezionato
	 * @return il numero dei prestiti avvenuti durante l'anno inserito dall'utente
	 */
	public int prestitiAnnoSolare(int anno)
	{
		
		int contatorePrestiti = 0;
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == anno)
				contatorePrestiti++;
		}
		
		return contatorePrestiti;
	}
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che conta tutte le proroghe che sono avvenute nell'anno solare inserito dall'utente
	 * @param anno selezionato
	 * @return il numero di proroghe avvenute durante l'anno inserito dall'utente
	 */
	public int prorogheAnnoSolare(int anno)
	{
		int contatoreProroghe = 0;
		
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.isProrogato() && prestito.getDataRitorno().get(GregorianCalendar.YEAR) == anno)
				contatoreProroghe++;
		}
		return contatoreProroghe;
	}
	
	/**
	 *(precondizione: prestiti != null)
	 * Metodo che restituisce un vettore di prestiti avvenuti nell'anno inserito
	 * @param annoanno selezionato
	 * @return vettore di prestiti avvenuti nell'anno inserito
	 */
	public Vector<Prestito> prestitiAnnui(int anno)
	{
		Vector<Prestito> prestitiAnnui = new Vector<Prestito>();
		//int annoSelezionato = StoricoView.AnnoSelezionato();
		
		//seleziono solo i prestiti che sono stati effettuati nell'anno indicato
		for (Prestito prestito : prestiti.getPrestiti()) 
		{
			if(prestito.getDataInizio().get(GregorianCalendar.YEAR) == anno)
			{
				prestitiAnnui.add(prestito);
			}
		}
		if(prestitiAnnui.isEmpty())
		{
			return null;
			//StoricoView.noPrestitiInAnnoSelezionato();
		}
		else
		{
			return prestitiAnnui;
		}	
	}
	
	
	/**
	 * (precondizione: prestiti != null)
	 * Metodo che restituisce tutti i prestiti effettuati dal fruitore il cui user 
	 * è uguale ad user selezionato
	 * @param prestiti l'elenco di tutti i prestiti in archivio
	 * @param user = nome utente selezionato
	 * @return tutti i prestiti effettuati dal fruitore il cui user 
	 * è uguale ad user selezionato
	 */
	public int prestitiPerFruitore(Vector<Prestito> prestiti, String user)
	{
		int nPrestiti = 0;
		for(Prestito prestito : prestiti)
		{
			if(prestito.getFruitore().getUser().equals(user))
			{
				nPrestiti++;
			}
		}
		return nPrestiti;
	}
	
	/**
	 * Metodo che restituisce un vettore di Libri o Films che erano prestabili in passato
	 * @param risorsa -> "Libro" o "Film". Indica se si vuole cercare nei libri o nei films
	 * @return Vector<Risorsa> -> un vettore di risorse (Libri o Films) che erano prestabili in passato
	 */
	public Vector<Risorsa> risorsePrestabiliInPassato(String risorsa)
	{
		Vector<Risorsa>risorsePrestabili = new Vector<>();
		
		if(risorsa.equals("Libri"))
		{
			Vector<Libro>libri = archivio.getLibri().getLibri();
			for(Libro libro : libri)
			{
				if(!libro.isPrestabile())
				{
					risorsePrestabili.add(libro);
				}
			}
		}
		else if(risorsa.equals("Films"))
		{
			Vector<Film>films = archivio.getFilms().getFilms();
			for(Film film : films)
			{
				if(!film.isPrestabile())
				{
					risorsePrestabili.add(film);
				}
			}
		}
		
		if(risorsePrestabili.isEmpty())
		{
			return null;
		}
		else
		{
			return risorsePrestabili;
		}
	}
	
	/**
	 * Metodo che restituisce un vettore di fruitori che sono decaduti 
	 * @return Vector<Fruitore> -> vettore di fruitori che sono decaduti
	 */
	public Vector<Fruitore> fruitoriDecaduti()
	{
		int num = 0;
		Vector<Fruitore> fruitoriDecaduti = new Vector<>();
		for (int i = 0; i < fruitori.getFruitori().size(); i++) 
		{
			if(fruitori.getFruitori().get(i).isDecaduto())
			{
				fruitoriDecaduti.addElement(fruitori.getFruitori().get(i));
				//StoricoView.fruitoreDecaduto(fruitori.getFruitori().get(i));
				num++;
			}
		}
		if(num == 0)
		{
			return null;
		}
		else
		{
			return fruitoriDecaduti;
		}
	}
	
	/**
	 * Metodo che restituisce un vettore composto dai nomi dei fruitori che hanno rinnovato l'iscrizione
	 * @return Vector<String> -> vettore composto dai nomi dei fruitori che hanno rinnovato l'iscrizione
	 */
	public Vector<String> fruitoriRinnovati()
	{
		int num = 0;
		Vector<String> dateRinnovi = new Vector<>();
		for (int i = 0; i < fruitori.getFruitori().size(); i++) 
		{
			if(!fruitori.getFruitori().get(i).getRinnovi().isEmpty())
			
				num++;
				for (int j = 0; i < fruitori.getFruitori().get(i).getRinnovi().size(); j++) 
				{
					dateRinnovi.addElement(GestioneDate.visualizzaData(fruitori.getFruitori().get(i).getRinnovi().get(j)));
					//StoricoView.dateRinnovo(GestioneDate.visualizzaData(fruitori.getFruitori().get(i).getRinnovi().get(j)));
				}
		}
		
		if(num == 0)
		{
			return null;
			//StoricoView.nessunFruitoreHaRinnovato();
		}
		else
		{
			return dateRinnovi;
		}
	}
	
	/**
	 * Metodo che restituisce un vettore di prestiti che sono stati prorogati
	 * @return  Vector<Prestito> -> vettore di prestiti che sono stati prorogati
	 */
	public Vector<Prestito> prestitiProrogati()
	{
		int num = 0;
		Vector<Prestito> prestitiProrogati = new Vector<>();
		
		for (int i = 0; i < prestiti.getPrestiti().size(); i++) 
		{
			if(prestiti.getPrestiti().get(i).isProrogato())
			{
				num++;
				prestitiProrogati.addElement(prestiti.getPrestiti().get(i));
				//StoricoView.prestitoProrogato(prestiti.getPrestiti().get(i));
			}
		}
		if(num == 0)
		{
			return null;
			//StoricoView.noPrestitiRinnovvati();
		}
		else
		{
			return prestitiProrogati;
		}	
	}
	
	/**
	 * Metodo che restituisce un vettore di prestiti che sono terminati
	 * @return Vector<Prestito> -> vettore di prestiti che sono terminati
	 */
	public Vector<Prestito> prestitiTerminati()
	{
		int num = 0;
		Vector<Prestito> prestitiTerminati = new Vector<>();
		for (int i = 0; i < prestiti.getPrestiti().size(); i++) 
		{
			if(prestiti.getPrestiti().get(i).isTerminato())
			{
				num++;
				prestitiTerminati.addElement(prestiti.getPrestiti().get(i));
				//StoricoView.prestitoTerminato(prestiti.getPrestiti().get(i));
			}
		}
		if(num == 0)
		{
			return null;
			//StoricoView.noPrestitiTerminati();
		}
		else
		{
			return prestitiTerminati;
		}
	}
	
	/**
	 * Metodo che restituisce un vettore di prestiti che sono terminati in anticipo
	 * @return Vector<Prestito> -> vettore di prestiti che sono terminati in anticipo
	 */
	public Vector<Prestito> prestitiTerminatiInAnticipo()
	{
		int num = 0;
		Vector<Prestito> prestitiTerminatiInAnticipo = new Vector<>();
		for(Prestito prestito : prestiti.getPrestiti())
		{
			if(prestito.isTerminato() && prestito.getDataRitorno().before(prestito.getDataScadenza()))
			{
				num++;
				prestitiTerminatiInAnticipo.addElement(prestito);
				//StoricoView.prestitoTerminatoInAnticipo(prestito);
			}
		}
		if(num == 0)
		{
			return null;
		}
		else
		{
			return prestitiTerminatiInAnticipo;
		}
	}
	
}
