package model.lzw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Daniel Boté Mayer
 * Driver del LZW
 */
public class DriverLZW
{

	private static String fitxer_tractar;
	private static String accio;
	private static LZW lzw;


	public static void main(String[] args) throws IOException
	{
		System.out.println("Introdueix acció:");
		System.out.println("1 - Codificar");
		System.out.println("2 - Decodificar");
		Scanner scanner = new Scanner(System.in);
		accio = scanner.nextLine();
		System.out.println("Introdueix fitxer:");
		fitxer_tractar = scanner.nextLine();
		//fitxer_tractar = scanner.nextLine();
		//codificar/decodificar fitxer
		//fitxer_tractar = args[1];
		//System.out.println(fitxer_tractar);
		lzw = new LZW();

		if(accio.equals("1")) {
			System.out.println("Està codificant");
			lzw.codifica(fitxer_tractar);

		}

		if(accio.equals("2")) {
			lzw.decodifica(fitxer_tractar);
		}
	}
}
