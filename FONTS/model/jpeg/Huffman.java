package model.jpeg;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Huffman
{
    private DCTable DCL, DCC;
    private ACTable ACL, ACC;
    public Huffman() {
        DCL = new DCTable("./DOCS/HuffmanTable/DC_luminance.txt");
        DCC = new DCTable("./DOCS/HuffmanTable/DC_chrominance.txt");
        ACL = new ACTable("./DOCS/HuffmanTable/AC_luminance.txt");
        ACC = new ACTable("./DOCS/HuffmanTable/AC_chrominance.txt");
    }

    /***
     * Donat un vector, codifica la luminància en un String.
     * @param array
     * @return
     */
    public String LumEncoding(int[] array)
    {
        Vector<int[]> data = RLEencoding(array);
        data = entropyCoding(data);
        String result = "";
        for(int i=0; i<data.size(); i++) {
            int x = data.elementAt(i)[0],
                    y = data.elementAt(i)[1],
                    z = data.elementAt(i)[2];
            if(i==0) {
                result += DCL.getCodeWord(x);
                result += VLI.getCode(y);
            } else {
                result += ACL.getCodeWord(x, y);
                result += VLI.getCode(z);
            }
        }
        return result;
    }

    /***
     * Donat un vector, codifica la crominància en un String.
     * @param array
     * @return
     */
    public String ChrEncoding(int[] array)
    {
        Vector<int[]> data = RLEencoding(array);
        data = entropyCoding(data);
        String result = "";
        for(int i=0; i<data.size(); i++) {
            int x = data.elementAt(i)[0],
                    y = data.elementAt(i)[1],
                    z = data.elementAt(i)[2];
            if(i==0) {
                result += DCC.getCodeWord(x);
                result += VLI.getCode(y);
            } else {
                result += ACC.getCodeWord(x, y);
                result += VLI.getCode(z);
            }
        }
        return result;
    }

    /***
     * primer pas per codificar per Huffman
     * @param array
     * @return
     */
    private Vector<int[]> RLEencoding(int[] array)
    {
        Vector<int[]> data = new Vector<int[]>();
        int zeroCnt = 0;
        for(int i=0; i<64; i++) {
            int[] curData = new int[3];
            if(i==0) {

                curData[0] = array[0];
                data.addElement(curData);
            }
            else {
                boolean flag = true;
                for(int j=i; j<64; j++) {
                    if(array[j] != 0)
                        flag = false;
                }
                if(flag) {
                    curData[0] = curData[1] = 0;
                    data.addElement(curData);
                    break;
                }

                if(zeroCnt == 16) {
                    curData[0] = 15;
                    curData[1] = 0;
                    zeroCnt = 0;
                    data.addElement(curData);
                }
                if(array[i]==0)
                    zeroCnt++;
                else {
                    curData[0] = zeroCnt;
                    curData[1] = array[i];
                    zeroCnt = 0;
                    data.addElement(curData);
                }
            }
        }
        return data;
    }

    /***
     * Codifica la entropia
     * @param data
     * @return
     */
    private Vector<int[]> entropyCoding(Vector<int[]> data)
    {
        for(int i=0; i<data.size(); i++) {
            int[] curData = data.elementAt(i);
            if(i==0) {
                int DC = curData[0];
                data.elementAt(0)[0] = VLI.getIndex(DC);
                data.elementAt(0)[1] = DC;
            } else if(data.elementAt(i)[0] !=0 || data.elementAt(i)[1]!=0){
                data.elementAt(i)[2] = data.elementAt(i)[1];
                data.elementAt(i)[1] = VLI.getIndex(data.elementAt(i)[2]);
            }
        }
        return data;
    }

    /***
     * Decodifica la crominància
     * @param data
     * @param line
     * @return
     */
    public String ChrDecoding(String data, int[] line)
    {
        int DCHeadLen = -1;
        for(int rIndex=2; rIndex<11; rIndex++) {
            DCHeadLen = DCC.getCategory(data.substring(0, rIndex));
            if(DCHeadLen>=0) {
                data = data.substring(rIndex);
                break;
            }
        }
        if(DCHeadLen == -1) {
            System.out.println("[INFO] ERROR: CHrDecoding 1!");
        }
        line[0] = VLI.getNum(data.substring(0, DCHeadLen));
        data = data.substring(DCHeadLen);
        int lineLen = 1;
        while(lineLen < 64) {
            Point curP = new Point(-1, -1);
            for(int rIndex = 2; rIndex <=16; rIndex++) {
                curP = ACC.getRunSize(data.substring(0, rIndex));
                if(!(curP.x == -1 && curP.y == -1)) {
                    data = data.substring(rIndex);
                    break;
                }
            }
            if(curP.x == -1 && curP.y == -1) {
                System.out.println("[INFO] ERROR: CHrDecoding 2!");
            }
            int x = curP.x;
            int y = VLI.getNum(data.substring(0, curP.y));
            data = data.substring(curP.y);
            if(x==y && y==0) {
                while(lineLen < 64)
                    line[lineLen++] = 0;
                break;
            }
            for(int i=0; i<x; i++)
                line[lineLen++] = 0;
            line[lineLen++] = y;
        }
        return data;
    }

    /***
     * Decodifica la luminància
     * @param data
     * @param line
     * @return
     */
    public String LumDecoding(String data, int [] line)
    {
        int DCHeadLen = -1;
        for(int rIndex=2; rIndex<9; rIndex++) {
            DCHeadLen = DCL.getCategory(data.substring(0, rIndex));
            if(DCHeadLen>=0) {
                data = data.substring(rIndex);
                break;
            }
        }
        if(DCHeadLen == -1) {
            System.out.println("[INFO] ERROR: LumDecoding 1!");
        }
        line[0] = VLI.getNum(data.substring(0, DCHeadLen));
        data = data.substring(DCHeadLen);
        int lineLen = 1;
        while(lineLen < 64) {
            Point curP = new Point(-1, -1);
            for(int rIndex = 2; rIndex <=16; rIndex++) {
                curP = ACL.getRunSize(data.substring(0, rIndex));
                if(!(curP.x == -1 && curP.y == -1)) {
                    data = data.substring(rIndex);
                    break;
                }
            }
            if(curP.x == -1 && curP.y == -1) {
                System.out.println("[INFO] ERROR: LumDecoding 2!");
            }
            int x = curP.x;
            int y = VLI.getNum(data.substring(0, curP.y));
            data = data.substring(curP.y);
            if(x==y && y==0) {
                while(lineLen < 64)
                    line[lineLen++] = 0;
                break;
            }
            for(int i=0; i<x; i++)
                line[lineLen++] = 0;
            line[lineLen++] = y;
        }
        return data;
    }


}

/***
 * classe per interactuar amb la taula de Huffman
 */
class DCTable
{
    private int[] category;
    private String[] codeWord;
    private String fileName;

    /***
     * Constructora
     * @param fileName
     */
    public DCTable(String fileName)
    {
        category = new int[12];
        codeWord = new String[12];
        this.fileName = fileName;
        init();
    }

    /***
     * inicialitza la classe correctament
     */
    private void init()
    {
        File file = new File(fileName);
        if(file.exists()){
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String lineContent = null;
                int index = 0;
                while((lineContent = br.readLine())!=null){
                    String[] ss = lineContent.split("\\s\\s");
                    category[index] = Integer.parseInt(ss[0]);
                    codeWord[index++] = ss[1];
                }
                br.close();
                fileReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("[INFO] File does not exist");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("[INFO] Io exception");
                e.printStackTrace();
            }
        }
    }


    public String getCodeWord(int index)
    {
        for(int i=0; i<12; i++) {
            if(category[i]==index)
                return codeWord[i];
        }
        return "";
    }


    public int getCategory(String codeWord)
    {
        for(int i=0; i<12; i++) {
            if(this.codeWord[i].equals(codeWord))
                return category[i];
        }
        return -1;
    }
}

/***
 * classe per interactuar amb la taula de Huffman
 */
class ACTable
{
    private Vector<Point> runSize;
    private Vector<String> codeWord;
    private String fileName;
    public ACTable(String fileName) {
        runSize = new Vector<Point>();
        codeWord = new Vector<String>();
        this.fileName = fileName;
        init();
    }
    /***
     * inicialitza la classe correctament
     */
    private void init()
    {
        File file = new File(fileName);
        if(file.exists()){
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                String lineContent = null;
                while((lineContent = br.readLine())!=null){

                    String[] ss = lineContent.split("\\s\\s");
                    runSize.addElement(handleRS(ss[0]));
                    codeWord.addElement(ss[1]);
                }
                br.close();
                fileReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("[INFO] File does not exist");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("[INFO] Io exception");
                e.printStackTrace();
            }
        }
    }

    private Point handleRS(String s)
    {
        int x, y;
        String[] ss = s.split("/");
        if(ss[0].charAt(0) >= 'A')
            x = ss[0].charAt(0)-'A' +10;
        else
            x = ss[0].charAt(0)-'0';
        if(ss[1].charAt(0) >= 'A')
            y = ss[1].charAt(0)-'A' +10;
        else
            y = ss[1].charAt(0)-'0';
        return (new Point(x, y));
    }

    public String getCodeWord(int x, int y)
    {
        for(int i=0; i<runSize.size(); i++) {
            if(runSize.elementAt(i).x==x && runSize.elementAt(i).y==y)
                return codeWord.elementAt(i);
        }
        return "";
    }


    public Point getRunSize(String codeWord)
    {
        for(int i=0; i<this.codeWord.size(); i++) {
            if(this.codeWord.elementAt(i).equals(codeWord))
                return runSize.elementAt(i);
        }
        return (new Point(-1,-1));
    }
}
/***
 * classe per interactuar amb la taula de Huffman
 */
class VLI
{
    public static int getIndex(int num)
    {
        if(num == 0) return 0;
        if(num < 0) num *= -1;
        double result = Math.log(num)/Math.log(2);
        for(int i=(int) (result-5); i <= result+5; i++) {
            if(Math.pow(2, i-1) <= num && num <=Math.pow(2, i)-1)
                return i;
        }
        return 0;
    }


    public static String getCode(int num)
    {
        if(num ==0)return "";
        int index = getIndex(num);
        String result;
        if(num < 0){
            result = Integer.toBinaryString((-num)^(int)(Math.pow(2, index)-1));
        } else {
            result = Integer.toBinaryString(num);
        }
        int p = index-result.length();
        for(int i=0; i<p; i++) {
            result = "0" + result;
        }
        return result;
    }

    public static int getNum(String code)
    {
        if(code.equals(""))return 0;
        int len = code.length();
        int num;
        if(code.charAt(0)=='1') {
            num = Integer.parseInt(code, 2);
        } else {
            String negCode = "";
            for(int i=0; i<len; i++) {
                negCode += code.charAt(i)=='0'?"1":"0";
            }
            num = -Integer.parseInt(negCode, 2);
        }
        return num;
    }
}
