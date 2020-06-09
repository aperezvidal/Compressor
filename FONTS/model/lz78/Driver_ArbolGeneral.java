package model.lz78;


/***
 * Driver que servix per provar la classe ArbolGeneral
 * @author Marc Simó Guzmán
 */
public class Driver_ArbolGeneral
{
    /***
     * Aquest driver serveix per veure el funcionament de la classe ArbolGeneral, i funciona de la següent manera:
     *  Entrada: S'introdueix el nombre de fills (nfills) i l'altura de l'arbre (altura)
     *  Sortida (si args[0]!="Usage" o l'entrada és vàlida): Generem un arbre d'altura altura amb nfills nodes en cada altura que són fills del fill (nfill-1) o de l'arrel en l'altura 0
     *  Sortida (si args[0]=="Usage" o l'entrada no és vàlida): escribim per la sortida d'escritura estàndard l'Usage
     */
    public static void main(String[] args)
    {
        boolean usage=(!args[0].equals("Usage") && args.length==2);
        if (usage) {
            ArbolGeneral arbre= new ArbolGeneral();
            int nfills=Integer.parseInt(args[0]);
            int altura=Integer.parseInt(args[0]);
            if (nfills>0 && altura>0){
                for (int i=0;i<altura;++i) {
                    for (int j=0;j<nfills;++j){
                        arbre.addFill(j,(char) (j+'a'));
                    }
                    arbre.vesFill((char) (nfills-1+'a'));
                }
                arbre.vesArrel();
                for (int i=0;i<altura;++i) {
                    String fills="";
                    for (int j=0;j<nfills;++j){
                            node aux=arbre.getFill((char)(j+'a'));
                            fills+=aux.getCaracter()+" ";
                    }
                    arbre.vesFill((char)('a'+nfills-1));
                    System.out.println(fills);
                }
            }
            else usage=false;
        }
        if (!usage) System.out.println("Usage: java  package......Driver_ArbolGeneral nfills altura");

    }
}
