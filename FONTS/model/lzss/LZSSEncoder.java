package model.lzss;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Marc Jímenez Gimeno
 * classe LZSSEncoder
 */
public class LZSSEncoder
{
    private static final int LENGTH_SIZE = 7;
    private static final int DISTANCE_SIZE = 11;

    private static final int LOOKAHEAD_SIZE_MAX = (1 << LENGTH_SIZE) - 1;
    private static final int DICTIONARY_SIZE_MAX = 1 << DISTANCE_SIZE;

    /***
     * Comprimeix un fitxer de text en un ArrayList<Bytes>.
     * @param File_Input: nom del fitxer de text a comprimir
     * @return Retorna un ArrayList<Byte> amb el fitxer de text comprimit.
     */
    public ArrayList<Byte> encode(String File_Input) throws IOException
    {
        byte[] data = Files.readAllBytes(Paths.get(File_Input));
        ByteBuffer buf = ByteBuffer.wrap(data).asReadOnlyBuffer();
        StringBuilder sb = new StringBuilder();
        while (buf.hasRemaining()) {
            int length = 0, distance = 0;
            int dictSize = Math.min(buf.position(), DICTIONARY_SIZE_MAX);
            for (int i = 0; i < dictSize; i++) {
                int j;
                int lookAheadSize = Math.min(buf.remaining(), LOOKAHEAD_SIZE_MAX);
                for (j = 0; j < i + 1 && j < lookAheadSize; j++) {
                    int dictionaryData = buf.get(buf.position() - i + j - 1);
                    int lookAheadData = buf.get(buf.position() + j);
                    if (dictionaryData != lookAheadData) {
                        break;
                    }
                }
                if (length < j) {
                    distance = i;
                    length = j;
                }
            }
            if (length * (1 + Byte.SIZE) < 1 + LENGTH_SIZE + DISTANCE_SIZE) {
                sb.append("0");
                sb.append(toBits(Byte.SIZE, buf.get()));
            }
            else {
                sb.append("1");
                sb.append(toBits(LENGTH_SIZE, length));
                sb.append(toBits(DISTANCE_SIZE, distance));
                buf.position(buf.position() + length);
            }
        }
        String2Byte2String transform = new String2Byte2String();
        return transform.String2Byte(sb.toString());
    }


    /***
     * Codifica un element b, en la seva representacio binaria en size bits.
     * @param size: Llargaria del element un cop passat a binari
     * @param b: Element a passar a binari
     * @return Retrona un String de 0 i 1.
     */
    private String toBits(int size, int b)
    {
        return size == 0 ? "" : toBits(size - 1, b >> 1) + (b & 1);
    }

}
