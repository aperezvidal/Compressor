package model;

/**
 * @author Daniel Boté Mayer
 * Classe Objecte
 */
public class Objecte
{
	private String nom;		//La funció per obtenir el nom es redefineix en les subclases.
	private double size;

	public Objecte(){};

	public double get_size()
	{
		return size;
	}

	public String get_nom() 
	{
		return nom;
	}
	public void set_nom(String s)
	{
		nom = s;
	}
}
