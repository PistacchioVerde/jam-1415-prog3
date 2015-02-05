package JAM;

import java.io.Serializable;

/**
 * classe che permette la costruzione di un agente generico
 *
 * @author Andrea Bragagnolo
 */
public class GenericAgentID implements AgentID, Serializable {

    protected String name;
    protected String category;

    /**
     * costruttore di GenericAgentID che assegna stringhe vuote ai parametri
     * name e category
     */
    public GenericAgentID() {
        this.name = "";
        this.category = "";
    }

    /**
     *
     * @return String valore corrente del campo name di tipo String che rappre-
     * senta il nome simbolico dell'agente
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return String valore corrente del campo category di tipo String che
     * rappresenta la categoria a cui appartiene l'agente
     */
    public String getCategory() {
        return this.category;
    }

    /**
     *
     * @return String un oggetto che rappresenta l'oggetto stesso
     */
    public String toString() {
        return "(" + this.name + " " + this.category + ")";
    }

    /**
     * confronta un oggetto della classe che implementa l'interfaccia AgentID
     * con un oggetto di tipo AgentID
     *
     * @param agent oggetto da confrontare
     * @return Restituisce true se l'identificatore dell'agente passato come
     * parametro ha lo stesso nome e appartiene alla stessa categoria
     * dell'oggetto su cui e invocato il metodo, false altrimenti
     */
    public boolean equals(AgentID agent) {
        if (agent == null) {
            return false;
        } else {
            try {
                GenericAgentID a = (GenericAgentID) agent;
                return true;
            } catch (ClassCastException e) {
                return false;
            }
        }
    }
}
