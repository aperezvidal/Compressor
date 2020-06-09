package model.lz78;

import model.*;

import java.util.ArrayList;

/***
 * Driver que servix per provar la classe DecoderLZ78
 * @author Marc Simò Guzmàn
 */
public class Driver_DecoderLZ78
{
    /***
     * Si args[0]=="Usage" escribim per la sortida d'escritura estÃ ndard l'Usage
     * Altrament s'indica el fitxer d'entrada codificat en args[0] i es decodifica i es guarda en args[0]+"_decoded.txt"
     */
    public static void main(String[] args)
    {
        DecoderLZ78 Decoder= new DecoderLZ78();
        if (!args[0].equals("Usage")){
            BinaryIn entrada = new BinaryIn(args[0]);
            ArrayList<Byte> input= new ArrayList<>();
            while (!entrada.isEmpty()) input.add(entrada.readByte());

            StringBuilder output = Decoder.decode(input);

            BinaryOut salida = new BinaryOut(args[0].substring(0,args[0].indexOf(".")) + "_decoded.txt");
            int n=output.length();
            for (int i=0;i<n;++i) salida.write(output.charAt(i));
            salida.close();
        }
        else System.out.println("Usage: java  package......Driver_DecoderLZ78 'fitxer_entrada'.txt");
    }
}
