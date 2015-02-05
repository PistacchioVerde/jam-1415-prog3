package JAM;

import java.rmi.*;
import java.util.*;

/**
 * L'interfaccia <code>ADSL</code> ha lo scopo di identificare le classi adatte
 * a "pubblicare" i <code>RemoteMessageBox</code> degli agenti presenti nella
 * piattaforma in un dato momento
 *
 * @author Andrea Bragagnolo
 */
public interface ADSL extends Remote {

    /**
     * Restituisce una lista di riferimenti ad oggetti (remoti) di tipo
     * RemoteMessageBox i cui proprietari sono uguali a agentID
     *
     * @param agentID identifica il proprietario dell'oggetto RemoteMessageBox
     * @return una lista di oggetti remoti con proprietario agentID
     * @throws RemoteException
     */
    public List<RemoteMessageBox> getRemoteMessageBox(AgentID agentID) throws RemoteException;

    /**
     * Richiede l'inserimento di messageBox presso l'ADSL, se l'elemento e`
     * gia presente non viene eettuata alcuna operazione e viene lanciata
     * un'opportuna eccezione
     *
     * @param messageBox identifica la RemoteMessageBox da inserire nell'ADSL
     * @throws RemoteException
     */
    public void insertRemoteMessageBox(RemoteMessageBox messageBox) throws RemoteException;

    /**
     * Richiede la cancellazione dell'oggetto RemoteMessageBox presente presso
     * l'ADSL di proprieta dell'agente agentID. Se l'elemento non e` presente
     * non viene eettuata alcuna operazione e viene lanciata un'opportuna
     * eccezione
     *
     * @param agentID indentifica il proprietario dell'oggetto RemoteMessageBox
     * da cancellare
     * @throws RemoteException
     */
    public void removeRemoteMessageBox(AgentID agentID) throws RemoteException;

}
