package JAM;

import java.io.Serializable;

/**
 * classe usata per la costruzione di messaggi utilizzati dagli agenti per
 * comunicare. E` caratterizzata da un mittente (sender) un destinatario
 * (receiver) una performativa (performative) un contenuto (content) e un
 * argomentro extra (extraArgument)
 *
 * @author Andrea Bragagnolo
 */
public class Message implements Serializable {

    private AgentID sender;
    private AgentID receiver;
    private Performative performative;
    private String content;
    private Object extraArgument;

    /**
     * costruttore utilizzato per creare messaggi che necessitano dell'argomento
     * extra
     *
     * @param send AgentID il sender del messaggio
     * @param rec AgentID il receiver del messaggio
     * @param perf Performative la performativa del messaggio
     * @param cont String il contenuto del messaggio
     * @param extra Object l'argomento extra del messaggio
     */
    public Message(AgentID send, AgentID rec, Performative perf, String cont, Object extra) {
        this.sender = send;
        this.receiver = rec;
        this.performative = perf;
        this.content = cont;
        this.extraArgument = extra;
    }

    /**
     * costruttore utilizzato per creare messaggi che non necessitano
     * dell'argomento extra
     *
     * @param send AgentID il sender del messaggio
     * @param rec AgentID il receiver del messaggio
     * @param perf Performative la performativa del messaggio
     * @param cont String il contenuto del messaggio
     */
    public Message(AgentID send, AgentID rec, Performative perf, String cont) {
        this.sender = send;
        this.receiver = rec;
        this.performative = perf;
        this.content = cont;
    }

    /**
     *
     * @return AgentID il sender del messaggio su cui e` invocato
     */
    public AgentID getSender() {
        return this.sender;
    }

    /**
     * metodo usato per settare il sender di un messaggio
     *
     * @param sender AgentID sender utilizzato dal metodo
     */
    public void setSender(AgentID sender) {
        this.sender = sender;
    }

    /**
     *
     * @return AgentID receiver del messaggio su cui e` invocato
     */
    public AgentID getReceiver() {
        return this.receiver;
    }

    /**
     * metodo usato per settare il receiver di un messaggio
     *
     * @param receiver AgentID receiver utilizzato dal metodo
     */
    public void setReceiver(AgentID receiver) {
        this.receiver = receiver;
    }

    /**
     *
     * @return Performative performativa del messaggio su cui e` invocato
     */
    public Performative getPerformative() {
        return this.performative;
    }

    /**
     * metodo usato per settare la performativa di un messaggio
     *
     * @param perf Performative peformativa usata dal metodo
     */
    public void setPerformative(Performative perf) {
        this.performative = perf;
    }

    /**
     *
     * @return String content del messaggio su cui e` invocato
     */
    public String getContent() {
        return this.content;
    }

    /**
     * metodo usato per settare il content del messaggio su cui e` invocato
     *
     * @param content String content usato dal metodo
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return Object argomento extra del messaggio su cui e` invocato
     */
    public Object getExtraArgument() {
        return this.extraArgument;
    }

    /**
     * metodo usato per settare l'argomento extra del messaggio su cui e`
     * invocato
     *
     * @param extra Object argomento extra usato dal metodo
     */
    public void setExtraArgument(Object extra) {
        this.extraArgument = extra;
    }

    /**
     *
     * @return String oggetto che rappresenta il Message
     */
    public String toString() {
        String res
                = "Performativa: " + this.performative + "\n"
                + "Sender: " + this.sender.toString() + "\n"
                + "Receiver: " + this.receiver.toString() + "\n"
                + "Content: " + this.content + "\n";
        if (this.extraArgument != null) {
            res += "Extra Argument: " + this.extraArgument.toString() + "\n";
        }

        return res;
    }
}
