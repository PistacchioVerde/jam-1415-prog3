package JAM;

import java.rmi.RemoteException;

/**
 * eccezione customizzata per oggetti di tipo ADSL
 *
 * @author Andrea Bragagnolo
 */
public class JAMADSLException extends RemoteException {

    /**
     * costruttore dell'eccezione con stringa definita nell'invocazione
     *
     * @param str stringa usata per costruire l'eccezione
     */
    JAMADSLException(String str) {
        super(str);
    }
}
