package model.lzss;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Marc Jímenez Gimeno
 * Driver per proar la classe LZSS
 */
public class DriverLZSS
{
    private static String fitxer;
    private static String accio;
    private static LZSS lzss;


    public static void main(String[] args) throws IOException
    {
        System.out.println("Introdueix número d'acció:");
        System.out.println("1 - Comprimir");
        System.out.println("2 - Decscomprimir");
        Scanner scanner = new Scanner(System.in);
        accio = scanner.nextLine();
        while (! (accio.equals("1") | accio.equals("2"))) {
            System.out.println("No has introduit una accio valida.");
            System.out.println("Introduceix numero 1 per comprimir o numero 2 per descomprimir.");
            accio = scanner.nextLine();
        }
        System.out.println("Introdueix el nom del fitxer:");
        fitxer = scanner.nextLine();
        File af = new File(fitxer);
        while(!af.exists()) {
            System.out.println("El nom del fitxer introduit no existeix.");
            System.out.println("Introdueix un nom de fitxer valid.");
            fitxer = scanner.nextLine();
            af = new File(fitxer);
        }

        lzss = new LZSS();

        if(accio.equals("1")) {
            System.out.println("comprimint...");
            try {
                lzss.codifica(fitxer);
                System.out.println("S'ha comprimit el fitxer.");
            } catch (Exception e) {
                System.out.println("No s'ha pogut comprimir el fitxer.");
            }

        }

        if(accio.equals("2")) {
            System.out.println("decomprimint...");
            try {
                lzss.decodifica(fitxer);
                System.out.println("S'ha descomprimit el fitxer.");
            } catch (Exception e) {
                System.out.println("No s'ha pogut decoomprimir el fitxer.");
            }
        }
    }
}
