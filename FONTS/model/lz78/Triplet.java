package model.lz78;

/***
 * Classe Triplet (Tupla de 3 elements amb noms predeterminats)
 * @author Marc Simó Guzmán
 * @param <K>: Objecte 0
 * @param <V>: Objecte 1
 * @param <Z>: Objecte 2
 */
public class Triplet<K, V, Z> {

        private final K element0;
        private final V element1;
        private final Z element2;

        public static <K, V, Z> Triplet<K, V, Z> createTriplet(K element0, V element1, Z element2) {
            return new Triplet<K, V, Z>(element0, element1, element2);
        }

        public Triplet(K element0, V element1, Z element2) {
            this.element0 = element0;
            this.element1 = element1;
            this.element2 = element2;
        }

        public K getElement0() {
            return element0;
        }

        public V getElement1() {
            return element1;
        }

        public Z getElement2() {
            return element2;
        }

}
