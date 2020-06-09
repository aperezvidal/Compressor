package model;

import model.capaPresentacio.controladorPresentacio;

public class Main {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    controladorPresentacio main= new controladorPresentacio();
                    main.inicialitzarPresentacio();
                }
            }
        );
    }
}
