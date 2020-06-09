package model.lz78;

import model.*;

import java.io.IOException;
import java.util.ArrayList;

/***
 * Driver que servix per provar la classe EncoderLZ78
 * @author Marc Simó Guzmán
 */public class Driver_EncoderLZ78
{
    /***
     * Si args[0]=="Usage" escribim per la sortida d'escritura estÃ ndard l'Usage
     * Altrament s'indica el fitxer d'entrada en args[0] i es codifica i es guarda en (args[0]-".txt")+".lz78"
     */
    public static void main(String[] args) throws IOException
    {
        EncoderLZ78 Encoder= new EncoderLZ78();
        if (!args[0].equals("Usage")) {
            BinaryIn entrada = new BinaryIn(args[0]);
            StringBuilder input= new StringBuilder();
            while (!entrada.isEmpty()) input.append(entrada.readChar());

            ArrayList<Byte> output=Encoder.encode(input);

            CreateFile salida= new CreateFile();
            salida.CreateLZFile(output,args[0],".lz78");
        }
        else System.out.println("Usage: java  package......Driver_EncoderLZ78 'fitxer_entrada'.txt");
    }
}
