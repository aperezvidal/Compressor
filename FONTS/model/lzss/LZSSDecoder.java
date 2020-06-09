package model.lzss;

import java.io.ByteArrayOutputStream;

/**
 * @author Marc Jímenez Gimeno
 * classe LZSS
 */
public class LZSSDecoder
{
    private static final int LENGTH_SIZE = 7;
    private static final int DISTANCE_SIZE = 11;

    private static final int LOOKAHEAD_SIZE_MAX = (1 << LENGTH_SIZE) - 1;
    private static final int DICTIONARY_SIZE_MAX = 1 << DISTANCE_SIZE;


    /***
     * Decodifica code al fitxer de text corresponent abans de haber fet la compressio.
     * @param encodedText: Nom del fitxer de text que representa un string de 0 i 1 que representa la codificacio d'un fitxer de text comprimit en LZSS.
     * @return retorna un Array de Bytes corresponent al nostre text descomprimit.
     */
    public byte[] decode(String encodedText)
    {
        String2Byte2String transform = new String2Byte2String();
        String code = transform.Byte2String(encodedText);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] slide = new byte[DICTIONARY_SIZE_MAX + LOOKAHEAD_SIZE_MAX];
        int offset = 0;
        int rLength = code.length();
        //Condicional per no llegir un valor fora del baos
        while ((!code.isEmpty()) && ((code.startsWith("0") && rLength > 8) | (code.startsWith("1") && rLength > (LENGTH_SIZE + DISTANCE_SIZE)))) {
            if (code.startsWith("0")) {  //code.length necessary because of extra bits in the code when doing Byte2String
                // gets the substring after index 1 till index 1+Byte.SIZE
                // parseInt(string, 2) returns integer value in base 10 of string in base 2
                slide[offset++] = (byte) Integer.parseInt(code.substring(1, 1 + Byte.SIZE), 2);
                code = code.substring(1 + Byte.SIZE);
            }
            else {
                int length = Integer.parseInt(code.substring(1, 1 + LENGTH_SIZE), 2);
                int distance = Integer.parseInt(code.substring(1 + LENGTH_SIZE, 1 + LENGTH_SIZE + DISTANCE_SIZE), 2);
                try {
                    //public static void arraycopy (Object src, int srcPos, Object dest, int destPos, int length)
                    System.arraycopy(slide, offset - distance - 1, slide, offset, length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                offset = offset + length;
                // returns code starting from index 1+LENGTH_SIZE+DISTANCE_SIZE (What we still have to visit)
                code = code.substring(1 + LENGTH_SIZE + DISTANCE_SIZE);
            }
            if (offset > DICTIONARY_SIZE_MAX) {    //ACTUALIZE DICTIONARY IN CASE WE HAVE IT FULL
                baos.write(slide, 0, offset - DICTIONARY_SIZE_MAX); //write the extra part of the DICTIONARY_SIZE (starting from 0)
                System.arraycopy(slide, offset - DICTIONARY_SIZE_MAX, slide, 0, DICTIONARY_SIZE_MAX);  // Shift odd elements out of the dictionary
                offset = DICTIONARY_SIZE_MAX;   // offset still in the maximum size of dictionary
            }
            rLength = code.length();
        }
        baos.write(slide, 0, offset);
        return baos.toByteArray();
    }
}
