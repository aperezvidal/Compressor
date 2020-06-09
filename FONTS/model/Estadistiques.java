package model;

/**
 * @author Marc Jímenez Gimeno
 * Classe per generar estadístiques
 */
public class Estadistiques
{
    private long Start_Time;
    private long End_Time;

    private double UncompressedBytes;
    private double CompressedBytes;


    /***
     * Comença el timer
     */
    public void StartTimer(){
        Start_Time = System.currentTimeMillis();
    }


    /***
     * Atura el timer
     */
    public void StopTimer(){
        End_Time = System.currentTimeMillis();
    }


    /***
     * Assigna uSize al tamany del fitxer sense comprimir
     * @param uSize: tamany del fitxer sense comprimir
     */
    public void uncompressedSize(int uSize){
        UncompressedBytes = uSize;
    }


    /***
     * Assigna cSize al tamany del fitxer comprimit
     * @param cSize: tamany del fitxer comprimit
     */
    public void  compressedSize(int cSize){
        CompressedBytes = cSize;
    }


    /***
     * Funcio per obtenir el temps que s'ha trigat en fer la compressio / descompressio de un fitxer.
     * @return El temps trigat en comprimir / descomprimir.
     */
    public long GetTime(){
        System.out.println("El temps que s'ha trigat a fer la Compressio o Descompressio ha estat: " + (End_Time-Start_Time) + " milisegons.");
        return (End_Time - Start_Time);
    }


    /***
     * Funcio per obtenir el percentatge de millora aconseguit a la compressio.
     * @return Rati de la millora d'espai en Bytes.
     */
    public double GetCompressionRatio(){
        double cRate = UncompressedBytes - CompressedBytes;
        cRate = (cRate/UncompressedBytes)*100;
        System.out.println("El rati de compressio aconseguit ha estat: " + cRate + " %");
        return cRate;
    }


}
