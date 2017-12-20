package parte1;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Fruitore implements Serializable 
{	
	private static final long serialVersionUID = 1L;
	private String nome; 
	private String cognome;
	private GregorianCalendar dataNascita; 
	private GregorianCalendar dataIscrizione;
	
	public Fruitore(String nome, String cognome, GregorianCalendar dataNascita, GregorianCalendar dataIscrizione) 
	{
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataIscrizione = dataIscrizione;
	}
	
	public String getNome() 
	{
		return nome;
	}
	public String getCognome() 
	{
		return cognome;
	}
	public GregorianCalendar getDataNascita() 
	{
		return dataNascita;
	}
	public GregorianCalendar getDataIscrizione() 
	{
		return dataIscrizione;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	public void setDataNascita(GregorianCalendar dataNascita) 
	{
		this.dataNascita = dataNascita;
	}
	public void setDataIscrizione(GregorianCalendar dataIscrizione) 
	{
		this.dataIscrizione = dataIscrizione;
	}
}
