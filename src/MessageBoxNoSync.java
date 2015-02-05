package JAM;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * classe che permette la creazione di MessageBox non sincronizzate. Queste sono
 * definite da un owner ed una lista di messaggi. Offre metodi per la lettura e
 * la scrittura di messaggi su tale lista
 *
 * @author Andrea Bragagnolo
 */
public class MessageBoxNoSync extends UnicastRemoteObject {

    private PersonalAgentID owner; //proprietario della casella postale
    private List<Message> box; //lista di messaggi nella casella

    /**
     * costruttore della MessageBox che inizializza la lista di Message vuota
     *
     * @param owner PersonalAgentID identifica il proprietario della MessageBox
     * @throws RemoteException
     */
    public MessageBoxNoSync(PersonalAgentID owner) throws RemoteException {
        this.owner = owner;
        this.box = new ArrayList<Message>();
    }

    /**
     * metodo atto all'identificazione dell'owner di una certa MessageBox
     *
     * @return PersonalAgentID owner della MessageBox
     */
    public PersonalAgentID getOwner() {
        return this.owner;
    }

    /**
     * metodo atto ad ottenere la lista di messaggi dentro la MessageBox
     *
     * @return List lista di messaggi presente nella box
     */
    public List<Message> getBox() {
        return this.box;
    }

    /**
     * metodo utilizzato per settare l'owner di una MessageBox
     *
     * @param owner PersonalAgentID che definisce l'owner da settare
     */
    public void setOwner(PersonalAgentID owner) {
        this.owner = owner;
    }

    /**
     * metodo utilizzato per settare la lsita di messaggi di una MessageBox
     *
     * @param list ArrayList che definisce la lista da settare
     */
    public void setBox(ArrayList<Message> list) {
        this.box = list;
    }

    /**
     * metodo utilizzato per controllase se sono presenti messaggi nella
     * MessageBox
     *
     * @return boolena true se la MessageBox e` vuota, false altrimenti
     */
    public boolean isBoxEmpty() {
        return this.box.isEmpty();
    }

    /**
     * richiamo del metodo readMessage(AgentID sender, Performative perf) con un
     * agente generico e nessuna performativa
     *
     * @return messaggio che corrisponde ai parametri
     * @throws JAMMessageBoxException
     * @throws InterruptedException
     */
    public Message readMessage() throws JAMMessageBoxException, InterruptedException {
        return readMessage(new GenericAgentID(), null);
    }

    /**
     * richiamo del metodo readMessage(AgentID sender, Performative perf) con un
     * AgentID definito da parametro e nessuna performativa
     *
     * @param sender sender del messaggio ricercato
     * @return messaggio che corrisponde ai parametri
     * @throws JAMMessageBoxException
     * @throws InterruptedException
     */
    public Message readMessage(AgentID sender) throws JAMMessageBoxException, InterruptedException {
        return readMessage(sender, null);
    }

    /**
     * richiamo del metodo readMessage(AgentID sender, Performative perf) con un
     * agente generico e una performativa definita da parametro
     *
     * @param perf performativa del messaggio ricercato
     * @return messaggio che corrisponde ai parametri
     * @throws JAMMessageBoxException
     * @throws InterruptedException
     */
    public Message readMessage(Performative perf) throws JAMMessageBoxException, InterruptedException {
        return readMessage(new GenericAgentID(), perf);
    }

    /**
     * cerca e rimuove il primo messaggio (piu` vecchio) di un particolare
     * agente con una certa performativa. Per ogni componente della lista di
     * messaggi della MessageBox controlla se il sender dell'elemento e` uguale
     * al parametro AgentID, nel caso lo fosse controlla se la performativa del
     * messaggio e` uguale a quella passata come parametro o nulla. Se uno dei
     * due controlli e` falso lancia una JAMMessageBoxException
     *
     * @param sender sender del messaggio ricercato
     * @param perf performativa del messaggio ricercato
     * @return messaggio che corrisponde ai parametri
     * @throws JAM.JAMMessageBoxException
     * @throws java.lang.InterruptedException
     */
    public Message readMessage(AgentID sender, Performative perf) throws JAMMessageBoxException, InterruptedException {
        for (int i = 0; i < this.box.size(); i++) {
            if (this.box.get(i).getSender().equals(sender)) {
                if (this.box.get(i).getPerformative() == perf || perf == null) {
                    return this.box.remove(i);
                }
            }
        }
        throw new JAMMessageBoxException();
    }

    /**
     * richiamo del metodo isThereMessage(AgentID sender, Performative perf) con
     * un agente generico e una performativa nulla
     *
     * @return boolean true se il messaggio e` presente, false altrimenti
     * @throws Exception
     */
    public boolean isThereMessage() throws Exception {
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
     * parametri, in una MessageBox. Per ogni componente della lista di messaggi
     * della MessageBox controlla se il sender dell'elemento e` uguale al
     * parametro AgentID, nel caso lo fosse controlla se la performativa del
     * messaggio e` uguale a quella passata come parametro o nulla. Se uno dei
     * due controlli e` falso rtorna false, true altrimenti.
     *
     * @param sender AgentID sender del messaggio da ricercare
     * @param perf Performative performativa del messagggio da ricercare
     * @return boolean true se il messaggio e` presente, false altrimenti
     * @throws java.lang.InterruptedException
     */
    public boolean isThereMessage(AgentID sender, Performative perf) throws InterruptedException {

        for (int i = 0; i < this.box.size(); i++) {
            if (this.box.get(i).getSender().equals(sender)) {
                if (perf == null || perf == this.box.get(i).getPerformative()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * aggiunge il messaggio passato per parametro in coda alla listya di
     * messaggi
     *
     * @param m Message messaggio da aggiungere alla lista
     */
    public void writeMessage(Message m) {
        this.box.add(m);
    }

    /**
     *
     * @return String stamapa l'owner del messaggio come stringa
     */
    public String toString() {
        return "[Message Box - " + this.owner + "]";
    }
}
