package model.capaPresentacio;

public class controladorPresentacio {

    /** Atributs **/
    private controladorDomini ctrlDomini;
    // declaració private de les vistes

    /** Constructores y mètodes d'inicialització **/
    public controladorPresentacio() {
        controladorPresentacio=new controladorPresentacio();
        //declaració de les vistes
    }

    public void inicialitzarPresentacio(){
        ctrlDomini.inicialitzarCtrlDomini();

        //funcions de les vistes per la pantalla inicial
    }

    /** Métodes de sincronització entre vistes **/


    /** Crides al controlador de Domini **/

    // Preguntar al professor si es millor opera o comprimir/descomprimir
    public void opera(String path, String algorisme, String opcio){
        ctrlDomini.opera(path,algorisme,opcio);
    }

    public void estadistiques(){
        stats= new //lo que siga
        stats=ctrlDomini.estadistiques();
    }
}
