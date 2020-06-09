package model.jpeg;

public class DriverQuantitzacio
{

    public static void main(String[] args)
    {

		Quantitzacio q = new Quantitzacio();
        int[][][] win = new int[8][8][8];
        int[][][] win2 = new int[8][8][8];
        double[][][] win_tmp = new double[8][8][8];
        double[][][] win_tmp2 = new double[8][8][8];



        for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				for (int k=0; k<8;k++) win_tmp[i][j][k] = win_tmp2[i][j][k] = 2;
			}
		}

        for(int i=0; i<win.length; i++) {
                win[i] = Quantitzacio.quantY(win_tmp[i]);
                win2[i] = Quantitzacio.quantUV(win_tmp[i]);
        }
        for(int i=0; i<win.length; i++) {
            win_tmp[i] = Quantitzacio.iQuantY(win[i]);
            win_tmp2[i] = Quantitzacio.iQuantUV(win2[i]);
        }

    }
}
