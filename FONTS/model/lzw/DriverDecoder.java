package model.lzw; 

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Daniel Boté Mayer
 * Driver del Decoder
 */
public class DriverDecoder
{

	private static String fitxer;
	private static String accio;
	private static Decoder dec;
	private static StringBuffer sb;


	public static void main(String[] args) throws IOException
	{

	//java Decode fitxer accio

		System.out.println("Introdueix accio");
		System.out.println("1 - Decodifica string");
		System.out.println("2 - Modifica fitxer entrada");
		Scanner scanner = new Scanner(System.in);
		accio = scanner.nextLine();
		System.out.println("Introdueix fitxer");
		fitxer = scanner.nextLine();
		dec = new Decoder();

		if(accio.equals("1")) {
			try {
				sb = dec.Decode_String(fitxer);
			} catch(Exception e) {
				System.out.println("No ha decodificat bé");
			}
		}

		/*if(accio.equals("crea_fitxer"))
		{
			dec.Create_decoded_file(sb);
		}*/

		else if(accio.equals("2")) {
			try {
				dec.modifica_file_input(fitxer);
				System.out.println("Ha modificat el fitxer d'entrada");
			} catch (Exception e) {
				System.out.println("No ha modificat el fitxer d'entrada");
			}
		}	
	}


}
