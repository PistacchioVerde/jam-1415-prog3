package JAM;

/**
 *la classe permette di creare un agente definito solo dalla categoria
 * @author Andrea Bragagnolo
 */
public class CategoryAgentID extends GenericAgentID {

    /**
     * costruttore che richiama il metodo di GenericAgentID
     */
    public CategoryAgentID() {
        super();
    }

    /**
     * costruttore con categoria specifica
     *
     * @param cat categoria da assegnare all'agente
     */
    public CategoryAgentID(String cat) {
        super();
        this.category = cat;
    }

    /**
     * confronta un oggetto della classe che implementa l'interfaccia AgentID
     * con un oggetto di tipo AgentID. Se il parametro e` nullo ritorna false,
     * altrimenti tenta di eseguire un cast in CategoryAgentID e confronta la
     * categoria dei due elementi. Nel caso il cast sollevi un'eccezione viene
     * richiamato il metodo di GenericAgentID
     *
     * @param agent oggetto da confrontare
     * @return Restituisce true se l'identificatore dell'agente passato come
     * parametro appartiene alla stessa categoria dell'oggetto su cui e
     * invocato il metodo, false altrimenti
     */
    @Override
    public boolean equals(AgentID agent) {
        if (agent == null) {
            return false;
        }
        try {
            CategoryAgentID a = (CategoryAgentID) agent;
            if (a.category.equals(this.category)) {
                return true;
            }
        } catch (ClassCastException e) {
            return super.equals(agent);
        }
        return false;
    }
}
