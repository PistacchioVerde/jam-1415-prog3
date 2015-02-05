package JAM;

/**
 * eccezione utilizzata per riferire l'interruzione del thread senza usare
 * InterruptedException
 *
 * @author Andrea Bragagnolo
 */
public class JAMBehaviourInterruptedException extends InterruptedException {

    /**
     * costruttore che crea un'eccezione con una stringa predefinita
     */
    public JAMBehaviourInterruptedException() {
        super("Thread interrotto");
    }
}
