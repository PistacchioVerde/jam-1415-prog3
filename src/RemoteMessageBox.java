package JAM;

import java.rmi.*;

/**
 * interfaccia per la creazione di una MessageBox remota
 *
 * @author Andrea Bragagnolo
 */
public interface RemoteMessageBox extends Remote {

    /**
     * metodo per scrivere un messaggio su una MessageBox
     *
     * @param m messaggio da scrivere sulla MessageBox
     * @throws RemoteException
     */
    public void writeMessage(Message m) throws RemoteException;

    /**
     * metodo per leggere il primo messaggio della MessageBox
     *
     * @return Message messaggio letto
     * @throws java.rmi.RemoteException
     * @throws java.lang.InterruptedException
     */
    public Message readMessage() throws InterruptedException, RemoteException;

    /**
     * metodo utilizzato per conosce l'owner di una certa MessageBox
     *
     * @return PersonalAgentID owner della MessageBox su cui e` invocato il
     * metodo
     * @throws RemoteException
     */
    public PersonalAgentID getOwner() throws RemoteException;

}
