package model.lz78;

import model.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;

/***
 * Decodificador de l'algorisme LZ78
 * @author Marc Simó Guzmán
 */
public class DecoderLZ78 {

    /***
     * Constructora sense parÃ metres
     */
    public DecoderLZ78(){}

    /***
     *
     * @param b: Byte sobre el que operar
     * @param quant: Quantitat de bytes que Ã©s ficaran a 0
     * @return Retorna el byte b amb els primers quant bytes ficats a 0
     */
    private byte zeroLeft (Byte b, int quant){    // quant Ã©s quantitat de 0 a l'esquerra
        return ByteBuffer.allocate(4).putInt((b)&(255>>>(quant))).array()[3];
    }

    /***
     *
     * @param b1: Byte amb el que operar
     * @param b2: Byte amb el que operar
     * @return Retorna el resultat de realitzar una operaciÃ³ d'or a nivell de bit entre b1 i b2
     */
    private byte  byteOr (Byte b1, Byte b2){    // quant Ã©s quantitat de 0 a l'esquerra
        return ByteBuffer.allocate(4).putInt((b1|b2)).array()[3];
    }

    /***
     *
     * @param x: Enter del que Ã©s vol saber el logaritme
     * @return Si x Ã©s 0 retorna 0, sinÃ² retorna el resultat de fer log2(x) arredonit a la baixa
     */
    private int log2(int x)
    {
        if (x==0) return 0; //Tecnicament incorrecte pero necessari
        return (int) (Math.log(x) / Math.log(2) + 1e-10);
    }

    /***
     *
     * @param op: Ã‰s un enter que identifica la direcciÃ³ en la que fer shift: 0->esquerra, 1->dreta
     * @param b: Ã‰s el byte sobre el que Ã©s realitza l'operaciÃ³ de shift
     * @param quant: Ã‰s la quantitat de bits a shiftejar
     * @return Retornem el byte b shiftejat quant vegades en la direcciÃ³ marcada per op, els bits nous que apareixen en shiftejar sempre sÃ³n 0
     */
    private byte shift(int op, Byte b, int quant){
        if (op==0){     //esquerra
            return ByteBuffer.allocate(4).putInt((b)<<(quant)).array()[3];
        }
        else{    //dreta
            int aux1=b;
            if (aux1<0) aux1+=256;
            return ByteBuffer.allocate(4).putInt(aux1>>>(quant)).array()[3];
        }
    }

    /***
     *
     * @param vector: Vector que contÃ© els bytes corresponents a un Enter
     * @param nbits: Nombre de bits que contenent la codificaciÃ³ binaria de l'enter
     * @return Retorna l'enter (en positiu) corresponent als ÃšLTIMS nbits del vector
     */
    private Integer getNombre(Vector<Byte> vector, int nbits){
        int res=0, n=vector.size();
        int nbitsInicials=nbits%8;
        if (nbitsInicials==0) nbitsInicials=8;

        if (nbits%8!=0 || (nbits/8)>=n){
            res=(int) zeroLeft(vector.get(0),8-nbitsInicials);
            if (res<0) res+=256;
        }

        for (int i=1;i<n;++i){
            int aux=vector.get(i);
            if (aux<0) aux+=256;
            res*=256;
            res+=aux;
        }

        return res;
    }

    /***
     *
     * @param vector: Vector que contÃ© un nombre codificat en nbits
     * @param bitInicial: Bit on comenÃ§a la codificaciÃ³ de l'enter
     * @param nbits: Nombre de bits que codifiquen l'enter
     * @return Retorna el nombre (en positiu) corresponent als nbits del vector a partir de bitInicial
     */
    private Pair<Integer,Integer> llegixNombre(Vector<Byte> vector, int bitInicial, int nbits){

        int nombreRepresentat=0;
        int bitInicialSeguent=(bitInicial+nbits)%8;

        if (bitInicialSeguent==0){
            nombreRepresentat=getNombre(vector,nbits);
        }
        else{
            Vector<Byte> aux=new Vector<Byte>();
            int n=vector.size();

            if ((nbits/8)+1==n) aux.addElement(shift(1,vector.get(0),8-bitInicialSeguent));
            for(int i=1;i<n;++i){
                aux.addElement(byteOr(shift(0,vector.get(i-1),bitInicialSeguent),shift(1,vector.get(i),8-bitInicialSeguent)));
            }

            nombreRepresentat=getNombre(aux,nbits);
        }
        return new Pair<Integer,Integer>(nombreRepresentat,bitInicialSeguent);
    }

    /***
     *
     * @param value: Vector que contÃ© un char codificat en binari en UTF-8 en 1 o 2 bytes, el seu tamany Ã©s equivalent al nombre de bytes que codifiquen el carÃ cter
     * @return Retorna el carÃ cter corresponent a la codificaciÃ³
     */
    private char getChar(Vector<Byte> value){
        byte[] bytes;
        if (value.size()==1){
            bytes= new byte[] {value.get(0)};
        }
        else{
            bytes= new byte[] {value.get(0), value.get(1)};
        }
        return (new String(bytes, Charset.forName("UTF-8"))).charAt(0);
    }

    /***
     *
     * @param vector: Vector sobre el qual Ã©s vol llegir un byte
     * @param bitInicial: Bit del element 0 del Vector on comenÃ§a la codificaciÃ³ del byte
     * @return Retorna el byte corresponent als primers 8 bits a partir del bitInicial de l'element 0
     */
    private byte getByte(Vector<Byte> vector, int bitInicial){
        byte ByteARetornar;
        if (bitInicial==0) ByteARetornar= vector.get(0);
        else ByteARetornar= byteOr(shift(0,vector.get(0),bitInicial),shift(1,vector.get(1),(8-bitInicial)));
        return ByteARetornar;
    }

    /***
     *
     * @param input: ArrayList de bytes codificats en LZ78
     * @return Retorna un StringBuilder corresponent a la decodificaciÃ³ a nivell de bit utilitzant l'algorisme LZ78 de l'Array de bytes obtingut com a parÃ metre
     */
    public StringBuilder decode(ArrayList<Byte> input) {
        StringBuilder sortida = new StringBuilder();
        int n = input.size();
        // Declarem el diccionari que utilitzarem per decodificar l'input
        Vector<Pair<Integer, Character>> diccionari = new Vector<Pair<Integer, Character>>();
        // Contador del nombre de referencies existents en el diccionari
        int contref = 1;
        int i = 0;
        int bitActual = 0;

        while (i < n - 1 || (i == n - 1 && (log2(contref - 1) + 1) <= 8 - bitActual)) {
            // Obtenim el nombre de bits que codifiquen la referencia a partir del nombre de referencies existents
            int nbits = log2(contref - 1) + 1;

            // Obtenim la referencia codificada en nbits i la guardem en nombreVector
            Vector<Byte> nombreVector = new Vector<>();

            int nIter = (nbits - (8 - bitActual)) / 8;
            if ((nbits - (8 - bitActual)) % 8 > 0) ++nIter;
            nombreVector.addElement(input.get(i));
            while (nIter > 0) {
                ++i;
                nombreVector.addElement(input.get(i));
                --nIter;
            }
            if ((nbits - (8 - bitActual)) % 8 == 0) ++i;

            // Obtenim la referencia corresponent a nombreVector
            Pair<Integer, Integer> aux = llegixNombre(nombreVector, bitActual, nbits);
            int referencia = aux.getElement0();
            bitActual = aux.getElement1();


            // Obtenim el caracter codificat en UTF-8 en 8 o 16 bits i el guardem en charVector

            // Guardarem en l'String frase l'String corresponent segons LZ78 al parell referencia carÃ cter obtinguts o Ãºnicament referencia si arribem al final del vector
            String frase = "";
            Vector<Byte> charVector = new Vector<>();

            if (i < n - 1 || (i == n - 1 && bitActual == 0)) {
                Vector<Byte> auxVector = new Vector<>();
                auxVector.addElement(input.get(i));
                ++i;
                if (bitActual != 0) auxVector.addElement(input.get(i));
                charVector.addElement(getByte(auxVector, bitActual));

                if (charVector.get(0) < 0) {
                    auxVector.clear();
                    auxVector.addElement(input.get(i));
                    ++i;
                    if (bitActual != 0) auxVector.addElement(input.get(i));
                    charVector.addElement(getByte(auxVector, bitActual));
                }

                // Obtenim el carÃ cter corresponent als bits guardats en charVector
                char caracter = getChar(charVector);

                // Afegim la referencia i el caracter al diccionari
                diccionari.addElement(new Pair<Integer, Character>(referencia, caracter));

                // Afegim el carÃ cter a la frase a escriure
                frase = "" + caracter;
            }

            Pair<Integer, Character> auxpair;
            // Busquem en el diccionari la frase que hem d'escriure segons la referencia i la fiquem en l'String frase abans del carÃ cter codificat si hi havia
            while (referencia != 0) {
                auxpair = diccionari.get(referencia - 1);
                referencia = auxpair.getElement0();
                frase = ("" + auxpair.getElement1()).concat(frase);
            }
            // Afegim la frase ja decodificada al text de sortida
            sortida.append(frase);
            // Augmentem el nombre de referencies existents en el diccionari
            ++contref;
        }

        return sortida;
    }
}
