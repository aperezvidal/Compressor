package model.lz78;

import java.util.*;

/***
 * Classe que representa un node d'un Arbre N-ari
 * @author Marc Simó Guzmán
 */
class node
{
    //Atributs
    private int referencia;
    private char caracter;
    private node pare;
    private HashMap<Character,node> hijos= new HashMap<Character,node>();

    //Constructores

    /***
     * Es crea un nou node amb els paràmetres indicats
     *
     * @param referencia: referencia del nou node
     * @param caracter: caracter del nou node
     * @param pare: node pare del nou node
     */
    public node(int referencia, char caracter, node pare)
    {
        this.referencia=referencia;
        this.caracter=caracter;
        this.pare=pare;
    }

    /***
     * Es crea un nou node amb els paràmetres indicats
     * @param pare: node pare del nou node
     */
    public node(node pare)
    {
        this.pare=pare;
    }

    /***
     * Es crea un nou node inicialitzat amb referencia=0 i pare=null
     */
    public node()
    {
        // Aquest seria l'arrel
        this.referencia=0;
        this.pare=null;
    }

    //Metodes
    public int getReferencia()
    {
        return referencia;
    }

    public void setReferencia(int referencia)
    {
        this.referencia=referencia;
    }

    public char getCaracter()
    {
        return caracter;
    }

    public void setCaracter(char caracter)
    {
        this.caracter=caracter;
    }

    public boolean teFills()
    {
        return !hijos.isEmpty();
    }

    public boolean teFill(char caracter)
    {
        return hijos.containsKey(caracter);
    }

    public node getFill(char caracter)
    {
        // retorna null en cas de no tenir el fill de key caracter
        return hijos.get(caracter);
    }

    public HashMap<Character,node> getFills()
    {
        // retorna null en cas de no tenir el fill de key caracter
        return hijos;
    }

    public void addFill(int referencia, char caracter)
    {
        hijos.put(caracter,new node(referencia,caracter,this));
    }

    public boolean tePare()
    {
        return !(pare==null);
    }

    public node getPare()
    {
        return pare;
    }

    public void setPare(node pare)
    {
        this.pare=pare;
    }
}

/***
 * Classe que representa un Arbre N-ari
 */
public class ArbolGeneral
{
    //Atributs
    private node Arrel;
    private node NodeActual;

    //Constructores

    /***
     * Inicialitzem l'arbre amb un únic node arrel de referencia 0 i pare null
     */
    public ArbolGeneral()
    {
        Arrel= new node();
        NodeActual=Arrel;
    }

    //Metodes
    public void vesArrel()
    {
        NodeActual=Arrel;
    }

    public void vesFill(char caracter)
    {
        NodeActual=NodeActual.getFill(caracter);
    }

    public void vesPare()
    {
        NodeActual=NodeActual.getPare();
    }


    public int getReferencia()
    {
        return NodeActual.getReferencia();
    }

    public void setReferencia(int referencia)
    {
        NodeActual.setReferencia(referencia);
    }

    public char getCaracter()
    {
        return NodeActual.getCaracter();
    }

    public void setCaracter(char caracter)
    {
        NodeActual.setCaracter(caracter);
    }

    public boolean teFills()
    {
        return NodeActual.teFills();
    }

    public boolean teFill(char caracter)
    {
        return NodeActual.teFill(caracter);
    }

    public node getFill(char caracter)
    {
        // retorna null en cas de no tenir el fill de key caracter
        return NodeActual.getFill(caracter);
    }

    public HashMap<Character,node> getFills()
    {
        // retorna null en cas de no tenir el fill de key caracter
        return NodeActual.getFills();
    }

    public void addFill(int referencia, char caracter)
    {
        NodeActual.addFill(referencia,caracter);
    }

    public boolean tePare()
    {
        return NodeActual.tePare();
    }

    public node getPare()
    {
        return NodeActual.getPare();
    }
}
