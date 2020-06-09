package model.lzss;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Marc Jímenez Gimeno
 * Driver per probar la classe String2Byte2String
 */
public class DriverString2Byte2String
{
    private static String operacio;
    private static String2Byte2String transform;

    public static void main(String[] args) throws IOException
    {
        System.out.println("Introdueix numero de Operacio que vols realitzar:");
        System.out.println("1 -> Si vols transformar un string de 0 i 1s a ArrayList<Byte> corresponent.");
        System.out.println("2 -> Si vols transformar un fitxer de Bytes, al seu string de 0 i 1s corresponents.");
        Scanner scanner = new Scanner(System.in);
        operacio = scanner.nextLine();
        while (!(operacio.equals("1") | operacio.equals("2"))) {
            System.out.println("No has introduit un numero de operacio correcte.");
            System.out.println("Introdueix 1 o 2.");
            operacio = scanner.nextLine();
        }

        transform = new String2Byte2String();

        switch(operacio)
        {
            case "1":
                System.out.println("Introdueix per terminal un conjunt de 0 i 1s en format 0001101010...");
                String str = scanner.nextLine();
                try {
                    transform.String2Byte(str);
                    System.out.println("Se ha fet la transformacio a ArrayList<Byte>");
                } catch (Exception e) {
                    System.out.println("No se ha pogut transformar el string de 0 i 1, a ArrayList<Byte>");
                }
                break;
            case "2":
                System.out.println("Introdueix el nom del fitxer de Bytes que vols transformar a string 0 i 1s");
                String fitxer = scanner.nextLine();
                File af = new File(fitxer);
                while (!af.exists()) {
                    System.out.println("El fitxer no existeix, introodueix el nom d'un fitxer valid.");
                    fitxer = scanner.nextLine();
                    af = new File(fitxer);
                }
                try {
                    transform.Byte2String(fitxer);
                    System.out.println("S'ha fet la transformacio a string de 0 i 1s");
                } catch (Exception e) {
                    System.out.println("No s'ha pogut fer la transformacio del fitxer de Bytes a un String de 0 i 1s");
                }
                break;
            default:
                System.out.println("Error, no hauria de passar mai !");
        }
    }
}
