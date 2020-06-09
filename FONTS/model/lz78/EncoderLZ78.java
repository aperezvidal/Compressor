package model.lz78;

import model.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Vector;

/***
 * Codificador de l'algorisme LZ78
 * @author Marc Simó Guzmán
 */
public class EncoderLZ78 {

    /***
     * Constructora sense parÃ metres
     */
    public EncoderLZ78(){}

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
        if (x==0) return 0;
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
     * @param ByteEnUso: Byte en el qual Ã©s comenÃ§arÃ  a escriure el char
     * @param bitsOcupats: Nombre de bits del ByteEnUso que ja estÃ n sent utilitzats
     * @param value: CarÃ cter que Ã©s vol escriure en binari codificat en UTF-8 a continuaciÃ³ de l'Ãºltim bit ocupat de ByteEnUso
     * @return Retorna un Pair que contÃ© un vector dels bytes que ja tenen els seus 8 bits plens i estÃ¡n llestos per ser escrits, i un nou ByteEnUso amb la codificaciÃ³ binaria sobrant (en cas de no sobrar res es retorna un byte de valor 0 com a nou ByteEnUso)
     */
    private Pair<Byte,Vector<Byte>> byteAEscriureChar(Byte ByteEnUso, int bitsOcupats, char value){
        byte[] aux=(""+value).getBytes(Charset.forName("UTF-8"));   //bytes que conformen el caracter
        Vector<Byte> BytesAEscriure= new Vector<>();

        BytesAEscriure.addElement(byteOr(ByteEnUso,shift(1,aux[0],bitsOcupats)));
        ByteEnUso=shift(0,aux[0],8-bitsOcupats);

        if (aux.length==2){
            BytesAEscriure.addElement(byteOr(ByteEnUso,shift(1,aux[1],bitsOcupats)));
            ByteEnUso=shift(0, aux[1],8-bitsOcupats);
        }
        return new Pair<Byte,Vector<Byte>>(ByteEnUso,BytesAEscriure);
    }

    /***
     *
     * @param ByteEnUso: Byte en el qual Ã©s comenÃ§arÃ  a escriure l'enter
     * @param bitsOcupats: Nombre de bits del ByteEnUso que ja estÃ n sent utilitzats
     * @param value: Nombre que Ã©s vol escriure en binari a continuaciÃ³ de l'Ãºltim bit ocupat de ByteEnUso
     * @param nbits: nombre de bits que s'utilitzarÃ n per escriure l'enter
     * @return Retorna un Triplet que contÃ© un vector dels bytes que ja tenen els seus 8 bits plens i estÃ¡n llestos per ser escrits, un nou ByteEnUso amb la codificaciÃ³ binaria sobrant (en cas de no sobrar res es retorna un byte de valor 0 com a nou ByteEnUso), i els bitsOcupats del nou ByteEnUso
     */
    private Triplet<Byte,Integer,Vector<Byte>> bytesAEscriureInt(Byte ByteEnUso, int bitsOcupats, int value, int nbits){
        byte[] aux= ByteBuffer.allocate(4).putInt(value).array();
        Vector<Byte> bytesAEscriure=new Vector<Byte>();

        int n=aux.length;   // Per si cambiem value a double en lloc d'enter
        int bitActual= (8-(nbits%8))%8;     // bit a partir del qual comenÃ§em a escriure
        // Calculem r -> n-r Ã©s on estÃ n els primers bits a escriure
        int r= nbits/8;
        if (nbits%8!=0) r+=1;

        // IntroduÃ¯m la part que ens interessa del primer byte
        if (bitActual>bitsOcupats){
            ByteEnUso=byteOr(ByteEnUso,shift(0,aux[n-r],bitActual-bitsOcupats));
            bitsOcupats=bitsOcupats+(8-bitActual);
        }
        else {
            ByteEnUso=byteOr(ByteEnUso,shift(1,aux[n-r],bitsOcupats-bitActual));
            bytesAEscriure.addElement(ByteEnUso);
            ByteEnUso=shift(0,aux[n-r],(8-bitsOcupats)+bitActual);
            bitsOcupats=bitsOcupats-bitActual;
        }
        --r;

        // Escribim la resta de bytes complets, per tant ja no necessitarem bitActual i bitsOcupats es mantindrÃ  igual
        while(r>0){
            ByteEnUso=byteOr(ByteEnUso,shift(1,aux[n-r],bitsOcupats));
            bytesAEscriure.addElement(ByteEnUso);
            ByteEnUso=shift(0,aux[n-r],8-bitsOcupats);
            --r;
        }
        return new Triplet<Byte,Integer,Vector<Byte>>(ByteEnUso, bitsOcupats,bytesAEscriure);
    }


    /***
     *
     * @param input: Text de carÃ cters codificables en UTF-8 que es vol codificar en LZ78
     * @return Retorna un ArrayList de Byte corresponent a la codificaciÃ³ a nivell de bit del text d'entrada utilitzant l'algorisme LZ78
     */
    public ArrayList<Byte> encode(StringBuilder input){
        ArrayList<Byte> sortida=new ArrayList<>();
        int n= input.length();
        // Declarem l'arbre N-ari que ens ajudarÃ  a codificar el text d'entrada amb LZ78
        ArbolGeneral arbre= new ArbolGeneral();
        // Contador del nombre de referencies existents en el diccionari de codificaciÃ³/decodificaciÃ³
        int contref=1;
        // En aquestes variables guardarem el byte amb part codificada pero no complet i el nombre de bits d'este que ja estan sent utilitzats
        byte ByteEnUso=0;
        int bitsOcupats=0;

        for (int i=0;i<n; ++i){
            //Llegim un nou carÃ cter del text d'entrada
            char caracter=input.charAt(i);
            if (arbre.teFill(caracter)){
                // Si el carÃ cter no conforma una frase no guardada en el diccionari, llegim el segÃ¼ent carÃ cter
                arbre.vesFill(caracter);
            }
            else{
                // Si el carÃ cter conforma una nova frase no guardada en el diccionari, afegim la frase al diccionari i actualitzem l'ArrayList de sortida
                // Inserim un nou fill amb la nova referencia i li assignem el carÃ cter llegit
                // El seu pare i nodeActual serÃ  el carÃ cter referenciat per aquesta entrada
                arbre.addFill(contref,caracter);

                // Obtenim el nombre de bits amb els quals codificar la referencia
                int nbits=log2(contref-1)+1;

                // Codifiquem la referencia guardada en el nodeActual en nbits i l'afegim a sortida
                Triplet<Byte,Integer, Vector<Byte>> aux = bytesAEscriureInt(ByteEnUso,bitsOcupats,arbre.getReferencia(),nbits);
                ByteEnUso=aux.getElement0();
                bitsOcupats=aux.getElement1();
                sortida.addAll(aux.getElement2());

                // Codifiquem el carÃ cter en UTF-8 i l'afegim a sortida
                Pair<Byte, Vector<Byte>> auxChar= byteAEscriureChar(ByteEnUso,bitsOcupats,caracter);
                ByteEnUso=auxChar.getElement0();
                sortida.addAll(auxChar.getElement1());

                // Tornem a l'arrel i augmentem el nombre de referencies existents
                arbre.vesArrel();
                ++contref;
            }
        }
        //Si en acabar el bucle ens quedem amb una referencia solta i sense caracter nou escribim la referencia
        if (arbre.getReferencia()!=0){
            // Escribim la referencia
            int nbits = log2(contref - 1) + 1;

            Triplet<Byte, Integer, Vector<Byte>> aux = bytesAEscriureInt(ByteEnUso, bitsOcupats, arbre.getReferencia(), nbits);
            ByteEnUso = aux.getElement0();
            bitsOcupats = aux.getElement1();
            sortida.addAll(aux.getElement2());
        }
        // Escribim els bits finals guardats al ByteEnUso si hi han
        if (bitsOcupats!=0) sortida.add(ByteEnUso);
        return sortida;
    }
}
