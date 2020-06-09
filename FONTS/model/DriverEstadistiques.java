package model;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Marc Jímenez Gimeno
 * Driver de la classe Estadistiques
 */
public class DriverEstadistiques
{

    private static Estadistiques stadistics;
    private static String valor;
    private static int nBytes;

    public static void main(String[] args) throws IOException
    {
        //java DriverEncode acció fitxer;
        System.out.println("Introdueix acció:");
        System.out.println("1 - Comença timer");
        System.out.println("2 - Atura timer");
        System.out.println("3 - Assigna tamany fitxer sense comprimir");
        System.out.println("4 - Assigna tamany fitxer comprimit");
        System.out.println("5 - Mostra temps transcorregut");
        System.out.println("6 - Mostra rati de compressio");
        Scanner scanner = new Scanner(System.in);
        String accio = scanner.nextLine();
        stadistics = new Estadistiques();

        switch(accio)
        {
            case "1":
                try {
                    stadistics.StartTimer();
                    System.out.println("El timer ha començat correctament.");
                } catch (Exception e){
                    System.out.println("No se ha pogut començar el timer");
                }
                break;

            case "2":
                try {
                    stadistics.StopTimer();
                    System.out.println("El timer s'ha aturat correctament.");
                } catch (Exception e){
                    System.out.println("No se ha pogut aturar el timer");
                }
                break;

            case "3":
                System.out.println("Escriu el numero de Bytes que ocupa el fitxer sense comprimir.");
                valor = scanner.nextLine();
                nBytes = Integer.valueOf(valor);
                while(!(nBytes >= 0)){
                    System.out.println("No es un valor valid.");
                    System.out.println("Introdueix un numero de Bytes major que 0.");
                    valor = scanner.nextLine();
                    nBytes = Integer.valueOf(valor);
                }
                try{
                    stadistics.uncompressedSize(nBytes);
                    System.out.println("S'ha assignat correctament el valor del arxiu sense comprimir.");
                } catch (Exception e){
                    System.out.println("No se ha pogut assignar el tamany del arxiu sense comprimir.");
                }
                break;

            case "4":
                System.out.println("Escriu el numero de Bytes que ocupa el fitxer comprimit.");
                valor = scanner.nextLine();
                nBytes = Integer.valueOf(valor);
                while(!(nBytes >= 0)){
                    System.out.println("No es un valor valid.");
                    System.out.println("Introdueix un numero de Bytes major que 0.");
                    valor = scanner.nextLine();
                    nBytes = Integer.valueOf(valor);
                }
                try{
                    stadistics.compressedSize(nBytes);
                    System.out.println("S'ha assignat correctament el valor del arxiu comprimit.");
                } catch (Exception e){
                    System.out.println("No se ha pogut assignar el tamany del arxiu comprimit.");
                }
                break;

            case"5":
                try{
                    stadistics.GetTime();
                    System.out.println("S'ha obtingut el temps correctament.");
                } catch(Exception e){
                    System.out.println("No s'ha pogut obtenir el temps.");
                }
                break;

            case "6":
                try{
                    stadistics.GetCompressionRatio();
                    System.out.println("S'ha obtingut el compression ratio correctament.");
                } catch (Exception e){
                    System.out.println("No s'ha pogut obtenir el compression ratio.");
                }
                break;
            default:
                System.out.println("No s'ha introduit un numero de accio correcte.");
                System.out.println("Introdueix un numero de accio entre 1...6");
        }
    }
}
