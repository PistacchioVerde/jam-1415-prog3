package JAM;

import java.rmi.RemoteException;

/**
 * la classe estende MessageBoxNoSync e implementa RemoteMessageBox. E`
 * utilizzata per rendere synchronized i metodi relativi alle MessageBox
 *
 * @author Andrea Bragagnolo
 */
public class MessageBox extends MessageBoxNoSync implements RemoteMessageBox {

    /**
     * costruttore della MessageBox che inizializza la lista di Message vuota
     *
     * @param owner PersonalAgentID identifica il proprietario della MessageBox
     * @throws Exception
     */
    public MessageBox(PersonalAgentID owner) throws RemoteException {
        super(owner);
    }

    /**
     * metodo atto all'identificazione dell'owner di una certa MessageBox
     *
     * @return PersonalAgentID owner della MessageBox
     */
    public PersonalAgentID getOwner() {
        return super.getOwner();
    }

    /**
     * richiamo del metodo readMessage(AgentID sender, Performative perf) con un
     * agente generico e nessuna performativa
     *
     * @return messaggio che corrisponde ai parametri
     * @throws InterruptedException
     */
    public Message readMessage() throws InterruptedException {
        return readMessage(new GenericAgentID(), null);
    }

    /**
     * richiamo del metodo readMessage(AgentID sender, Performative perf) con un
     * AgentID definito da parametro e nessuna performativa
     *
     * @param sender sender del messaggio ricercato
     * @return messaggio che corrisponde ai parametri
     * @throws InterruptedException
     */
    public Message readMessage(AgentID sender) throws InterruptedException {
        return readMessage(sender, null);
    }

    /**
     * richiamo del metodo readMessage(AgentID sender, Performative perf) con un
     * agente generico e una performativa definita da parametro
     *
     * @param perf performativa del messaggio ricercato
     * @return messaggio che corrisponde ai parametri
     * @throws InterruptedException
     */
    public Message readMessage(Performative perf) throws InterruptedException {
        return readMessage(new GenericAgentID(), perf);
    }

    /**
     * cerca e rimuove il primo messaggio (piu` vecchio) di un particolare
     * agente con una certa performativa. Richiama il metodo corrispondente di
     * MessageBoxNoSync e notifica tutti gli eventuali thread in wait. Nel caso
     * non vi sia il messaggio ricercato invoca ilk metodo wait()
     *
     * @param sender sender del messaggio ricercato
     * @param perf performativa del messaggio ricercato
     * @return messaggio che corrisponde ai parametri
     * @throws java.lang.InterruptedException
     */
    public synchronized Message readMessage(AgentID sender, Performative perf) throws InterruptedException {
        while (true) {
            try {
                Message m = super.readMessage(sender, perf);
                notifyAll();
                return m;
            } catch (JAMMessageBoxException e) {
                wait();
            }
        }
    }

    /**
     * richiamo del metodo isThereMessage(AgentID sender, Performative perf) con
     * un agente generico e una performativa nulla
     *
     * @return boolean true se il messaggio e` presente, false altrimenti
     * @throws Exception
     */
    public boolean isThereMessage() throws Exception  {
        return isThereMessage(new GenericAgentID(), null);
    }

    /**
     * richiamo del metodo isThereMessage(AgentID sender, Performative perf) con
     * un agente spcificato da parametro e una performativa nulla
     *
     * @param agent AgentID sender del messaggio da ricercare
     * @return boolean true se il messaggio e` presente, false altrimenti
     * @throws Exception
     */
    public boolean isThereMessage(AgentID agent) throws Exception {
        return isThereMessage(agent, null);
    }

    /**
     * richiamo del metodo isThereMessage(AgentID sender, Performative perf) con
     * un agente generico e una performativa specificata da parametro
     *
     * @param perf Performative performativa del messagggio da ricercare
     * @return boolean true se il messaggio e` presente, false altrimenti
     * @throws Exception
     */
    public boolean isThereMessage(Performative perf) throws Exception {
        return isThereMessage(new GenericAgentID(), perf);
    }

    /**
     * metodo atto a verificare la presenza di un messaggio, definito da
     * parametri, in una MessageBox. Richiama il metodo corrispondente di
     * MessageBoxNoSync e notifica tutti gli eventuali thread in wait. Nel caso
     * non vi sia il messaggio ricercato invoca ilk metodo wait()
     *
     * @param sender AgentID sender del messaggio da ricercare
     * @param perf Performative performativa del messagggio da ricercare
     * @return boolean true se il messaggio e` presente, false altrimenti
     * @throws InterruptedException
     */
    public synchronized boolean isThereMessage(AgentID sender, Performative perf) throws InterruptedException {
        while (true) {
            try {
                boolean isThere;
                isThere = super.isThereMessage(sender, perf);
                notifyAll();
                return isThere;
            } catch (java.util.ConcurrentModificationException e) {
                wait();
            }
        }
    }

    /**
     * aggiunge il messaggio passato per parametro in coda alla listya di
     * messaggi
     *
     * @param m Message messaggio da aggiungere alla lista
     */
    public synchronized void writeMessage(Message m) {
        super.writeMessage(m);
        notifyAll();
    }

    /**
     *
     * @return String stamapa l'owner del messaggio come stringa
     */
    public String toString() {
        return super.toString();
    }
}
