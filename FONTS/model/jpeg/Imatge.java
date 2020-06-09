package model.jpeg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import java.awt.Color;
import java.io.File;
import java.util.Vector;

/***
 * Classe Imatge
 */
public class Imatge
{
    private double[][] Y, U, V;
    //private int[][] img;
    public static Huffman h = new Huffman();
    public static String strY = "", strU = "", strV = "";
    private static DCT dct = new DCT();


    /***
     * Creadora de la classe Imatge.
     *
     * <ul>
     * <li>Si q = 0 (comprimeix):
     * <li>1.crea una nova instancia de readPPM per a llegir un arxiu PPM
     *<li>2.Canvia l'espai de color de la imatge, de RGB a YUV
     *<li>3.Executa encode() per Y, U i V respectivament.
     *<li>4.Recorre cada matriu de cada eix mitjançant la funció zigzag()
     *<li>5.Codifica cada eix per Huffman a través de LumEncoding (eix Y) i ChrEncoding (eixos U i V)
     *<li>6.Finalment s'escriuen els 3 strings de cada eix comprimits en un arxiu
     *<li></li>
     *
     *<li>Si q = 1 (descomprimeix):</li>
     *<li>1.llegeix l'arxiu comprimit</li>
     *<li>2.Descodifica cada eix per Huffman a través de LumDecoding (eix Y) i ChrDecoding (eixos U i V)</li>
     *<li>3.Recorre cada matriu inversament de cada eix mitjançant la funció antizigzag()</li>
     *<li>4.Executa decode() per Y, U i V respectivament.</li>
     *<li>5.Canvia l'espai de color de la imatge, de YUV a RGB.</li>
     *<li>6.Escriu la imatge en format PPM.</li>
     * </ul>
     *
     * @param nom Nom del fitxer a descomprimir
     * @param q   Si q = 0 comprimeix, altrament si q = 1 descomprimeix.
     */
    public Imatge(String nom, int q)
    {

        // 0 compressió
        // 1 descompressió


        if (q == 0) {
            //compressió

            //Carregar Imatge
            readPPM ppm = null;
            try {
                ppm = new readPPM(nom);
            } catch (IOException e) {
                e.getCause();
                e.printStackTrace();
                System.out.println("error");

            }

            int width = ppm.getWidth();
            int height = ppm.getHeight();
            int img[][] = ppm.getRGBMatrix();

            //RGB-->YUV

            System.out.println("començo rgb-->yuv");

            Y = new double[img.length][img[1].length];
            U = new double[img.length / 2][img[1].length / 2];
            V = new double[img.length / 2][img[1].length / 2];
            for (int i = 0; i < img.length / 2; i++)
                for (int j = 0; j < img[1].length / 2; j++) {
                    U[i][j] = V[i][j] = 0;
                }
            for (int i = 0; i < img.length; i++)
                for (int j = 0; j < img[1].length; j++) {
                    int r = ppm.getRed(i, j);
                    int g = ppm.getGreen(i, j);
                    int b = ppm.getBlue(i, j);
                    Y[i][j] = 0.299 * r + 0.587 * g + 0.114 * b;
                    U[i / 2][j / 2] = -0.169 * r - 0.331 * g + 0.5 * b + 128;
                    V[i / 2][j / 2] = 0.5 * r - 0.419 * g - 0.081 * b + 128;
                }

            //System.out.println("hola");
            double[][] mat = Y.clone();
            int[][][] winY = encode(mat, 0);
            mat = U.clone();
            int[][][] winU = encode(mat, 1);
            mat = V.clone();
            int[][][] winV = encode(mat, 1);

            int[][] Y1d = zigzag(winY);
            int[][] U1d = zigzag(winU);
            int[][] V1d = zigzag(winV);


            //Huffman

            for (int i = 0; i < Y1d.length; i++) {
                strY += h.LumEncoding(Y1d[i]);
            }
            for (int i = 0; i < U1d.length; i++) {
                strU += h.ChrEncoding(U1d[i]);
                strV += h.ChrEncoding(V1d[i]);
            }
            //System.out.println(strY);

            double len = strY.length() + strU.length() + strV.length();
            System.out.println(len);
            write2File("./DOCS/test_comp.ppm", strY, strU, strV, "a","b");
        }

        else if(q == 1){

            //descompressió
            BufferedInputStream file;


            //Carregar Imatge Comprimida
            try {
                file = new BufferedInputStream(new FileInputStream(nom));
                byte[] buff = file.readAllBytes();
                String img = new String(buff);
                //separar y,u,v
                String[] s1 = img.split("a");
                String y = s1[0];
                String uv[] = s1[1].split("b");
                if(strY == y){
                    System.out.println("y es igual");
                }
                //System.out.println(y);
                //System.out.println();
                //System.out.println(strY);
                strY = s1[0];
                strU = uv[0];
                strV = uv[1];

                //write2File("./test_comp2.ppm", y, uv[0], uv[1], "a","b");

            }
            catch (IOException i){
                i.printStackTrace();
            }
            Vector<int[]> Y1d = new Vector<int[]>();
            Vector<int[]> U1d = new Vector<int[]>();
            Vector<int[]> V1d = new Vector<int[]>();
            System.out.println("començo a decodificar x huffman");


            while(!strY.equals("")) {
                int[] line = new int[64];
                strY = h.LumDecoding(strY, line);
                Y1d.addElement(line);
            }
            System.out.println("y ok");
            while(!strU.equals("")) {
                int[] line = new int[64];
                strU = h.ChrDecoding(strU, line);
                U1d.addElement(line);
            }
            System.out.println("u ok");
            while(!strV.equals("")) {
                int[] line = new int[64];
                strV = h.ChrDecoding(strV, line);
                V1d.addElement(line);
            }
            System.out.println("chrdecode ok");

            System.out.println("començo antizigzag");
            int[][][] Y2d = antizigzag(Y1d);
            System.out.println("antizigzag1");
            int[][][] U2d = antizigzag(U1d);
            System.out.println("antizigzag2");
            int[][][] V2d = antizigzag(V1d);
            System.out.println("antizigzag ok");

            double[][] Y = decode(Y2d, 0);
            double[][] U = decode(U2d, 1);
            double[][] V = decode(V2d, 1);
            System.out.println("decode ok");

            //yuv-->rgb
            Color[][] pic = new Color[Y.length][Y.length];
            for (int i = 0; i < Y.length; i++) {
                for (int j = 0; j < Y[0].length; j++) {
                    double y = Y[i][j];
                    double u = U[i / 2][j / 2];
                    double v = V[i / 2][j / 2];

                    int r,g,b;
                    r = (int) (y+1.13983*(v-128));
                    g = (int) (y-0.39465*(u-128)-0.58060*(v-128));
                    b = (int) (y+2.03211*(u-128));
                    r=r>255?255:r<0?0:r;
                    g=r>255?255:g<0?0:g;
                    b=b>255?255:b<0?0:b;

                    if(g > 255) g = 255;
                    pic[i][j] = new Color(r, g, b);

                }
            }

            writeOutFile(pic,"./DOCS/descompressio.ppm");




        }


    }


    /***
     * Parteix la matriu en blocs de 8x8 i els guarda en un vector.
     * Després aplica la DCT i quantitza per cada bloc.
     * @param matrix matriu que representa els valors de l'eix de color per cada pixel
     * @param type 0 per quantificar Y i 1 per quantificar U i V
     * @return
     */
    public  int[][][] encode(double[][] matrix, int type)
    {

        //blocs 8x8
        double[][][] win_tmp = new double[matrix.length/8*matrix[1].length/8][8][8];
        int[][][] win = new int[matrix.length/8*matrix[1].length/8][8][8];
        for(int i=0; i<matrix.length; i+=8) {
            for(int j=0; j<matrix[0].length; j+=8) {
                int index = i*matrix[0].length/64 + j/8;
                for(int k=0; k<8; k++)
                    for(int l=0; l<8; l++) {
                        win_tmp[index][k][l] = matrix[i + k][j + l];
                        //System.out.printf("index=%d, (k,l)=(%d,%d), (i+k,j+l)=(%d,%d)\n", index, k, l, i+k, j+l);
                    }
            }
        }

        for(int i=0; i<win_tmp.length; i++) {
            //Aplicar DCT
            win_tmp[i] = DCT.fast_fdct(win_tmp[i]);
            //Quantitzar
            if(type==0)
                win[i] = Quantitzacio.quantY(win_tmp[i]);
            else
                win[i] = Quantitzacio.quantUV(win_tmp[i]);

        }
        return win;
    }

    /***
     * A partir d'un vector de blocs, es desquantitzen, s'hi fa la DCT inversa,
     * i finalment es retorna en de forma de matriu de cada eix de color(pot ser Y,U o V).
     * @param matrix vector de blocs de 8x8
     * @param type 0 per desquantificar Y i 1 per desquantificar U i V
     * @return
     */
    public double[][] decode(int[][][] matrix, int type)
    {
        double[][][] win_tmp = new double[matrix.length][matrix[0].length][matrix[0][0].length];
        for(int i=0; i<win_tmp.length; i++) {
            if(type==0)
                win_tmp[i] = Quantitzacio.iQuantY(matrix[i]);
            else
                win_tmp[i] = Quantitzacio.iQuantUV(matrix[i]);
            win_tmp[i] = DCT.fast_idct(win_tmp[i]);
        }
        int m = type==0?256:128;
        double[][] pic = new double[m][m];
        for(int i=0; i<pic.length; i+=8) {
            for(int j=0; j<pic[0].length; j+=8) {
                int index = i*pic[0].length/64 + j/8;
                for(int k=0; k<8; k++)
                    for(int l=0; l<8; l++) {
                        pic[i + k][j + l] = win_tmp[index][k][l];
                    }
            }
        }
        return pic;
    }

    /***
     * Recorre un vector de matrius de 8x8 en zigzag (cada una).
     * Retorna una matriu
     * @param wins
     * @return
     */
    public static int[][] zigzag(int[][][] wins)
    {

        int[][] data1d = new int[wins.length][8 * 8];
        for (int i = 0; i < wins.length; i++) {
            if (i == 0)
                data1d[0][0] = wins[0][0][0];
            else
                data1d[i][0] = wins[i][0][0] - wins[i - 1][0][0];
            int index = 1, j = 0, k = 1;
            int deltaJ = 1, deltaK = -1;
            while (!(j > 7 || k > 7)) {
                data1d[i][index++] = wins[i][j][k];
                if (j == 0) {
                    deltaJ = 1;
                    deltaK = -1;
                    if (k % 2 == 0) {
                        k++;
                        continue;
                    }
                }
                if (k == 0) {
                    deltaJ = -1;
                    deltaK = 1;
                    if (j % 2 != 0) {
                        j++;
                        continue;
                    }
                }
                k += deltaK;
                j += deltaJ;
            }
        }
        return data1d;
    }

    /***
     *Donat un vector de recorreguts en zigzag, es recorren inversament
     * i es retornen en un vector de blocs de 8x8
     * @param data1d vector de recorreguts en zigzag
     * @return
     */
    public static int[][][] antizigzag(Vector<int[]> data1d)
    {
        int[][][] wins = new int[data1d.size()][8][8];
        for (int i = 0; i < wins.length; i++) {
            if (i == 0)
                wins[0][0][0] = data1d.elementAt(0)[0];
            else
                wins[i][0][0] =wins[i - 1][0][0]  + data1d.elementAt(i)[0];
            int index = 1, j = 0, k = 1;
            int deltaJ = 1, deltaK = -1;
            while (!(j > 7 || k > 7)) {
                wins[i][j][k] = data1d.elementAt(i)[index++];
                if (j == 0) {
                    deltaJ = 1;
                    deltaK = -1;
                    if (k % 2 == 0) {
                        k++;
                        continue;
                    }
                }
                if (k == 0) {
                    deltaJ = -1;
                    deltaK = 1;
                    if (j % 2 != 0) {
                        j++;
                        continue;
                    }
                }
                k += deltaK;
                j += deltaJ;
            }
        }
        return wins;
    }

   /* public static void writeImage(Color[][] pic, String name, String type) {
        System.out.println("[INFO] Begin Write");
        try {
            BufferedImage bi = new BufferedImage(pic.length, pic[0].length, BufferedImage.TYPE_INT_RGB);
            for(int i=0; i<pic.length; i++)
                for(int j=0; j<pic[0].length; j++) {
                    bi.setRGB(i ,j, pic[i][j].getRGB());
                }
            Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(type);// 定义图像格式
            ImageWriter writer = it.next();
            ImageOutputStream ios;
            ios = ImageIO.createImageOutputStream(new File("./" + name + "." + type));
            writer.setOutput(ios);
            writer.write(bi);
            bi.flush();
            ios.flush();
            System.out.println("[INFO] Write Success");
        } catch (IOException e) {
			System.out.println(c);
            e.printStackTrace();
        }

    }*/




    //writeOutFile(ppm.surprise(), height, width, fileName + "_surprise.ppm");

    /***
     * Escriu una imatge en format PPM.
     * @param pic Matriu de colors
     * @param name Nom del fitxer
     */
    public static void writeOutFile(Color[][] pic, String name)
    {
        PrintWriter PW = null;
        try {
            PW = new PrintWriter(new File(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int height = pic.length;
        int width = pic[0].length;


        PW.println("P3");
        PW.println(width + " " + height);
        PW.println("255");
        PW.println("# " + name);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                PW.print(pic[i][j].getRed() + "\t");
                PW.print(pic[i][j].getGreen() + "\t");
                PW.println(pic[i][j].getBlue());
            }
        }
        PW.close();
        return;
    }


    /***
     * Escriu Strings amb un separador a un fitxer
     * @param fileName Nom del fitxer
     * @param s1 Eix Y
     * @param s2 Eix U
     * @param s3 Eix V
     * @param a  Separador 1
     * @param b  Separador 2
     */
    public static void write2File(String fileName, String s1,String s2,String s3,String a, String b)
    {
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        try {
            outSTr = new FileOutputStream(new File("./" + fileName));
            Buff = new BufferedOutputStream(outSTr);
            Buff.write(s1.getBytes());
            Buff.flush();
            Buff.write(a.getBytes());
            Buff.flush();
            Buff.write(s2.getBytes());
            Buff.flush();
            Buff.write(b.getBytes());
            Buff.flush();
            Buff.write(s3.getBytes());
            Buff.flush();

            Buff.close();

            //System.out.println("escric");

        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("no escric");
        }
    }

}



