package JAM;

/**
 * eccezione utilizzata nel metodi readMessage quando non viene ritrovato un
 * messaggio corrispondente ai parametri di ricerca
 *
 * @author Andrea Bragagnolo
 */
public class JAMMessageBoxException extends Exception {

    /**
     * costruttore che genera una nuova eccezione con una stringa predefinita
     */
    public JAMMessageBoxException() {
        super("Nessun messaggio corrispondente ai criteri di ricerca.");
    }
}
