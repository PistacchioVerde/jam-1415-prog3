package JAM;

/**
 * Interfaccia per gli agenti
 *
 * @author Andrea Bragagnolo
 */
public interface AgentID {

    /**
     * confronta un oggetto della classe che implementa l'interfac- cia AgentID
     * con un oggetto di tipo AgentID
     *
     * @param agentID oggetto da confrontare
     * @return Restituisce true se l'identificatore dell'agente passato come
     * parametro ha lo stesso nome e appartiene alla stessa categoria
     * dell'oggetto su cui e invocato il metodo, false altrimenti
     */
    public boolean equals(AgentID agentID);

    /**
     *
     * @return valore corrente del campo name di tipo String che rappre- senta
     * il nome simbolico dell'agente
     */
    public String getName();

    /**
     *
     * @return valore corrente del campo category di tipo String che rappresenta
     * la categoria a cui appartiene l'agente
     */
    public String getCategory();

    /**
     *
     * @return un oggetto di tipo String che rappresenta l'oggetto stesso
     */
    public String toString();
}
