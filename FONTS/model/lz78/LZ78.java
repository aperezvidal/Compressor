package model.lz78;

import model.*;

import java.io.IOException;
import java.util.*;

/***
 * Classe de l'algorisme LZ78
 * @author Marc Simó Guzmán
 */
public class LZ78
{
    private static DecoderLZ78 Decoder;
    private static EncoderLZ78 Encoder;


    /***
     *  Constructora sense paràmetres
     */
    public LZ78()
    {
        Decoder= new DecoderLZ78();
        Encoder= new EncoderLZ78();
    }

    /***
     * Fa l'acció indicada sobre el fitxer indicat, i guarda el resultat en un nou fitxer
     * @param accio: String que indica l'acció a realitzar ("encode" o "decode")
     * @param fitxer: String que indica el fitxer sobre el que s'ha de realitzar l'acció
     * @throws IOException
     */
    public void pasa_parametres(String accio, String fitxer) throws IOException
    {
        Decoder= new DecoderLZ78();
        Encoder= new EncoderLZ78();

        if (accio.equals("encode")) {
            BinaryIn entrada = new BinaryIn(fitxer);
            StringBuilder input= new StringBuilder();
            while (!entrada.isEmpty()) input.append(entrada.readChar());

            ArrayList<Byte> output=Encoder.encode(input);

            CreateFile salida= new CreateFile();
            salida.CreateLZFile(output,fitxer,".lz78");
        }
        else {   //"decode"
            BinaryIn entrada = new BinaryIn(fitxer);
            ArrayList<Byte> input= new ArrayList<>();
            while (!entrada.isEmpty()) input.add(entrada.readByte());

            StringBuilder output = Decoder.decode(input);

            BinaryOut salida = new BinaryOut(fitxer.substring(0,fitxer.indexOf(".")) + "_decoded.txt");
            int n=output.length();
            for (int i=0;i<n;++i) salida.write(output.charAt(i));
            salida.close();
        }
    }
}

