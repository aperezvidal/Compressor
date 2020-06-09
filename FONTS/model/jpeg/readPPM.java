package model.jpeg;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class readPPM
{
    private String magicNumber;
    private String comment;
    private int width;
    private int height;
    private int maxVal;
    private int rgb[][];

    private BufferedInputStream file;

    /***
     * llegeix un fitxer en format PPM
     * @param fileName Nom del fitxer
     * @throws IOException
     */
    public readPPM(String fileName) throws IOException
    {
        magicNumber = new String();
        width = 0;
        height = 0;
        maxVal = 0;

        // File opening
        file = new BufferedInputStream(new FileInputStream(fileName));

        // Magic number
        magicNumber += (char)(file.read());
        magicNumber += (char)(file.read());

        // Whitespace
        String widthStr = new String();
        comment = new String();
        while (true) {
            int b = file.read();

            if (Character.isWhitespace(b)) {
                continue;
            }

            if (b == (int)('#')) {
                comment += (char)(b);
                while (true) {
                    b = file.read();
                    if (b == (int)('\n')) {
                        break;
                    }
                    comment += (char)(b);
                }
                continue;
            }

            widthStr += (char)(b);
            break;
        }

        // width
        while (true) {
            int b = file.read();
            if ((b >= (int)('0')) && (b <= (int)('9'))) {
                widthStr += (char)(b);
            } else {
                break;
            }
        }
        width = Integer.parseInt(widthStr);

        // Whitespace
        String heightStr = new String();
        while (true) {
            int b = file.read();
            if (Character.isWhitespace(b)) {
                continue;
            }
            heightStr += (char)(b);
            break;
        }

        // height
        while (true) {
            int b = file.read();
            if ((b >= (int)('0')) && (b <= (int)('9'))) {
                heightStr += (char)(b);
            } else {
                break;
            }
        }
        height = Integer.parseInt(heightStr);

        // Whitespace
        String maxValStr = new String();
        while (true) {
            int b = file.read();
            if (Character.isWhitespace(b)) {
                continue;
            }
            maxValStr += (char)(b);
            break;
        }

        // height
        while (true) {
            int b = file.read();
            if ((b >= (int)('0')) && (b <= (int)('9'))) {
                maxValStr += (char)(b);
            } else {
                break;
            }
        }
        maxVal = Integer.parseInt(maxValStr);

        // Whitespace
        file.read();

        // Pixels
        rgb = new int [height][width];

        if (maxVal < 256) {
            // This is the case 1 byte
            for (int rowIndex = 0; rowIndex < height; ++rowIndex) {
                for (int columnIndex = 0; columnIndex < width; ++columnIndex) {
                    int g = file.read();
                    int b = file.read();
                    int r = file.read();

                    rgb[rowIndex][columnIndex] = RGB(r, g, b);
                }
            }
        } else {
            // This is the case 2 byte
            for (int rowIndex = 0; rowIndex < height; ++rowIndex) {
                for (int columnIndex = 0; columnIndex < width; ++columnIndex) {
                    int g = (int)(file.read()) * 256 + (int)(file.read());
                    int b = (int)(file.read()) * 256 + (int)(file.read());
                    int r = (int)(file.read()) * 256 + (int)(file.read());

                    rgb[rowIndex][columnIndex] = RGB(r, g, b);
                }
            }
        }
    }

    public static int RGB(int red, int green, int blue)
    {
        return (0xff000000) | (red << 16) | (green << 8) | blue;
    }

    public String getMagicNumber()
    {
        return magicNumber;
    }

    public String getComment()
    {
        return comment;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getMaxVal()
    {
        return maxVal;
    }
    public int getRed(int i,int j)
    {
        return (getRGBAt(i,j) >> 16) & 0xFF;
    }
    public int getGreen(int i, int j)
    {
        return (getRGBAt(i,j) >> 8) & 0xFF;
    }

    public int getBlue(int i, int j)
    {
        return (getRGBAt(i,j) >> 0) & 0xFF;
    }
    public int[][] getRGBMatrix()
    {
        return rgb;
    }



    public int getRGBAt(int rowIndex, int columnIndex)
    {
        if ((rowIndex < 0) || (columnIndex < 0) || (rowIndex >= height) || (columnIndex >= width)) {
            return -1;
        }
        return rgb[rowIndex][columnIndex];
    }
}

