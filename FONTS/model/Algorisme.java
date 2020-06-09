package model;

import model.lzss.*;
import model.lz78.*;
import model.lzw.*;
import model.jpeg.*;

import java.io.IOException;
import java.util.*;


/**
 * @author Daniel Boté Mayer
 * Classe Algorisme
 */
public class Algorisme
{

	public static void main(String[] args) throws IOException
	{


	String alg;
	String accio;
	String fitxer;
	Estadistiques est;

	LZSS alg_lzss = new LZSS();
	LZ78 alg_lz78 = new LZ78();
	LZW alg_lzw = new LZW();
	JPEG alg_jpeg = new JPEG();

	//java Main_general algorisme codificar/decodificar fitxer
	System.out.println("Escull algorisme:");
	System.out.println("1 - LZSS");
	System.out.println("2 - LZ78");
	System.out.println("3 - LZW");
	System.out.println("4 - JPEG");
	Scanner scanner = new Scanner(System.in);
	alg = scanner.nextLine();
	System.out.println("Introdueix accio");
	System.out.println("encode");
	System.out.println("decode");
	accio = scanner.nextLine();
	while(!(accio.equals("encode") | accio.equals("decode"))) {
		System.out.println("No s'ha escollit una acció valida");
		System.out.println("Escull encode o decode");
		accio = scanner.nextLine();
	}
	System.out.println("Introdueix fitxer");
	fitxer = scanner.nextLine();
	est = new Estadistiques();


	est.StartTimer();
	Objecte obj = new Objecte();
	switch (alg)
	{
		case "1":
			alg_lzss.pasa_parametres(accio, fitxer);
			break;
		case "2":
			alg_lz78.pasa_parametres(accio, fitxer);
			break;
		case "3":
			alg_lzw.pasa_parametres(accio, fitxer);
			break;
		case "4":
			alg_jpeg.pasa_parametres(accio, fitxer);
			break;
		default:
			System.out.println("No s'ha seleccionat cap algorisme valid.");
			System.out.println("Escull un algorisme entre 1 i 4");
	}
	est.StopTimer();
	est.GetTime();
	}
}
